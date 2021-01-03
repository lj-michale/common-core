package common.core.web.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.core.env.ConfigurableEnvironment;

import common.core.app.lang.ContentType;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.ApplicationContextUtil;
import common.core.common.util.IoUtil;

public class DefaultCacheFilePolicyBuilder implements CacheFilePolicyBuilder {
	private final Logger logger = LoggerFactory.getLogger(DefaultCacheFilePolicyBuilder.class);

	private static final int MAX_PATH_LENGTH = 20;
	private Pattern[] rulePatterns;
	private File[] pathFiles;
	private long[] timeouts;
	private String[] extensions;
	private int ruleSize;
	private static Boolean init = Boolean.FALSE;

	public DefaultCacheFilePolicyBuilder() {
	}

	private void init() {
		if (init)
			return;
		synchronized (this) {
			if (init)
				return;
			ConfigurableEnvironment env = ApplicationContextUtil.getBean(ConfigurableEnvironment.class);

			String[] rules = env.getProperty("cacheFile.rule", "").split(";");
			String[] paths = env.getProperty("cacheFile.path", "").split(";");
			String[] extensionItems = env.getProperty("cacheFile.extension", "").split(";");
			String[] ts = env.getProperty("cacheFile.timeout", "").split(";");
			this.ruleSize = rules.length;
			this.rulePatterns = new Pattern[ruleSize];
			this.pathFiles = new File[ruleSize];
			this.extensions = new String[ruleSize];
			this.timeouts = new long[ruleSize];
			for (int i = 0; i < ruleSize; i++) {
				this.rulePatterns[i] = Pattern.compile(rules[i]);
				this.pathFiles[i] = new File(paths[i]);
				this.extensions[i] = extensionItems[i];
				this.timeouts[i] = Long.parseLong(ts[i]);
				IoUtil.createDirectorieIfMissing(this.pathFiles[i]);
			}
			init = true;
		}

	}

	@Override
	public CacheFilePolicy getCapturerFileItem(HttpServletRequest request, HttpServletResponse response) {
		if (!request.getMethod().equalsIgnoreCase("get")) {
			return null;
		}

		init();

		int index = -1;
		String uri = request.getRequestURI();
		for (int i = 0; i < ruleSize; i++) {
			if (this.rulePatterns[i].matcher(uri).find()) {
				index = i;
				break;
			}
		}
		if (index == -1) {
			return null;
		}
		CacheFilePolicy capturerFileItem = new CacheFilePolicy();
		File file = buildFile(index, uri);
		boolean canCapture = !file.exists() || System.currentTimeMillis() - file.lastModified() > this.timeouts[index];
		capturerFileItem.setFile(file);
		capturerFileItem.setCanCapture(canCapture);
		return capturerFileItem;
	}

	private File buildFile(int index, String uri) {
		String md5 = DigestUtils.md5Hex(uri);
		String path = uri.replaceAll("/", "_");
		if (path.length() > MAX_PATH_LENGTH) {
			path = path.substring(0, MAX_PATH_LENGTH);
		}
		File file = new File(this.pathFiles[index], new StringBuilder(path).append("_").append(md5).append(".").append(this.extensions[index]).toString());
		return file;
	}

	@Override
	public void writeFromCapturerFilePolicy(HttpServletResponse httpServletResponse, CacheFilePolicy cacheFilePolicy) throws IOException {
		int size = 1024;
		int length = -1;
		int dataLength = 0;
		char[] data = new char[size];
		httpServletResponse.setContentLength(-1);
		httpServletResponse.setCharacterEncoding(CharEncoding.UTF_8);
		httpServletResponse.setContentType(ContentType.TEXT_HTML_UTF8);
		BufferedReader reader = null;
		try {
			//reader = new BufferedReader(new FileReader(cacheFilePolicy.getFile()));
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(cacheFilePolicy.getFile()) ,"utf-8"));
			PrintWriter writer = httpServletResponse.getWriter();
			while ((length = reader.read(data, 0, size)) != -1) {
				writer.write(data, 0, length);
				dataLength = dataLength + length;
			}
			httpServletResponse.setContentLength(dataLength);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}

	}
}
