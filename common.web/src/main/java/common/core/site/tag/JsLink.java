package common.core.site.tag;

import java.io.IOException;
import java.io.Writer;

import org.springframework.util.StringUtils;

public class JsLink implements JsWriter {

	private String link;

	private String attributes;

	public JsLink(String link, String attributes) {
		super();
		this.link = link;
		this.attributes = attributes;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	@Override
	public void write(Writer writer) throws IOException {
		writer.write("<script type=\"text/javascript\" src=\"");
		writer.write(link);
		writer.write("\"");
		if (StringUtils.hasText(attributes)) {
			writer.append(" ");
			writer.append(attributes);
		}
		writer.write("></script>");
	}

}
