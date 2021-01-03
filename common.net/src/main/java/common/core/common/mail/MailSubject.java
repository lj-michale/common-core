package common.core.common.mail;

public class MailSubject extends MailHeader {
	private static final long serialVersionUID = 2346725856792799824L;
	private String subject;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "MailSubject [subject=" + subject + ", toString()=" + super.toString() + "]";
	}

}
