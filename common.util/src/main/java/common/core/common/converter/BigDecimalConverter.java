package common.core.common.converter;

import java.math.BigDecimal;

public class BigDecimalConverter extends NumberConverter<BigDecimal> {

	@Override
	public BigDecimal convertToNumber(String numStr) {
		return new BigDecimal(numStr);
	}

}
