package common.core.common.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xalan.processor.TransformerFactoryImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import common.core.app.lang.CharSet;
import common.core.common.util.StringUtil;

public final class DOMUtils {
	// force to use Xalan impl, to avoid use saxon which has bugs
	private static final TransformerFactory TRANSFORMER_FACTORY = new TransformerFactoryImpl();

	public static String text(Node node) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		print(node, bytes);
		return new String(bytes.toByteArray(), Charset.forName(CharSet.DEFAULT_CHAR_SET_STRING));
	}

	private static void print(Node node, OutputStream out) {
		try {
			Transformer transformer = TRANSFORMER_FACTORY.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.ENCODING, CharSet.DEFAULT_CHAR_SET_STRING);
			transformer.transform(new DOMSource(node), new StreamResult(out));
		} catch (TransformerException e) {
			throw new XMLException(e);
		}
	}

	public static List<Element> childElements(Node node) {
		List<Element> result = new ArrayList<Element>();
		NodeList childNodes = node.getChildNodes();
		int length = childNodes.getLength();
		for (int i = 0; i < length; i++) {
			Node child = childNodes.item(i);
			if (child instanceof Element) {
				result.add((Element) child);
			}
		}
		return result;
	}

	public static List<Node> children(Node node) {
		List<Node> result = new ArrayList<Node>();
		NodeList childNodes = node.getChildNodes();
		int length = childNodes.getLength();
		for (int i = 0; i < length; i++) {
			Node child = childNodes.item(i);
			if (child instanceof Text) {
				Text text = (Text) child;
				if (StringUtil.hasText(text.getWholeText()))
					result.add(text);
			} else {
				result.add(child);
			}
		}
		return result;
	}

	public static List<Attr> attributes(Node node) {
		List<Attr> result = new ArrayList<Attr>();
		NamedNodeMap attributes = node.getAttributes();
		int length = attributes.getLength();
		for (int i = 0; i < length; i++) {
			Attr attr = (Attr) attributes.item(i);
			result.add(attr);
		}
		return result;
	}

	public static void setText(Element element, String text) {
		NodeList childNodes = element.getChildNodes();
		if (childNodes.getLength() > 1)
			throw new XMLException("can not set text for element " + text(element));
		if (childNodes.getLength() == 0) {
			Text textNode = element.getOwnerDocument().createTextNode(text);
			element.appendChild(textNode);
		} else {
			Node textNode = element.getFirstChild();
			if (!(textNode instanceof Text))
				throw new XMLException("can not set text for element " + text(element));
			textNode.setTextContent(text);
		}
	}

	public static String getText(Element element) {
		NodeList children = element.getChildNodes();
		if (children.getLength() == 0) {
			return element.getTextContent();
		} else if (children.getLength() == 1) {
			Node firstChild = element.getFirstChild();
			if (firstChild instanceof Text)
				return firstChild.getTextContent();
		}
		throw new XMLException("target element is not a text element " + text(element));
	}

	/*
	 * The only working API is deprecated xerces serialize API. (the JAXP
	 * according to the Doc doesn't work well, that's why text() doesn't output
	 * pretty format, we can use it once it passes our test)
	 * http://xerces.apache.org/xerces2-j/faq-general.html#faq-6
	 */
	@SuppressWarnings("deprecation")
	public static String prettyFormat(Document document) {
		try {
			org.apache.xml.serialize.OutputFormat format = new org.apache.xml.serialize.OutputFormat(document);
			format.setIndenting(true);
			format.setOmitXMLDeclaration(true);
			format.setIndent(2);
			Writer out = new StringWriter();
			org.apache.xml.serialize.XMLSerializer serializer = new org.apache.xml.serialize.XMLSerializer(out, format);
			serializer.serialize(document);
			return out.toString();
		} catch (IOException e) {
			throw new XMLException(e);
		}
	}

	private DOMUtils() {
	}
}
