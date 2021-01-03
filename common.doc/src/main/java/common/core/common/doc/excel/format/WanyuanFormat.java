package common.core.common.doc.excel.format;

import java.math.BigDecimal;

import common.core.common.util.ClassUtil;

public class WanyuanFormat implements WriteCellFormat {

	public WanyuanFormat() {
	}

	@Override
	public Object format(Object obj) {
		if (null == obj || !ClassUtil.isNumberType(obj.getClass())) {
			return obj;
		}
		BigDecimal num = new BigDecimal(obj.toString());
		return num.divide(new BigDecimal("10000"));
	}

}
