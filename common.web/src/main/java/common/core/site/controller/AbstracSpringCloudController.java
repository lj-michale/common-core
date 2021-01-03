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
import common.core.web.exception.InfoMessageException;
import common.core.web.exception.RedirectException;
import common.core.web.exception.WarnMessageException;
import common.core.web.queue.QueueRequiredException;

/**
 * @description: 内部服务调用异常处理,返回原始claas信息
 * @author: jia.chen
 * @create: 2018/12/15 14:39
 **/
public abstract class AbstracSpringCloudController implements PreHandler {
	private final Logger logger = LoggerFactory.getLogger(AbstracSpringCloudController.class);

	@Override
	public void preHandle() {

	}

	@ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseApiResponse exception(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return new ExceptionBaseApiResponse("error", exception);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseApiResponse throwable(Throwable exception) {
        logger.error(exception.getMessage(), exception);
        return new ExceptionBaseApiResponse("error", new Exception("系统异常"));
    }

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse dataAccessException(DataAccessException exception) {
		logger.error(exception.getMessage(), exception);
		return new ExceptionBaseApiResponse("error", exception);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse exception(ServiceException serviceException) {
		logger.debug(serviceException.getMessage(), serviceException);
		return new ExceptionBaseApiResponse("warn", serviceException);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse exception(InfoAssertionException sssertionException) {
		logger.info(sssertionException.getMessage(), sssertionException);
		return new ExceptionBaseApiResponse("warn", sssertionException);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse exception(MultipartException multipartException) {
		logger.info(multipartException.getMessage(), multipartException);
		return new ExceptionBaseApiResponse("warn",multipartException);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse exception(WarnAssertionException sssertionException) {
		logger.info(sssertionException.getMessage(), sssertionException);
		return new ExceptionBaseApiResponse("warn", sssertionException);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse exception(ErrorAssertionException sssertionException) {
		logger.info(sssertionException.getMessage(), sssertionException);
		return new ExceptionBaseApiResponse("error", sssertionException);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse exception(ControllerException controllerException) {
		logger.debug(controllerException.getMessage(), controllerException);
		return new ExceptionBaseApiResponse("warn", controllerException);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse exception(DaoException daoException) {
		logger.debug(daoException.getMessage(), daoException);
		return new ExceptionBaseApiResponse("warn",  daoException);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse exception(QueueRequiredException queueRequiredException) {
		logger.debug(queueRequiredException.getMessage(), queueRequiredException);
		return new ExceptionBaseApiResponse("info", queueRequiredException);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse exception(InfoMessageException infoMessageException) {
		logger.debug(infoMessageException.getMessage(), infoMessageException);
		return new ExceptionBaseApiResponse("info", infoMessageException);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse exception(WarnMessageException warnMessageException) {
		logger.info(warnMessageException.getMessage(), warnMessageException);
		return new ExceptionBaseApiResponse("warn", warnMessageException);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse exception(NoPermissionException noPermissionException) {
		logger.warn(noPermissionException.getMessage(), noPermissionException);
		return new ExceptionBaseApiResponse("warn", noPermissionException);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse exception(RedirectException redirectException) {
		// logger.info("redirct:{}", redirectException.getUrl());
		return new ExceptionBaseApiResponse("redirct", redirectException.getUrl(), redirectException);
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
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
		return new ExceptionBaseApiResponse("warn", messages.toString(), new ControllerException(messages.toString()));
	}

	/**
	 * Exception to be thrown when validation on an argument annotated with @Valid fails.
	 * @param methodArgumentNotValidException
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseApiResponse handle(MethodArgumentNotValidException methodArgumentNotValidException) {
		logger.info(methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
		BindingResult bindingResult = ((MethodArgumentNotValidException) methodArgumentNotValidException).getBindingResult();
		return new ExceptionBaseApiResponse("info",new InfoMessageException(bindingResult.getFieldError().getDefaultMessage()));
	}
}
