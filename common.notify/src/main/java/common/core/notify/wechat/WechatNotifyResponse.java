package common.core.notify.wechat;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信消息通知响应
 *
 */
public class WechatNotifyResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7192285027225223609L;

	/**
	 * 响应流水号
	 */
	private int result;

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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}




}
