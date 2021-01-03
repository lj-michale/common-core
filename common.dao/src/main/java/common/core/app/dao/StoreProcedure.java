package common.core.app.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;

public class StoreProcedure {
	private final Logger logger = LoggerFactory.getLogger(StoreProcedure.class);

	static String buildResultSetIndex(int index) {
		return "result-set-" + index;
	}

	private final String name;
	@SuppressWarnings("rawtypes")
	private final Map<String, RowMapper> rowMappers = new LinkedHashMap<>();
	private final Map<String, Object> params = new HashMap<>();
	private int resultSetIndex = 0;

	public StoreProcedure(String name) {
		this.name = name;
	}

	public StoreProcedure addRowMapper(@SuppressWarnings("rawtypes") RowMapper rowMapper) {
		rowMappers.put(buildResultSetIndex(resultSetIndex), rowMapper);
		resultSetIndex++;
		return this;
	}

	public StoreProcedure addOutParamRowMapper(String param, @SuppressWarnings("rawtypes") RowMapper rowMapper) {
		rowMappers.put(param.toLowerCase(), rowMapper);
		return this;
	}

	public StoreProcedure setParam(String param, Object value) {
		params.put(param, value);
		return this;
	}

	ResultSets execute(JdbcTemplate jdbcTemplate) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(name).withReturnValue();

		for (@SuppressWarnings("rawtypes") Map.Entry<String, RowMapper> entry : rowMappers.entrySet()) {
			jdbcCall.returningResultSet(entry.getKey(), entry.getValue());
		}
		Map<String, Object> results = jdbcCall.execute(params);
		logger.debug("execute jdbc call, sp={}, params={}", name, params);
		return new ResultSets(results);
	}

	String getName() {
		return name;
	}
}
