package common.core.common.converter;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import common.core.common.util.StringUtil;

public class StringListConverter implements Converter<String, List<String>> {

	@Override
	public List<String> convert(String source) {
		if (StringUtil.isBlank(source))
			return null;
		return Arrays.asList(StringUtil.split(source));
	}

}
