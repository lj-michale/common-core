package common.core.common.http;

public interface ParameterBuilder extends RequestBuilder {
	ParameterBuilder addParameter(String key, String value);

	ParameterBuilder setParameter(String key, String value);
}
