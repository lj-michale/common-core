
package common.core.app.log.support;

import common.core.app.log.Logger;

public class SafeLogger implements common.core.app.log.Logger {

	private Logger logger;

	public SafeLogger(Logger logger) {
		this.logger = logger;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getName() {
		return logger.getName();
	}

	public void trace(String format, Object... arguments) {
		logger.trace(format, arguments);
	}

	public void trace(String msg, Throwable t) {
		logger.trace(msg, t);
	}

	public void debug(String format, Object... arguments) {
		logger.debug(format, arguments);
	}

	public void debug(String msg, Throwable t) {
		logger.debug(msg, t);
	}

	public void info(String format, Object... arguments) {
		logger.info(format, arguments);
	}

	public void info(String msg, Throwable t) {
		logger.info(msg, t);
	}

	public void warn(String format, Object... arguments) {
		logger.warn(format, arguments);
	}

	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	public void error(String format, Object... arguments) {
		logger.error(format, arguments);
	}

	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}


}