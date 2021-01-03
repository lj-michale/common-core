package common.core.common.http;

public class HTTPResponse {
	private final HTTPStatusCode statusCode;
	private final HTTPHeaders headers;
	private final HTTPContentType contentType;
	private final byte[] content;

	public HTTPResponse(HTTPStatusCode statusCode, HTTPHeaders headers, HTTPContentType contentType, byte[] content) {
		this.statusCode = statusCode;
		this.headers = headers;
		this.contentType = contentType;
		this.content = content;
	}

	public String responseText() {
		return new String(content, contentType.getCharset());
	}

	public byte[] responseBinary() {
		return content;
	}

	public HTTPStatusCode statusCode() {
		return statusCode;
	}

	public HTTPHeaders headers() {
		return headers;
	}

	public HTTPContentType contentType() {
		return contentType;
	}
}
