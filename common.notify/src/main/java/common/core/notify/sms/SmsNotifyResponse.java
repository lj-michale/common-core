package common.core.notify.sms;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信通知响应
 *
 */
public class SmsNotifyResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7192285027225223609L;

	/**
	 * 响应流水号
	 */
	private String responseId;

	/**
	 * 响应时间戳
	 */
	private Date responseDateTime;

	public Date getResponseDateTime() {
		return responseDateTime;
	}

	public void setResponseDateTime(Date responseDateTime) {
		this.responseDateTime = responseDateTime;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}


}
