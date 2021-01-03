package common.core.common.http;

public interface HeaderBuilder extends ParameterBuilder {
	HeaderBuilder addHeader(String key, String value);

	HeaderBuilder accept(String accept);
}
