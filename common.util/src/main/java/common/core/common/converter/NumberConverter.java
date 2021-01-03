package common.core.common.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;

import common.core.common.util.StringUtil;

public abstract class NumberConverter<T extends Number> implements Converter<String, T> {

	private final static BigDecimal BIGDECIMAL_MULTIPLY = new BigDecimal(10000);

	@Override
	public T convert(String text) {
		if (StringUtil.isNotBlank(text)) {
			text = filterText(text);
			return convertToNumber(text);
		}
		return null;
	}

	private String filterText(String text) {
		if (text.indexOf(",") >= 0) {
			text = text.replaceAll("\\,", "");
		}
		text = text.trim();
		if (text.endsWith("万")) {
			text = text.replaceAll("万", "");
			text = new BigDecimal(text).multiply(BIGDECIMAL_MULTIPLY).toString();
			text = text.replaceAll("\\.[0]*$", "");
		}
		return text;
	}

	public abstract T convertToNumber(String numStr);

}
