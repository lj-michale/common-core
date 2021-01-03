package common.core.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import common.core.app.dao.mapper.ArrayRowMapper;

public class TableRowMapperResultSetExtractor extends RowMapperResultSetExtractor<Object[]> {

	public static TableRowMapperResultSetExtractor rowMapper() {
		return new TableRowMapperResultSetExtractor();
	}

	private TableRowMapperResultSetExtractor() {
		super(ArrayRowMapper.rowMapper());
	}

	private String[] columnNames;

	@Override
	public List<Object[]> extractData(ResultSet rs) throws SQLException {
		int count = rs.getMetaData().getColumnCount();
		this.columnNames = new String[count];
		for (int i = 0; i < count; i++) {
			this.columnNames[i] = rs.getMetaData().getColumnLabel(i + 1);
		}
		return super.extractData(rs);
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

}
