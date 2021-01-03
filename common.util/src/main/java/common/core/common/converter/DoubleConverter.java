package common.core.common.converter;

public class DoubleConverter extends NumberConverter<Double> {

	@Override
	public Double convertToNumber(String numStr) {
		return Double.valueOf(numStr);
	}

}
