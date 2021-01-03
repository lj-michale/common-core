package common.core.common.card;

import java.util.HashMap;

public class IDCard {
	private String error;

	// wi =2(n-1)(mod 11)
	final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
	// verify digit
	final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
	private int[] ai = new int[18];
	private static String[] _areaCode = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91" };
	private static HashMap<String, Integer> dateMap;
	private static HashMap<String, String> areaCodeMap;
	static {
		dateMap = new HashMap<String, Integer>();
		dateMap.put("01", 31);
		dateMap.put("02", null);
		dateMap.put("03", 31);
		dateMap.put("04", 30);
		dateMap.put("05", 31);
		dateMap.put("06", 30);
		dateMap.put("07", 31);
		dateMap.put("08", 31);
		dateMap.put("09", 30);
		dateMap.put("10", 31);
		dateMap.put("11", 30);
		dateMap.put("12", 31);
		areaCodeMap = new HashMap<String, String>();
		for (String code : _areaCode) {
			areaCodeMap.put(code, null);
		}
	}

	// 验证身份证位数,15位和18位身份证
	public boolean verifyLength(String code) {
		int length = code.length();
		if (length == 15 || length == 18) {
			return true;
		} else {
			error = "错误：输入的身份证号不是15位和18位的";
			return false;
		}
	}

	// 判断地区码
	public boolean verifyAreaCode(String code) {
		String areaCode = code.substring(0, 2);
		// Element child= _areaCodeElement.getChild("_"+areaCode);
		if (areaCodeMap.containsKey(areaCode)) {
			return true;
		} else {
			error = "错误：输入的身份证号的地区码(1-2位)[" + areaCode + "]不符合中国行政区划分代码规定(GB/T2260-1999)";
			return false;
		}
	}

	// 判断月份和日期
	public boolean verifyBirthdayCode(String code) {
		// 验证月份
		String month = code.substring(10, 12);
		boolean isEighteenCode = (18 == code.length());
		if (!dateMap.containsKey(month)) {
			error = "错误：输入的身份证号" + (isEighteenCode ? "(11-12位)" : "(9-10位)") + "不存在[" + month + "]月份,不符合要求(GB/T7408)";
			return false;
		}
		// 验证日期
		String dayCode = code.substring(12, 14);
		Integer day = dateMap.get(month);
		String yearCode = code.substring(6, 10);
		Integer year = Integer.valueOf(yearCode);

		// 非2月的情况
		if (day != null) {
			if (Integer.valueOf(dayCode) > day || Integer.valueOf(dayCode) < 1) {
				error = "错误：输入的身份证号" + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "[" + dayCode + "]号不符合小月1-30天大月1-31天的规定(GB/T7408)";
				return false;
			}
		}
		// 2月的情况
		else {
			// 闰月的情况
			if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
				if (Integer.valueOf(dayCode) > 29 || Integer.valueOf(dayCode) < 1) {
					error = "错误：输入的身份证号" + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "[" + dayCode + "]号在" + year + "闰年的情况下未符合1-29号的规定(GB/T7408)";
					return false;
				}
			}
			// 非闰月的情况
			else {
				if (Integer.valueOf(dayCode) > 28 || Integer.valueOf(dayCode) < 1) {
					error = "错误：输入的身份证号" + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "[" + dayCode + "]号在" + year + "平年的情况下未符合1-28号的规定(GB/T7408)";
					return false;
				}
			}
		}
		return true;
	}

	// 验证身份除了最后位其他的是否包含字母
	public boolean containsAllNumber(String code) {
		String str = "";
		if (code.length() == 15) {
			str = code.substring(0, 15);
		} else if (code.length() == 18) {
			str = code.substring(0, 17);
		}
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (!(ch[i] >= '0' && ch[i] <= '9')) {
				error = "错误：输入的身份证号第" + (i + 1) + "位包含字母";
				return false;
			}
		}
		return true;
	}

	public String getCodeError() {
		return error;
	}

	// 验证身份证
	public boolean verify(String idcard) {
		error = "";
		// 验证身份证位数,15位和18位身份证
		if (!verifyLength(idcard)) {
			return false;
		}
		// 验证身份除了最后位其他的是否包含字母
		if (!containsAllNumber(idcard)) {
			return false;
		}

		// 如果是15位的就转成18位的身份证
		String eifhteencard = "";
		if (idcard.length() == 15) {
			eifhteencard = uptoeighteen(idcard);
		} else {
			eifhteencard = idcard;
		}
		// 验证身份证的地区码
		if (!verifyAreaCode(eifhteencard)) {
			return false;
		}
		// 判断月份和日期
		if (!verifyBirthdayCode(eifhteencard)) {
			return false;
		}
		// 验证18位校验码,校验码采用ISO 7064：1983，MOD 11-2 校验码系统
		if (!verifyMOD(eifhteencard)) {
			return false;
		}
		return true;
	}

	// 验证18位校验码,校验码采用ISO 7064：1983，MOD 11-2 校验码系统
	public boolean verifyMOD(String code) {
		String verify = code.substring(17, 18);
		if ("x".equals(verify)) {
			code = code.replaceAll("x", "X");
			verify = "X";
		}
		String verifyIndex = getVerify(code);
		if (verify.equals(verifyIndex)) {
			return true;
		}
		// int x=17;
		// if(code.length()==15){
		// x=14;
		// }
		error = "错误：输入的身份证号最末尾的数字验证码错误";
		return false;
	}

	// 获得校验位
	public String getVerify(String eightcardid) {
		int remaining = 0;

		if (eightcardid.length() == 18) {
			eightcardid = eightcardid.substring(0, 17);
		}

		if (eightcardid.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eightcardid.substring(i, i + 1);
				ai[i] = Integer.parseInt(k);
			}

			for (int i = 0; i < 17; i++) {
				sum = sum + wi[i] * ai[i];
			}
			remaining = sum % 11;
		}

		return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
	}

	// 15位转18位身份证
	public String uptoeighteen(String fifteencardid) {
		String eightcardid = fifteencardid.substring(0, 6);
		eightcardid = eightcardid + "19";
		eightcardid = eightcardid + fifteencardid.substring(6, 15);
		eightcardid = eightcardid + getVerify(eightcardid);
		return eightcardid;
	}
}
