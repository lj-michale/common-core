package common.core.common.xml;

import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLStreamWriter2;

import com.ctc.wstx.stax.WstxOutputFactory;
import common.core.common.util.StringUtil;

public final class XMLBuilder {
	private static final String NEW_LINE = "\n";
	private static final String INDENT = "    ";

	public static XMLBuilder simpleBuilder() {
		return new XMLBuilder();
	}

	public static XMLBuilder indentedXMLBuilder() {
		XMLBuilder builder = new XMLBuilder();
		builder.indented = true;
		return builder;
	}

	static enum Node {
		START_ELEMENT, TEXT, END_ELEMENT
	}

	final StringWriter writer;
	XMLStreamWriter2 stream;
	Node lastNode;
	boolean indented = false;
	int level = 0;

	private XMLBuilder() {
		writer = new StringWriter();
		// explicitly to use woodstox
		XMLOutputFactory factory = new WstxOutputFactory();

		try {
			stream = (XMLStreamWriter2) factory.createXMLStreamWriter(writer);
		} catch (XMLStreamException e) {
			throw new XMLException(e);
		}
	}

	public String toXML() {
		try {
			stream.writeEndDocument();
		} catch (XMLStreamException e) {
			throw new XMLException(e);
		}
		return writer.toString();
	}

	public XMLBuilder xmlDeclaration(String encoding, String version) {
		try {
			stream.writeStartDocument(encoding, version);
			return this;
		} catch (XMLStreamException e) {
			throw new XMLException(e);
		}
	}

	public XMLBuilder startElement(String localName) {
		try {
			indentForStartElement();
			stream.writeStartElement(localName);
			if (indented) {
				lastNode = Node.START_ELEMENT;
				level++;
			}
			return this;
		} catch (XMLStreamException e) {
			throw new XMLException(e);
		}
	}

	public XMLBuilder emptyElement(String localName) {
		try {
			indentForStartElement();
			stream.writeEmptyElement(localName);
			finishIndentingForEndElement();
			return this;
		} catch (XMLStreamException e) {
			throw new XMLException(e);
		}
	}

	private void finishIndentingForEndElement() {
		if (indented) {
			lastNode = Node.END_ELEMENT;
		}
	}

	private void indentForStartElement() throws XMLStreamException {
		if (indented && (Node.START_ELEMENT.equals(lastNode) || Node.END_ELEMENT.equals(lastNode))) {
			indent();
		}
	}

	public XMLBuilder endElement() {
		try {
			if (indented) {
				level--;
				if (Node.END_ELEMENT.equals(lastNode))
					indent();
			}

			stream.writeEndElement();

			finishIndentingForEndElement();
			return this;
		} catch (XMLStreamException e) {
			throw new XMLException(e);
		}
	}

	private void indent() throws XMLStreamException {
		stream.writeCharacters(NEW_LINE);
		for (int i = 0; i < level; i++) {
			stream.writeCharacters(INDENT);
		}
	}

	public XMLBuilder attribute(String localName, String value) {
		try {
			stream.writeAttribute(localName, value == null ? "" : value);
			return this;
		} catch (XMLStreamException e) {
			throw new XMLException(e);
		}
	}

	public XMLBuilder cdata(String data) {
		if (!StringUtil.hasText(data))
			return this;

		try {
			stream.writeCData(data);
			if (indented)
				lastNode = Node.TEXT;
			return this;
		} catch (XMLStreamException e) {
			throw new XMLException(e);
		}
	}

	public XMLBuilder text(String text) {
		if (!StringUtil.hasText(text))
			return this;

		try {
			stream.writeCharacters(text);
			if (indented)
				lastNode = Node.TEXT;
			return this;
		} catch (XMLStreamException e) {
			throw new XMLException(e);
		}
	}

	public XMLBuilder textElement(String localName, String text) {
		return startElement(localName).text(text).endElement();
	}

	public XMLBuilder rawXML(String xml) {
		try {
			indentForStartElement();
			stream.writeRaw(xml);
			finishIndentingForEndElement();
		} catch (XMLStreamException e) {
			throw new XMLException(e);
		}
		return this;
	}
}
