package common.core.common.http;

public interface BodyBuilder extends HeaderBuilder {
	HeaderBuilder form();

	BodyBuilder xml(Object body);
	BodyBuilder json(Object body);

	BodyBuilder text(String body, String contentType);

	BodyBuilder binary(byte[] body);

	BodyBuilder chunked(boolean chunked);
}
