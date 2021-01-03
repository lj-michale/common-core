package common.core.app.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import common.core.app.dao.mapper.RowMapperHelper;

public class ColumnLabelsResultSetExtractor<T> implements ResultSetExtractor<ColumnLabelsResult<T>> {

	private Class<T> requiredType;

	public ColumnLabelsResultSetExtractor(Class<T> requiredType) {
		super();
		this.requiredType = requiredType;
	}

	@Override
	public ColumnLabelsResult<T> extractData(ResultSet rs) throws SQLException, DataAccessException {
		ColumnLabelsResult<T> objectArrayListResult = new ColumnLabelsResult<T>();
		int columnCount = rs.getMetaData().getColumnCount();
		List<String> columnLabels = new ArrayList<String>(columnCount);
		for (int i = 1; i <= columnCount; i++) {
			columnLabels.add(rs.getMetaData().getColumnLabel(i));
		}
		objectArrayListResult.setColumnLabels(columnLabels);
		RowMapperResultSetExtractor<T> rowMapperResultSetExtractor = new RowMapperResultSetExtractor<T>(RowMapperHelper.create(requiredType));
		List<T> dataList = rowMapperResultSetExtractor.extractData(rs);
		objectArrayListResult.setDataList(dataList);
		return objectArrayListResult;
	}

}
