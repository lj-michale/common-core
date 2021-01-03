package common.core.site.page;

import org.junit.Assert;
import org.junit.Test;

public class NullNumberCellFormatTest {

	@Test
	public void format() {
		NullNumberCellFormat nullNumberCellFormat = new NullNumberCellFormat("###,###,###,###,###,###,##0.00", 0);
		Assert.assertEquals("0.00", nullNumberCellFormat.format(null));
		Assert.assertEquals("0.00", nullNumberCellFormat.format(0.001));
		Assert.assertEquals("10.00", nullNumberCellFormat.format(10.001));
		Assert.assertEquals("10.01", nullNumberCellFormat.format(10.005));
		Assert.assertEquals("1,000.00", nullNumberCellFormat.format(1000.004));
		Assert.assertEquals("1,000,000.01", nullNumberCellFormat.format(1000000.005));
	}
}
