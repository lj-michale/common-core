package common.core.app.log;

public interface Logger {

	final public String ROOT_LOGGER_NAME = "ROOT";

	public String getName();

	public void trace(String format, Object... arguments);

	public void trace(String msg, Throwable t);
	
	public boolean isTraceEnabled();

	public void debug(String format, Object... arguments);

	public void debug(String msg, Throwable t);
	
	public boolean isDebugEnabled();

	public void info(String format, Object... arguments);

	public void info(String msg, Throwable t);
	
	public boolean isInfoEnabled();

	public void warn(String format, Object... arguments);

	public void warn(String msg, Throwable t);
	
	public boolean isWarnEnabled();

	public void error(String format, Object... arguments);

	public void error(String msg, Throwable t);

	public boolean isErrorEnabled();

}