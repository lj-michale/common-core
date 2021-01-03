package common.core.notify.mail;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件通知响应
 *
 */
public class MailNotifyResponse implements Serializable {

	/** 属性名称 */
	private static final long serialVersionUID = 26184868904596265L;

	/**
	 * 响应流水号
	 */
	private String responseId;

	/**
	 * 响应时间戳
	 */
	private Date responseDateTime;

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public Date getResponseDateTime() {
		return responseDateTime;
	}

	public void setResponseDateTime(Date responseDateTime) {
		this.responseDateTime = responseDateTime;
	}


}
