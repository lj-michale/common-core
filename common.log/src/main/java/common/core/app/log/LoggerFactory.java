
package common.core.app.log;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import common.core.app.log.slf4j.Slf4jLoggerAdapter;
import common.core.app.log.support.SafeLogger;

public class LoggerFactory {

	private LoggerFactory() {
	}

	private static volatile LoggerAdapter LOGGER_ADAPTER;

	private static final ConcurrentMap<String, SafeLogger> LOGGERS = new ConcurrentHashMap<String, SafeLogger>();

	// 查找常用的日志框架
	static {
		setLoggerAdapter(new Slf4jLoggerAdapter());
	}

	/**
	 * 设置日志输出器供给器
	 * 
	 * @param loggerAdapter
	 *            日志输出器供给器
	 */
	public static void setLoggerAdapter(LoggerAdapter loggerAdapter) {
		if (loggerAdapter != null) {
			Logger logger = loggerAdapter.getLogger(LoggerFactory.class.getName());
			logger.info("using logger: " + loggerAdapter.getClass().getName());
			LoggerFactory.LOGGER_ADAPTER = loggerAdapter;
			for (Map.Entry<String, SafeLogger> entry : LOGGERS.entrySet()) {
				entry.getValue().setLogger(LOGGER_ADAPTER.getLogger(entry.getKey()));
			}
		}
	}

	/**
	 * 获取日志输出器
	 * 
	 * @param key
	 *            分类键
	 * @return 日志输出器, 后验条件: 不返回null.
	 */
	public static Logger getLogger(Class<?> key) {
		SafeLogger logger = LOGGERS.get(key.getName());
		if (logger == null) {
			LOGGERS.putIfAbsent(key.getName(), new SafeLogger(LOGGER_ADAPTER.getLogger(key)));
			logger = LOGGERS.get(key.getName());
		}
		return logger;
	}

	/**
	 * 获取日志输出器
	 * 
	 * @param key
	 *            分类键
	 * @return 日志输出器, 后验条件: 不返回null.
	 */
	public static Logger getLogger(String key) {
		SafeLogger logger = LOGGERS.get(key);
		if (logger == null) {
			LOGGERS.putIfAbsent(key, new SafeLogger(LOGGER_ADAPTER.getLogger(key)));
			logger = LOGGERS.get(key);
		}
		return logger;
	}

	/**
	 * 动态设置输出日志级别
	 * 
	 * @param level
	 *            日志级别
	 */
	public static void setLevel(Level level) {
		LOGGER_ADAPTER.setLevel(level);
	}

	/**
	 * 获取日志级别
	 * 
	 * @return 日志级别
	 */
	public static Level getLevel() {
		return LOGGER_ADAPTER.getLevel();
	}

	/**
	 * 获取日志文件
	 * 
	 * @return 日志文件
	 */
	public static File getFile() {
		return LOGGER_ADAPTER.getFile();
	}

}