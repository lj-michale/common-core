package common.core.common.mail;

public class MailBody extends MailSubject {

	private static final long serialVersionUID = 169203048465479210L;

	private String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "MailBody [body=" + body + ", toString()=" + super.toString() + "]";
	}

}
