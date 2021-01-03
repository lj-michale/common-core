package common.core.app.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import common.core.app.dao.DaoException;

public class ArrayRowMapper implements RowMapper<Object[]> {
	int size = -1;

	public static RowMapper<Object[]> rowMapper() {
		return new ArrayRowMapper();
	}

	private ArrayRowMapper() {

	}

	@Override
	public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		if (size == -1) {
			size = rs.getMetaData().getColumnCount();
			if (size <= 0)
				throw new DaoException("can't load data");
		}
		Object[] objects = new Object[size];
		for (int i = 0; i < size; i++) {
			objects[i] = rs.getObject(i + 1);
			if (null != objects[i] && objects[i].getClass().isArray()) {
				objects[i] = rs.getString(i + 1);
			}
		}
		return objects;
	}

}
