package common.core.web.response;

import javax.xml.bind.annotation.XmlRootElement;

import common.core.common.util.ExceptionUtil;
import common.core.web.context.RequestContext;

@XmlRootElement
public class ExceptionResponse extends BaseResponse {
	private String requestId;
	private String exceptionName;
	private String exceptionMessage;
	private String exceptionDetail;

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	public String getExceptionDetail() {
		return exceptionDetail;
	}

	public void setExceptionDetail(String exceptionDetail) {
		this.exceptionDetail = exceptionDetail;
	}

	public ExceptionResponse(Throwable exception) {
		this.requestId = RequestContext.getRequestId();
		this.exceptionName = exception.getClass().getName();
		this.exceptionMessage = exception.getMessage();
		this.exceptionDetail = ExceptionUtil.stackTrace(exception);
	}

	public ExceptionResponse() {
		this.requestId = RequestContext.getRequestId();
	}

}
