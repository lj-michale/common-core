package common.core.common.mail;

public class MailHeader implements java.io.Serializable {
	private static final long serialVersionUID = 2346725856792799824L;
	private String id;
	private String from;
	private String contentType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "MailHeader [id=" + id + ", from=" + from + ", contentType=" + contentType + "]";
	}

}
