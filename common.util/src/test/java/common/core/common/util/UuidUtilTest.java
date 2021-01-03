package common.core.common.util;

import org.junit.Assert;
import org.junit.Test;

public class UuidUtilTest {

	@Test
	public void generateUuid() {
		Assert.assertEquals(62, UuidUtil.CHAR_62.length);
		Assert.assertEquals(8, UuidUtil.generateShortUuid().length());
		Assert.assertEquals(8 + 13, UuidUtil.generateShortUuidWithTime().length());
		Assert.assertEquals(16, UuidUtil.generateUuid16().length());
		Assert.assertEquals(13 + 8, UuidUtil.generateUuid8WithTimeAndMd5().length());
		Assert.assertEquals(8, UuidUtil.generateUuid8WithMd5().length());
		// System.out.println(Integer.parseInt("10", 32));
		// System.out.println('a' + 0);
		// System.out.println('A' + 0);
		// System.out.println('0' + 0);
		// System.out.println(UUID.randomUUID().toString());
		// System.out.println(System.currentTimeMillis());
		// System.out.println(System.nanoTime());
		// System.out.println(System.nanoTime());
		// System.out.println(UuidUtil.generateShortUuid());
		// System.out.println(UuidUtil.generateShortUuidWithTime());
		// System.out.println(UuidUtil.generateUuid16());
		// System.out.println(UuidUtil.generateUuid16WithTime());
	}

}
