package common.core.common.converter;

import org.springframework.core.convert.converter.Converter;

public class StringConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		return source;
	}

}
