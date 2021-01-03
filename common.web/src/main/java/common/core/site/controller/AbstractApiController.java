package common.core.site.controller;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NoPermissionException;

import common.core.web.api.ExceptionBaseApiResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
import common.core.web.api.BaseApiResponse;
import common.core.web.exception.CustomErrorException;
import common.core.web.exception.InfoMessageException;
import common.core.web.exception.RedirectException;
import common.core.web.exception.WarnMessageException;
import common.core.web.queue.QueueRequiredException;

public abstract class AbstractApiController implements PreHandler {
	private final Logger logger = LoggerFactory.getLogger(AbstractApiController.class);

	@Override
	public void preHandle() {

	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(Throwable exception) {
		logger.error(exception.getMessage(), exception);
		return new BaseApiResponse("error", "系统发生异常");
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse dataAccessException(DataAccessException exception) {
		logger.error(exception.getMessage(), exception);
		return new BaseApiResponse("error", "数据库访问或者操作发生异常");
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(ServiceException serviceException) {
		logger.debug(serviceException.getMessage(), serviceException);
		return new BaseApiResponse("error", serviceException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(InfoAssertionException sssertionException) {
		logger.info(sssertionException.getMessage(), sssertionException);
		return new BaseApiResponse("error", sssertionException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(MultipartException multipartException) {
		logger.info(multipartException.getMessage(), multipartException);
		return new BaseApiResponse("error", multipartException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(WarnAssertionException sssertionException) {
		logger.info(sssertionException.getMessage(), sssertionException);
		return new BaseApiResponse("error", sssertionException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(ErrorAssertionException sssertionException) {
		logger.info(sssertionException.getMessage(), sssertionException);
		return new BaseApiResponse("error", sssertionException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(ControllerException controllerException) {
		logger.debug(controllerException.getMessage(), controllerException);
		return new BaseApiResponse("error", controllerException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(DaoException daoException) {
		logger.debug(daoException.getMessage(), daoException);
		return new BaseApiResponse("error", daoException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(QueueRequiredException queueRequiredException) {
		logger.debug(queueRequiredException.getMessage(), queueRequiredException);
		return new BaseApiResponse("error", queueRequiredException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(InfoMessageException infoMessageException) {
		logger.debug(infoMessageException.getMessage(), infoMessageException);
		return new BaseApiResponse("error", infoMessageException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(WarnMessageException warnMessageException) {
		logger.info(warnMessageException.getMessage(), warnMessageException);
		return new BaseApiResponse("error", warnMessageException.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(NoPermissionException noPermissionException) {
		logger.warn(noPermissionException.getMessage(), noPermissionException);
		return new BaseApiResponse("error", noPermissionException.getMessage());
	}

    @ExceptionHandler
    @ResponseBody
    public BaseApiResponse exception(CustomErrorException customErrorException){
        return new BaseApiResponse(customErrorException.getErrorCode(), customErrorException.getErrorMsg());
    }

    @ExceptionHandler
	@ResponseBody
	public BaseApiResponse exception(RedirectException redirectException) {
		// logger.info("redirct:{}", redirectException.getUrl());
		return new BaseApiResponse("redirct", redirectException.getUrl());
	}

	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse bindException(BindException bindException) {
		logger.info(bindException.getMessage(), bindException);
		List<String> messages = new ArrayList<>();
		List<common.core.site.view.FieldError> filedErrors = new ArrayList<common.core.site.view.FieldError>();
		if (null != bindException.getFieldErrors() && bindException.getFieldErrors().size() > 0) {
			for (FieldError fieldError : bindException.getFieldErrors()) {
				filedErrors.add(new common.core.site.view.FieldError(fieldError.getField(), fieldError.getDefaultMessage()));
				messages.add(fieldError.getDefaultMessage());
			}
		}
		return new BaseApiResponse("error", messages.toString());
	}

	/**
	 * Exception to be thrown when validation on an argument annotated with @Valid fails.
	 * @param methodArgumentNotValidException
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	public BaseApiResponse handle(MethodArgumentNotValidException methodArgumentNotValidException) {
		logger.info(methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
		BindingResult bindingResult = ((MethodArgumentNotValidException) methodArgumentNotValidException).getBindingResult();
		return new BaseApiResponse("error", bindingResult.getFieldError().getDefaultMessage());
	}
}
