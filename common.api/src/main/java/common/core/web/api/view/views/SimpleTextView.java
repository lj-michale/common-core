package common.core.web.api.view.views;

@ApiResponsePlugin
public class SimpleTextView {

	private String title;
	private String type;
	private String text[];

	public String getTitle() {
		return title;
	}

	public SimpleTextView setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getType() {
		return type;
	}

	public SimpleTextView setType(String type) {
		this.type = type;
		return this;
	}

	public String[] getText() {
		return text;
	}

	public void setText(String[] text) {
		this.text = text;
	}

	public SimpleTextView(String type, String title) {
		super();
		this.type = type;
		this.title = title;
	}

	@Override
	public String toString() {
		return "SimpleTextView [title=" + title + ", type=" + type + ", text[]=" + text+"]";
	}

}
