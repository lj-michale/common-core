package common.core.site.page;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public abstract class RowCellFormat extends Format {

	private static final long serialVersionUID = 8877605436376524162L;

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		return this.formatRowCellForma((RowCellFormatParam) obj);
	}

	public abstract StringBuffer formatRowCellForma(RowCellFormatParam rowCellFormatParam);

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return null;
	}

}
