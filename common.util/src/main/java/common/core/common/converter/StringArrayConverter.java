package common.core.common.converter;

import org.springframework.core.convert.converter.Converter;

import common.core.common.util.StringUtil;

public class StringArrayConverter implements Converter<String, String[]> {

	@Override
	public String[] convert(String source) {
		if (StringUtil.isBlank(source))
			return null;
		return StringUtil.split(source);
	}

}
