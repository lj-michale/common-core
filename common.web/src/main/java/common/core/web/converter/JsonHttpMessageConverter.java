package common.core.web.converter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.regex.Pattern;

import org.apache.commons.codec.CharEncoding;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;

import common.core.common.json.JsonBinder;
import common.core.common.util.StringUtil;
import common.core.web.context.RequestContext;
import common.core.web.p3p.P3pHeader;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

	public static final String[] PARAM_CALL_BACKS = { "_callback", "callback", "jsonpcallback" };
	public static final MediaType MEDIATYPE_JSONP = MediaType.valueOf("application/x-javascript; charset=utf-8");
	public static final Pattern PATTERN_CALLBACK = Pattern.compile("^[0-9a-zA-Z_\\.]+$");

	public JsonHttpMessageConverter() {
		super();
		this.setObjectMapper(JsonBinder.createMapper());
	}

	@Override
	public void setObjectMapper(ObjectMapper objectMapper) {
		super.setObjectMapper(objectMapper);
	}

	@Override
	public ObjectMapper getObjectMapper() {
		return super.getObjectMapper();
	}

	@Override
	public void setJsonPrefix(String jsonPrefix) {
		super.setJsonPrefix(jsonPrefix);
	}

	@Override
	public void setPrefixJson(boolean prefixJson) {
		super.setPrefixJson(prefixJson);
	}

	@Override
	public void setPrettyPrint(boolean prettyPrint) {
		super.setPrettyPrint(prettyPrint);
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return super.canRead(clazz, mediaType);
	}

	@Override
	public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
		return super.canRead(type, contextClass, mediaType);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return super.canWrite(clazz, mediaType);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return super.supports(clazz);
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return super.readInternal(clazz, inputMessage);
	}

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return super.read(type, contextClass, inputMessage);
	}

	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		this.writeInternal(object, null, outputMessage);
	}

	@Override
	protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		// clean cache
		outputMessage.getHeaders().set("CacheControl", "no-cache");
		outputMessage.getHeaders().set("Pragma", "no-cache");
		outputMessage.getHeaders().set("Expires", "-1");

		String callback = null;
		for (String callbackParamName : PARAM_CALL_BACKS) {
			callback = RequestContext.getParameter(callbackParamName);
			if (!StringUtil.isEmpty(callback))
				break;
		}
		boolean jsonp = false;
		if ((StringUtils.hasText(callback) && PATTERN_CALLBACK.matcher(callback).find())) {
			jsonp = true;
		}
		if (jsonp) {
			outputMessage.getHeaders().set(P3pHeader.HEADER_NAME, P3pHeader.HEADER_VALUE);
			outputMessage.getHeaders().setContentType(MEDIATYPE_JSONP);
			outputMessage.getBody().write(callback.getBytes(CharEncoding.UTF_8));
			outputMessage.getBody().write('(');
		}
		super.writeInternal(object, type, outputMessage);
		if (jsonp) {
			outputMessage.getBody().write(')');
		}

	}

	@Override
	protected JavaType getJavaType(Type type, Class<?> contextClass) {
		return super.getJavaType(type, contextClass);
	}

	@Override
	protected JsonEncoding getJsonEncoding(MediaType contentType) {
		return super.getJsonEncoding(contentType);
	}

}
