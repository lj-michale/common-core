package common.core.common.util;

import common.core.common.card.IDCard;

public class IDCardUtil {

	public static boolean verify(String idcard) {
		return new IDCard().verify(idcard);
	}
}
