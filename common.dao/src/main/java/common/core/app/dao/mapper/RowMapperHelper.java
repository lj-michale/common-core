package common.core.app.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

public class RowMapperHelper {

	@SuppressWarnings("unchecked")
	public static <T> RowMapper<T> create(Class<T> entityClass) {
		if (Object[].class.equals(entityClass)) {
			return (RowMapper<T>) ArrayRowMapper.rowMapper();
		}
		return new EntityRowMapper<>(entityClass);
	}

}
