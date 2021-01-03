package common.core.common.converter;

import java.sql.Date;

public class SqlDateConverter extends DateConverter<Date> {

	@Override
	public Date convertToDate(java.util.Date utilDate) {
		return new Date(utilDate.getTime());
	}

}
