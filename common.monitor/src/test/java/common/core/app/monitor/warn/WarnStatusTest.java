package common.core.app.monitor.warn;

import org.junit.Assert;
import org.junit.Test;

public class WarnStatusTest {
	@Test
	public void compareTo() {
		Assert.assertTrue(WarnStatus.DEBUG.compareTo(WarnStatus.ERROR) < 0);
		Assert.assertTrue(WarnStatus.ERROR.compareTo(WarnStatus.ERROR) == 0);
		Assert.assertTrue(WarnStatus.FATAL.compareTo(WarnStatus.ERROR) > 0);
	}
}
