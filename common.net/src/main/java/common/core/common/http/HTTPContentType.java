package common.core.common.http;

import java.nio.charset.Charset;

import org.apache.http.entity.ContentType;

import common.core.app.lang.CharSet;

public class HTTPContentType {
	static final Charset DEFAULT_CHARSET = CharSet.DEFAULT_CHAR_SET_CLASS;
	static final String DEFAULT_MIME_TYPE = HTTPConstants.CONTENT_TYPE_JSON;

	private ContentType contentType;

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public String getMimeType() {
		if (contentType == null || contentType.getMimeType() == null) {
			return DEFAULT_MIME_TYPE;
		} else {
			return contentType.getMimeType();
		}
	}

	public Charset getCharset() {
		if (contentType == null || contentType.getCharset() == null) {
			return DEFAULT_CHARSET;
		}
		return contentType.getCharset();
	}

}
