package common.core.site.session;

public class SessionSpec<T> {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SessionSpec(String name) {
		super();
		this.name = name;
	}

	public void setValue(T value) {
		SessionContext.setAttribute(this, value);
	}

	public T getValue() {
		return SessionContext.getAttribute(this);
	}

	public void remove() {
		SessionContext.removeAttribute(this);
	}
}
