package common.core.common.converter;

import java.util.Date;

public class UtilDateConverter extends DateConverter<Date> {

	@Override
	public Date convertToDate(java.util.Date utilDate) {
		return utilDate;
	}

}
