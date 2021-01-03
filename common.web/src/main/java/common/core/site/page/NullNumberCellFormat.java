package common.core.site.page;

import java.text.FieldPosition;

public class NullNumberCellFormat extends NumberCellFormat {

	private static final long serialVersionUID = -3010519022864628377L;

	private Object defaultValue;

	public NullNumberCellFormat(String pattern, Object defaultValue) {
		super(pattern);
		this.defaultValue = defaultValue;
	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		return super.format(null == obj ? defaultValue : obj, toAppendTo, pos);
	}

	public static void main(String[] args) {
		NullNumberCellFormat format = new NullNumberCellFormat("#0.00", 0);
		System.out.println(format.format(0));
		System.out.println(format.format(0.01));
		System.out.println(format.format(0.001));
		System.out.println(format.format(10.001));
		System.out.println(format.format(null));
	}

}
