package common.core.app.log.slf4j;

import java.io.File;

import common.core.app.log.Level;
import common.core.app.log.Logger;
import common.core.app.log.LoggerAdapter;

public class Slf4jLoggerAdapter implements LoggerAdapter {

	public Logger getLogger(String key) {
		return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(key));
	}

	public Logger getLogger(Class<?> key) {
		return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(key));
	}

	private Level level;

	private File file;

	public void setLevel(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
