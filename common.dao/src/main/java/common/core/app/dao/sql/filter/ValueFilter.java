package common.core.app.dao.sql.filter;

import java.lang.reflect.Field;

public interface ValueFilter {
	public Object doFilter(Object value, Field field, Object obj);
}
