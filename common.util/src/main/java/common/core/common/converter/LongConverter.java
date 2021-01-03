package common.core.common.converter;

public class LongConverter extends NumberConverter<Long> {

	@Override
	public Long convertToNumber(String numStr) {
		return Long.valueOf(numStr);
	}

}
