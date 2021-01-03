package common.core.site.page;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

public class NumberCellFormat extends Format {

	private static final long serialVersionUID = -3010519022864628377L;

	private DecimalFormat decimalFormat;

	public NumberCellFormat(String pattern) {
		this.decimalFormat = new DecimalFormat(pattern);
	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		toAppendTo.append(decimalFormat.format(obj));
		return toAppendTo;
	}

	public static void main(String[] args) {
		NumberCellFormat format = new NumberCellFormat("#0.00");
		System.out.println(format.format(0));
		System.out.println(format.format(0.01));
		System.out.println(format.format(0.001));
		System.out.println(format.format(10.001));
		System.out.println(format.format(null));
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		try {
			return decimalFormat.parseObject(source);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
