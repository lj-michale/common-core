package common.core.web.converter;

import java.lang.reflect.Type;
import java.util.Arrays;

import org.springframework.http.MediaType;

public class JavaScriptHttpMessageConverter extends JsonHttpMessageConverter {

	public JavaScriptHttpMessageConverter() {
		this.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "javascript", DEFAULT_CHARSET), new MediaType("application", "*+javascript", DEFAULT_CHARSET)));
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
		return false;
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return false;
	}

}
