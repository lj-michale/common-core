package common.core.common.util;

import org.junit.Test;

import common.core.common.assertion.exception.ErrorAssertionException;
import common.core.common.assertion.exception.InfoAssertionException;
import common.core.common.assertion.exception.WarnAssertionException;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.assertion.util.AssertInfoUtils;
import common.core.common.assertion.util.AssertWarnUtils;

public class ErrorAssertUtilsTest {

	@Test(expected = WarnAssertionException.class)
	public void assertWarnUtils() {
		AssertWarnUtils.assertFalse(true, "test warn !");
	}

	@Test(expected = ErrorAssertionException.class)
	public void assertErrorUtils() {
		AssertErrorUtils.assertFalse(true, "test warn !");
	}

	@Test(expected = InfoAssertionException.class)
	public void assertInfoUtils() {
		AssertInfoUtils.assertFalse(true, "test warn !");
	}

}
