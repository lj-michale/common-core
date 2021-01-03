package common.core.site.page;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import common.core.common.util.StringEscapeUtil;

public class CellWidthFormat extends Format {
	private static final long serialVersionUID = -6049040666737385806L;
	private Integer width;

	public CellWidthFormat(Integer width) {
		super();
		this.width = width;
	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		if (null != obj) {
			toAppendTo.append("<span style=\" word-break: break-all;word-wrap: break-word;white-space: normal;display:inline-block;width:").append(this.width).append("px;\">").append(StringEscapeUtil.escapeHtml4(obj.toString())).append("</span>");
		}
		return toAppendTo;
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return null;
	}

}
