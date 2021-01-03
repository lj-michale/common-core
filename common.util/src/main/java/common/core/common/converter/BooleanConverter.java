package common.core.common.converter;

import org.springframework.core.convert.converter.Converter;

import common.core.common.util.StringUtil;

public class BooleanConverter implements Converter<String, Boolean> {

	@Override
	public Boolean convert(String text) {
		if (StringUtil.isNotBlank(text)) {
			return Boolean.valueOf(text);
		}
		return null;
	}

}
