package common.core.app.log.slf4j;

import common.core.app.log.Logger;

public class Slf4jLogger implements Logger {

	private final org.slf4j.Logger logger;

	public Slf4jLogger(org.slf4j.Logger logger) {
		this.logger = logger;
	}

	@Override
	public String getName() {
		return logger.getName();
	}

	@Override
	public void trace(String format, Object... arguments) {
		this.logger.trace(format, arguments);
	}

	@Override
	public void trace(String msg, Throwable t) {
		this.logger.trace(msg, t);
	}

	@Override
	public void debug(String format, Object... arguments) {
		this.logger.debug(format, arguments);
	}

	@Override
	public void debug(String msg, Throwable t) {
		this.logger.debug(msg, t);
	}

	@Override
	public void info(String format, Object... arguments) {
		this.logger.info(format, arguments);
	}

	@Override
	public void info(String msg, Throwable t) {
		this.logger.info(msg, t);
	}

	@Override
	public void warn(String format, Object... arguments) {
		this.logger.warn(format, arguments);
	}

	@Override
	public void warn(String msg, Throwable t) {
		this.logger.warn(msg, t);
	}

	@Override
	public void error(String format, Object... arguments) {
		this.logger.error(format, arguments);
	}

	@Override
	public void error(String msg, Throwable t) {
		this.logger.error(msg, t);
	}

	@Override
	public boolean isTraceEnabled() {
		return this.logger.isTraceEnabled();
	}

	@Override
	public boolean isDebugEnabled() {
		return this.logger.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return this.logger.isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return this.logger.isWarnEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return this.logger.isErrorEnabled();
	}

}
