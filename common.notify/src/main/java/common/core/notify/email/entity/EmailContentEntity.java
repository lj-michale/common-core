package common.core.notify.email.entity;

import java.util.List;

public class EmailContentEntity {
	/**
	 * 收件人
	 */
	private String to;

	/**
	 * 发件人
	 */
	private String from;

	/**
	 * smtp主机
	 */
	private String host;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 附件文件名
	 */
	private List<String> attachments;

	private List<String> attachmentDirectories;

	/**
	 * 邮件主题
	 */
	private String subject;

	/**
	 * 邮件正文
	 */
	private String content;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	public List<String> getAttachmentDirectories() {
		return attachmentDirectories;
	}

	public void setAttachmentDirectories(List<String> attachmentDirectories) {
		this.attachmentDirectories = attachmentDirectories;
	}

}
