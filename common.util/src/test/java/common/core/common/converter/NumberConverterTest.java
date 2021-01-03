package common.core.common.converter;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class NumberConverterTest {

	@Test
	public void convert() {
		Assert.assertEquals(1000, new IntegerConverter().convert("1000").intValue());
		Assert.assertEquals(1100, new IntegerConverter().convert("0.11万").intValue());
		Assert.assertEquals(10000000, new IntegerConverter().convert("1000万").intValue());
		Assert.assertTrue(new DoubleConverter().convert("1000").doubleValue() - 1000 == 0);
		Assert.assertTrue(new DoubleConverter().convert("1000.01").doubleValue() - 1000.01 == 0);
		Assert.assertTrue(new DoubleConverter().convert("1000.01万").doubleValue() - 1000.01 * 10000 == 0);
		Assert.assertEquals(new BigDecimal(1000), new BigDecimalConverter().convert("1000"));
		Assert.assertEquals(new BigDecimal(1100), new BigDecimalConverter().convert("0.11万"));
		Assert.assertEquals(new BigDecimal(1000 * 10000), new BigDecimalConverter().convert("1000万"));
	}
}
