package common.core.common.http;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.util.EntityUtils;

import common.core.app.lang.TimeLength;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.exception.HttpClientsResponseException;
import common.core.common.exception.RuntimeIOException;
import common.core.common.util.ObjectUtil;
import common.core.common.util.StopWatch;
import common.core.common.util.StringUtil;

public class HttpClients {
	public static final TimeLength DEFAULT_TIME_OUT = TimeLength.minutes(2);

	public static final TimeLength NO_TIME_OUT = TimeLength.ZERO;

	private static final int CONNECTION_POOL_MAX_SIZE = 200;

	private final Logger logger = LoggerFactory.getLogger(HttpClients.class);

	private final ReentrantLock lock = new ReentrantLock();

	private transient HttpClient httpClient;

	private boolean connectionPoolEnabled = false;

	private boolean ignoreCookie = true;

	private boolean redirectEnabled = false;

	private boolean validateStatusCode = false;

	private boolean keepAlive = false;

	private TimeLength timeout = DEFAULT_TIME_OUT;

	public HttpClients enableConnectionPool() {
		this.connectionPoolEnabled = true;
		return this;
	}

	public HttpClients enableKeepAlive() {
		this.keepAlive = true;
		return this;
	}

	public HttpClients enableValidateStatus() {
		this.validateStatusCode = true;
		return this;
	}

	public HttpClients enableCookie() {
		this.ignoreCookie = false;
		return this;
	}

	public HttpClients enableRedirect() {
		this.redirectEnabled = true;
		return this;
	}

	public HttpClients setTimeout(TimeLength timeout) {
		this.timeout = timeout;
		return this;
	}

	public HTTPResponse execute(HTTPRequest request) {
		HttpRequestBase httpRequest = request.getRawRequest();
		StopWatch watch = new StopWatch();
		try {
			logger.debug("send request, url={}, method={}", request.url(), request.method());
			logger.debug("====== http request begin ======");
			logRequest(request);

			HttpResponse response = getHttpClient().execute(httpRequest);

			logger.debug("====== http request end ======");
			logger.debug("received response, statusCode={}", response.getStatusLine().getStatusCode());

			HTTPStatusCode statusCode = new HTTPStatusCode(response.getStatusLine().getStatusCode());

			HTTPContentType contentType = new HTTPContentType();
			contentType.setContentType(ContentType.get(response.getEntity()));

			byte[] content = EntityUtils.toByteArray(response.getEntity());
			HTTPResponse httpResponse = new HTTPResponse(statusCode, createResponseHeaders(response), contentType, content);
			logResponse(httpResponse);

			if (validateStatusCode) {
				validateStatusCode(statusCode);
			}

			return httpResponse;
		} catch (IOException e) {
			throw new HTTPException(e);
		} finally {
			httpRequest.releaseConnection();
			logger.debug("execute finished, elapsedTime={}(ms)", watch.elapsedTime());
		}
	}

	public HTTPResponse postXml(String url, Object xmlObject) {
		ParameterBuilder parameterBuilder = HTTPRequest.post(url).xml(xmlObject);
		HTTPResponse response = this.execute(parameterBuilder.request());
		return response;
	}

	public HTTPResponse postJson(String url, Object jsonObject) {
		ParameterBuilder parameterBuilder = HTTPRequest.post(url).json(jsonObject);
		HTTPResponse response = this.execute(parameterBuilder.request());
		return response;
	}

	public <T> T post(String url, Class<T> targetClass, Object parametersObj) {
		HTTPResponse response = postAndResponse(url, parametersObj);
		if (!response.statusCode().isSuccess()) {
			throw new HttpClientsResponseException("Post response code" + response.statusCode().code() + ":" + url + "");
		}
		return ObjectUtil.fromJson(targetClass, response.responseText());
	}

	public HTTPResponse postAndResponse(String url, Object parametersObj) {
		ParameterBuilder parameterBuilder = HTTPRequest.post(url).form();
		HTTPResponse response = addParameters(ObjectUtil.toMap(parametersObj), parameterBuilder);
		return response;
	}

	public <T> T postXml(String url, Object xmlRequestObject, Class<T> responseType) {
		HTTPResponse response = postXml(url, xmlRequestObject);
		if (!response.statusCode().isSuccess()) {
			throw new RuntimeIOException("Request  error:" + url);
		}
		return ObjectUtil.fromXml(responseType, response.responseText());
	}

	public <T> T get(String url, Class<T> targetClass) {
		return get(url, targetClass, null);
	}

	public <T> T get(String url, Class<T> targetClass, Object parametersObj) {
		HTTPResponse response = getAndResponse(url, parametersObj);
		if (!response.statusCode().isSuccess()) {
			throw new HttpClientsResponseException("Get response code" + response.statusCode().code() + ":" + url + "");
		}
		return ObjectUtil.fromJson(targetClass, response.responseText());
	}

	public HTTPResponse getAndResponse(String url, Object parametersObj) {
		ParameterBuilder parameterBuilder = HTTPRequest.get(url);
		HTTPResponse response = addParameters(ObjectUtil.toMap(parametersObj), parameterBuilder);
		return response;
	}

	private HTTPResponse addParameters(Map<String, ?> parameters, ParameterBuilder parameterBuilder) {
		if (null != parameters && !parameters.isEmpty()) {
			for (Map.Entry<String, ?> entry : parameters.entrySet()) {
				if (StringUtil.hasText(entry.getKey())) {
					if (null == entry.getValue())
						continue;
					parameterBuilder.addParameter(entry.getKey(), entry.getValue().toString());
				}
			}
		}
		HTTPResponse response = this.execute(parameterBuilder.request());
		return response;
	}

	HTTPHeaders createResponseHeaders(HttpResponse response) {
		HTTPHeaders headers = new HTTPHeaders();
		for (Header header : response.getAllHeaders()) {
			headers.add(header.getName(), header.getValue());
		}
		return headers;
	}

	void validateStatusCode(HTTPStatusCode statusCode) {
		if (statusCode.isSuccess() || statusCode.isRedirect()) {
			return;
		}
		throw new HTTPException("invalid response status code, statusCode=" + statusCode);
	}

	void logRequest(HTTPRequest httpRequest) {
		if (httpRequest.headers() != null)
			for (HTTPHeader header : httpRequest.headers()) {
				logger.debug("[header] {}={}", header.name(), header.value());
			}

		if (httpRequest.parameters() != null) {
			for (NameValuePair parameter : httpRequest.parameters()) {
				logger.debug("[param] {}={}", parameter.getName(), parameter.getValue());
			}
		}

		logger.debug("body={}", httpRequest.body());
	}

	void logResponse(HTTPResponse httpResponse) {
		if (httpResponse.headers() != null)
			for (HTTPHeader header : httpResponse.headers()) {
				logger.debug("[header] {}={}", header.name(), header.value());
			}
		if (null != httpResponse.contentType()) {
			String mimeType = httpResponse.contentType().getMimeType().toLowerCase();
			if (mimeType.indexOf("json") >= 0 || mimeType.indexOf("xml") >= 0 || mimeType.indexOf("html") >= 0 || mimeType.indexOf("txt") >= 0)
				logger.debug("responseText={}", httpResponse.responseText());
		}
	}

	HttpClient getHttpClient() {
		if (httpClient == null) {
			try {
				lock.lock();
				if (httpClient == null) {
					httpClient = createDefaultHttpClient();
					configProxy(httpClient);
				}
			} finally {
				lock.unlock();
			}
		}
		return httpClient;
	}

	private void configProxy(HttpClient httpClient2) {
		String host = System.getProperty("http.proxyHost");
		String port = System.getProperty("http.proxyPort");

		if (StringUtil.isEmpty(host) || StringUtil.isEmpty(port)) {
			String env = System.getenv("http_proxy") != null ? System.getenv("http_proxy") : System.getenv("https_proxy");
			if (env != null) {
				String[] splits = env.split(":");
				host = splits[0];
				port = splits[1];
			}
		}

		if (StringUtil.isEmpty(host) || StringUtil.isEmpty(port)) {
			return;
		}

		// 设置代理对象 ip/代理名称,端口
		HttpHost proxy = new HttpHost(host, Integer.parseInt(port));
		// 对HttpClient对象设置代理
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

	}

	HttpClient createDefaultHttpClient() {
		BasicHttpParams params = new SyncBasicHttpParams();

		// set default time out
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, (int) timeout.toMilliseconds());
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, (int) timeout.toMilliseconds());
		params.setParameter(CoreConnectionPNames.SO_KEEPALIVE, keepAlive);

		if (redirectEnabled) {
			params.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true);
		}

		if (ignoreCookie) {
			params.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
		}

		ClientConnectionManager connectionManager = createClientConnectionManager();

		connectionManager.getSchemeRegistry().register(new Scheme(HTTPConstants.SCHEME_HTTP, HTTPConstants.STANDARD_PORT_HTTP, PlainSocketFactory.getSocketFactory()));
		registerHttpsScheme(connectionManager);

		return new DefaultHttpClient(connectionManager, params);
	}

	private ClientConnectionManager createClientConnectionManager() {
		if (connectionPoolEnabled) {
			PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
			connectionManager.setMaxTotal(CONNECTION_POOL_MAX_SIZE);
			connectionManager.setDefaultMaxPerRoute(CONNECTION_POOL_MAX_SIZE);
			return connectionManager;
		} else {
			return new BasicClientConnectionManager();
		}
	}

	void registerHttpsScheme(ClientConnectionManager connectionManager) {
		TrustManager[] trustManagers = new TrustManager[] { new SelfSignedX509TrustManager() };
		try {
			SSLContext context = SSLContext.getInstance(SSLSocketFactory.TLS);
			context.init(null, trustManagers, null);

			X509HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			SSLSocketFactory socketFactory = new SSLSocketFactory(context, hostnameVerifier);

			Scheme scheme = new Scheme(HTTPConstants.SCHEME_HTTPS, HTTPConstants.STANDARD_PORT_HTTPS, socketFactory);
			connectionManager.getSchemeRegistry().register(scheme);
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			throw new HTTPException(e);
		}
	}
}
