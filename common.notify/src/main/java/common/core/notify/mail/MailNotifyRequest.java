package common.core.notify.mail;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class MailNotifyRequest implements Serializable {
	/** 属性名称 */
	private static final long serialVersionUID = -1620154862878843903L;
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
	 * 发送类型，参照common.core.notify.mail.MailSendType
	 */
	@NotEmpty
	private String mailSendType;
	/**
	 * 定时发送时间戳
	 */
	private Date scheduleSendDateTime;
	/**
	 * 接收邮件，可以是多个邮件，用逗号分割
	 */
	private String recipientTo;

	/**
	 * 抄送邮件，可以是多个邮件，用逗号分割
	 */
	private String recipientCc;

	/**
	 * 暗抄邮件，可以是多个邮件，用逗号分割
	 */
	private String recipientBcc;

	/**
	 * 附件的ftp地址
	 */
	private String attachmentPath;

	/**
	 * 标题
	 */
	@NotEmpty
	private String title;

	/**
	 * 内容
	 */
	@NotEmpty
	private String content;

	/**
	 * 发件人
	 */
	private String from;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getMailSendType() {
		return mailSendType;
	}

	public void setMailSendType(String mailSendType) {
		this.mailSendType = mailSendType;
	}

	public String getRecipientTo() {
		return recipientTo;
	}

	public void setRecipientTo(String recipientTo) {
		this.recipientTo = recipientTo;
	}

	public String getRecipientCc() {
		return recipientCc;
	}

	public void setRecipientCc(String recipientCc) {
		this.recipientCc = recipientCc;
	}

	public String getRecipientBcc() {
		return recipientBcc;
	}

	public void setRecipientBcc(String recipientBcc) {
		this.recipientBcc = recipientBcc;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Date getRequestDateTime() {
		return requestDateTime;
	}

	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}

	public Date getScheduleSendDateTime() {
		return scheduleSendDateTime;
	}

	public void setScheduleSendDateTime(Date scheduleSendDateTime) {
		this.scheduleSendDateTime = scheduleSendDateTime;
	}

}
