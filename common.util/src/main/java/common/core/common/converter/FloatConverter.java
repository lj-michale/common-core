package common.core.common.converter;

public class FloatConverter extends NumberConverter<Float> {

	@Override
	public Float convertToNumber(String numStr) {
		return Float.valueOf(numStr);
	}

}
