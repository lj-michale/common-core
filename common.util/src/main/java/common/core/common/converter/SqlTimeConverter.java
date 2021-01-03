package common.core.common.converter;


public class SqlTimeConverter extends DateConverter<java.sql.Time> {

	@Override
	public java.sql.Time convertToDate(java.util.Date utilDate) {
		return new java.sql.Time(utilDate.getTime());
	}

}
