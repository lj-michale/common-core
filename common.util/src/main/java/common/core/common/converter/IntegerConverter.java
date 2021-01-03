package common.core.common.converter;

public class IntegerConverter extends NumberConverter<Integer> {

	@Override
	public Integer convertToNumber(String numStr) {
		return Integer.valueOf(numStr);
	}

}
