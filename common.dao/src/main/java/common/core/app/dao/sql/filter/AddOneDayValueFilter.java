package common.core.app.dao.sql.filter;

import java.lang.reflect.Field;
import java.util.Date;

import common.core.common.util.DateUtils;

public class AddOneDayValueFilter implements ValueFilter {

	@Override
	public Object doFilter(Object value, Field field, Object obj) {
		if (null == value || !(value instanceof Date))
			return value;
		return DateUtils.addDate((Date) value, 1);
	}

}
