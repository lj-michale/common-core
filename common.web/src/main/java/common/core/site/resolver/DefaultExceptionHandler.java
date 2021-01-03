package common.core.site.resolver;

import common.core.app.lang.exception.IgnoreException;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;

public class DefaultExceptionHandler implements ExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	private Long counter = 0L;

	@Override
	public void handler(Throwable e) {
		this.counter++;
		if (isIgnore(e))
			return;
		logger.info(e.getMessage(), e);
	}

	boolean isIgnore(Throwable e) {
		if (null == e)
			return true;
		return e.getClass().isAnnotationPresent(IgnoreException.class);
	}

}
