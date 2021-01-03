package common.core.notify.sms;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class SmsNotifyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6971440415473790780L;
	/**
	 * 请求流水号（唯一）
	 */
	@NotEmpty
	private String requestId;
	/**
	 * 请求时间戳
	 */
	@NotNull
	private Date requestDateTime;
	/**
	 * 业务编码
	 */
	@NotEmpty
	private String bizCode;
	/**
	 * 系统编号，每个调用的系统分配一个唯一的编号
	 */
	@NotEmpty
	private String systemCode;
	/**
	 * 发送类型，参照common.core.notify.sms.SmsSendType
	 */
	@NotEmpty
	private String smsSendType;
	/**
	 * 定时发送时间戳
	 */
	private Date scheduleSendDateTime;
	/**
	 * 短信接收人号码
	 */
	@NotEmpty
	private String recipientNumber;
	/**
	 * 短信内容
	 */
	@NotEmpty
	private String content;
	/**
	 * 接收人姓名
	 */
	@NotEmpty
	private String receiveName;
	/**
	 * 发送人姓名
	 */
	@NotEmpty
	private String sendName;

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Date getRequestDateTime() {
		return requestDateTime;
	}

	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getSmsSendType() {
		return smsSendType;
	}

	public void setSmsSendType(String smsSendType) {
		this.smsSendType = smsSendType;
	}

	public Date getScheduleSendDateTime() {
		return scheduleSendDateTime;
	}

	public void setScheduleSendDateTime(Date scheduleSendDateTime) {
		this.scheduleSendDateTime = scheduleSendDateTime;
	}

	public String getRecipientNumber() {
		return recipientNumber;
	}

	public void setRecipientNumber(String recipientNumber) {
		this.recipientNumber = recipientNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

}
