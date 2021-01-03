package common.core.common.converter;

import java.sql.Timestamp;

public class SqlTimestampConverter extends DateConverter<Timestamp> {

	@Override
	public Timestamp convertToDate(java.util.Date utilDate) {
		return new Timestamp(utilDate.getTime());
	}

}
