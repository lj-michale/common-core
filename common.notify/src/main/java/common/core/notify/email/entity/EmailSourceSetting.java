package common.core.notify.email.entity;

import javax.xml.bind.annotation.XmlElement;

public class EmailSourceSetting {
	/**
	 * 收件人
	 */

	@XmlElement(name = "toemail", required = true)
	private String to;
	
	/**
	 * 金融总收件人
	 */

	@XmlElement(name = "toemailprocess", required = true)
	private String toProcess;

	/**
	 * 平台信贷经理收件人
	 */

	@XmlElement(name = "tocreditmanager", required = true)
	private String toCreditManager;
	/**
	 * 发件人
	 */
	@XmlElement(name = "fromemail", required = true)
	private String from;

	/**
	 * smtp主机
	 */
	@XmlElement(name = "host", required = true)
	private String host;

	/**
	 * 密码
	 */
	@XmlElement(name = "fromemailpd", required = true)
	private String password;

	/**
	 * 存放路径
	 */
	@XmlElement(name = "filepath", required = true)
	public String filepath;
	/**
	 * 用户名
	 */
	/*
	 * private String username;
	 * 
	 *//**
		 * 附件文件名
		 */
	/*
	 * private List<String> attachments;
	 * 
	 *//**
		 * 邮件主题
		 */

	/*
	 * private String subject;
	 * 
	 *//**
		 * 邮件正文
		 *//*
		 * private String content;
		 */

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getToProcess() {
		return toProcess;
	}

	public void setToProcess(String toProcess) {
		this.toProcess = toProcess;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getToCreditManager() {
		return toCreditManager;
	}

	public void setToCreditManager(String toCreditManager) {
		this.toCreditManager = toCreditManager;
	}

	/*
	 * public String getUsername() { return username; }
	 * 
	 * public void setUsername(String username) { this.username = username; }
	 * 
	 * public String getSubject() { return subject; }
	 * 
	 * public void setSubject(String subject) { this.subject = subject; }
	 * 
	 * public String getContent() { return content; }
	 * 
	 * public void setContent(String content) { this.content = content; }
	 * 
	 * public List<String> getAttachments() { return attachments; }
	 * 
	 * public void setAttachments(List<String> attachments) { this.attachments =
	 * attachments; }
	 */
}
