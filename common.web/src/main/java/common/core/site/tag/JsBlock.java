package common.core.site.tag;

import java.io.IOException;
import java.io.Writer;

public class JsBlock implements JsWriter {

	public String block;

	public JsBlock(String block) {
		super();
		this.block = block;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	@Override
	public void write(Writer writer) throws IOException {
		writer.write("<script type=\"text/javascript\">");
		writer.write(block);
		writer.write("</script>");
	}
}
