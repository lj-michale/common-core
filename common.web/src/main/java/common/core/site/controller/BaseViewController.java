package common.core.site.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.exception.ErrorAssertionException;
import common.core.common.assertion.exception.InfoAssertionException;
import common.core.common.assertion.exception.WarnAssertionException;
import common.core.common.util.ExceptionUtil;
import common.core.site.view.ViewContext;
import common.core.web.context.ResponseContext;
import common.core.web.context.WebSetting;
import common.core.web.exception.InfoMessageException;
import common.core.web.exception.RedirectException;
import common.core.web.exception.WarnMessageException;

public class BaseViewController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(BaseViewController.class);

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String throwable(Throwable exception) {
		logger.error(exception.getMessage(), exception);
		ViewContext.put("exceptionMessage", exception.getMessage());
		ViewContext.put("exceptionStakTrace", ExceptionUtil.stackTrace(exception));
		return errorView();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Object exception(InfoAssertionException sssertionException) {
		logger.info(sssertionException.getMessage(), sssertionException);
		ViewContext.put("exceptionStakTrace", ExceptionUtil.stackTrace(sssertionException));
		ViewContext.put("exceptionMessage", sssertionException.getMessage());
		return errorView();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Object exception(MultipartException multipartException) {
		logger.info(multipartException.getMessage(), multipartException);
		ViewContext.put("exceptionStakTrace", ExceptionUtil.stackTrace(multipartException));
		ViewContext.put("exceptionMessage", multipartException.getMessage());
		return errorView();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Object exception(WarnAssertionException sssertionException) {
		logger.warn(sssertionException.getMessage(), sssertionException);
		ViewContext.put("exceptionStakTrace", ExceptionUtil.stackTrace(sssertionException));
		ViewContext.put("exceptionMessage", sssertionException.getMessage());
		return errorView();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Object exception(ErrorAssertionException sssertionException) {
		logger.error(sssertionException.getMessage(), sssertionException);
		ViewContext.put("exceptionStakTrace", ExceptionUtil.stackTrace(sssertionException));
		ViewContext.put("exceptionMessage", sssertionException.getMessage());
		return errorView();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String infoMessageException(InfoMessageException exception) {
		logger.info(exception.getMessage(), exception);
		ViewContext.put("exceptionStakTrace", ExceptionUtil.stackTrace(exception));
		ViewContext.put("exceptionMessage", exception.getMessage());
		return errorView();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String warnMessageException(WarnMessageException warnMessageException) {
		logger.warn(warnMessageException.getMessage(), warnMessageException);
		ViewContext.put("exceptionStakTrace", ExceptionUtil.stackTrace(warnMessageException));
		ViewContext.put("exceptionMessage", warnMessageException.getMessage());
		return errorView();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.FOUND)
	public void redirectException(RedirectException redirectException) {
		// logger.info("redirct:{}", redirectException.getUrl());
		ResponseContext.addHeader("Location", redirectException.getUrl());
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String bindException(BindException exception) {
		logger.warn(exception.getMessage(), exception);
		ViewContext.put("exceptionStakTrace", ExceptionUtil.stackTrace(exception));
		ViewContext.put("exceptionMessage", exception.getMessage());
		return errorView();
	}



	protected String forward(String url) {
		return "forward:" + url;
	}

	protected String redirect(String url) {
		return "redirect:" + url;
	}

	public String errorView() {
		return WebSetting.get().getErrorView();
	}

	public String notAuthView() {
		return WebSetting.get().getNotAuthView();
	}
}