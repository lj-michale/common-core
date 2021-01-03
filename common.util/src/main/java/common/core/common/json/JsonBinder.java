package common.core.common.json;

import java.io.IOException;

import common.core.common.exception.RuntimeIOException;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class JsonBinder<T> {
	static final ObjectMapper DEFAULT_OBJECT_MAPPER;

	static {
		DEFAULT_OBJECT_MAPPER = createMapper();
	}

	public static <T> JsonBinder<T> binder(Class<T> beanClass) {
		return new JsonBinder<>(beanClass);
	}

	public static ObjectMapper getObjectMapper() {
		return DEFAULT_OBJECT_MAPPER;
	}

	public static ObjectMapper createMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new JsonDateFormat());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector(TypeFactory.defaultInstance()));
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, false);// 不格式化
		mapper.setSerializationInclusion(Include.NON_NULL);// 去空
		return mapper;
	}

	private final Class<T> beanClass;
	ObjectMapper objectMapper;

	private JsonBinder(Class<T> beanClass) {
		this.beanClass = beanClass;
		this.objectMapper = DEFAULT_OBJECT_MAPPER;
	}

	public T fromJson(String json) {
		try {
			return objectMapper.readValue(json, beanClass);
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public String toJson(T object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	public JsonBinder<T> indentOutput() {
		if (DEFAULT_OBJECT_MAPPER.equals(objectMapper)) {
			objectMapper = createMapper();
		}
		return this;
	}
}
