package common.core.site.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import common.core.app.dao.DaoException;
import common.core.app.exception.ControllerException;
import common.core.app.exception.ServiceException;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.exception.ErrorAssertionException;
import common.core.common.assertion.exception.InfoAssertionException;
import common.core.common.assertion.exception.WarnAssertionException;
import common.core.site.handler.PreHandler;
import common.core.site.view.BaseRestResult;
import common.core.web.exception.InfoMessageException;
import common.core.web.exception.RedirectException;
import common.core.web.exception.WarnMessageException;
import common.core.web.queue.QueueRequiredException;

public class BaseRestController extends BaseController implements PreHandler {
	private final Logger logger = LoggerFactory.getLogger(BaseRestController.class);

	@Override
	public void preHandle() {

	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(Throwable exception) {
		logger.error(exception.getMessage(), exception);
		return BaseRestResult.error("系统发生异常");
	}

	@ExceptionHandler
	@ResponseBody
	public Object dataAccessException(DataAccessException exception) {
		logger.error(exception.getMessage(), exception);
		return BaseRestResult.error("数据库访问或者操作发生异常");
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(ServiceException serviceException) {
		logger.debug(serviceException.getMessage(), serviceException);
		return BaseRestResult.warn(serviceException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(InfoAssertionException sssertionException) {
		logger.info(sssertionException.getMessage(), sssertionException);
		return BaseRestResult.warn(sssertionException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(MultipartException multipartException) {
		logger.info(multipartException.getMessage(), multipartException);
		return BaseRestResult.warn(multipartException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(WarnAssertionException sssertionException) {
		logger.warn(sssertionException.getMessage(), sssertionException);
		return BaseRestResult.warn(sssertionException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(ErrorAssertionException sssertionException) {
		logger.error(sssertionException.getMessage(), sssertionException);
		return BaseRestResult.error(sssertionException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(ControllerException controllerException) {
		logger.debug(controllerException.getMessage(), controllerException);
		return BaseRestResult.warn(controllerException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(DaoException daoException) {
		logger.debug(daoException.getMessage(), daoException);
		return BaseRestResult.warn(daoException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(QueueRequiredException queueRequiredException) {
		logger.debug(queueRequiredException.getMessage(), queueRequiredException);
		return BaseRestResult.warn(queueRequiredException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(InfoMessageException infoMessageException) {
		logger.debug(infoMessageException.getMessage(), infoMessageException);
		return BaseRestResult.info(infoMessageException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(WarnMessageException warnMessageException) {
		logger.warn(warnMessageException.getMessage(), warnMessageException);
		return BaseRestResult.warn(warnMessageException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(RedirectException redirectException) {
		// logger.info("redirct:{}", redirectException.getUrl());
		return BaseRestResult.redirect(redirectException.getUrl());
	}

	@ExceptionHandler
	@ResponseBody
	public Object bindException(BindException bindException) {
		logger.info(bindException.getMessage(), bindException);
		List<common.core.site.view.FieldError> filedErrors = new ArrayList<common.core.site.view.FieldError>();
		if (null != bindException.getFieldErrors() && bindException.getFieldErrors().size() > 0) {
			for (FieldError fieldError : bindException.getFieldErrors()) {
				filedErrors.add(new common.core.site.view.FieldError(fieldError.getField(), fieldError.getDefaultMessage()));
			}
		}
		return BaseRestResult.filedErrors(filedErrors.toArray(new common.core.site.view.FieldError[filedErrors.size()]));
	}
}