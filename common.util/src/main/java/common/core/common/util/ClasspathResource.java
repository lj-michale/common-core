package common.core.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

public final class ClasspathResource {
	private byte[] bytes;
	private final String resourcePath;

	public ClasspathResource(String resourcePath) {
		this.resourcePath = resourcePath;
		InputStream stream = null;
		try {
			stream = ClasspathResource.class.getClassLoader().getResourceAsStream(resourcePath);
			if (stream == null)
				throw new IllegalArgumentException("can not load resource, path=" + resourcePath);
			bytes = IoUtil.bytes(stream);
		} finally {
			IoUtil.close(stream);
		}
	}

	public InputStream getInputStream() {
		return new ByteArrayInputStream(bytes);
	}

	public String getTextContent() {
		return new String(bytes, Charset.defaultCharset());
	}

	public byte[] getBytes() {
		return bytes;
	}

	public String resourcePath() {
		return resourcePath;
	}
}
