package common.core.common.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public final class XPathUtils {
	public static Element selectElement(Node root, String xpath) {
		try {
			Node node = XPathAPI.selectSingleNode(root, xpath);
			if (node == null)
				return null;
			if (!(node instanceof Element))
				throw new XMLException("target node is not element, xpath=" + xpath + ", element=" + DOMUtils.text(root));
			return (Element) node;
		} catch (TransformerException e) {
			throw new XMLException(e);
		}
	}

	public static List<Element> selectElements(Node root, String xpath) {
		List<Element> elements = new ArrayList<>();
		try {
			NodeList nodes = XPathAPI.selectNodeList(root, xpath);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (!(node instanceof Element))
					throw new XMLException("target node is not element, xpath=" + xpath + ", element=" + DOMUtils.text(root));
				elements.add((Element) node);
			}
			return elements;
		} catch (TransformerException e) {
			throw new XMLException(e);
		}
	}

	public static String selectText(Node root, String xpath) {
		try {
			Node node = XPathAPI.selectSingleNode(root, xpath);
			if (node == null)
				return null;
			if (node instanceof Text) {
				return node.getTextContent();
			} else if (node instanceof Element) {
				return DOMUtils.getText((Element) node);
			} else if (node instanceof Attr) {
				return ((Attr) node).getValue();
			}
			throw new XMLException("unsupported type, xpath=" + xpath + ", element=" + DOMUtils.text(root));
		} catch (TransformerException e) {
			throw new XMLException(e);
		}
	}

	public static int selectInt(Node root, String xpath) {
		try {
			XObject result = XPathAPI.eval(root, xpath);
			return (int) result.num();
		} catch (TransformerException e) {
			throw new XMLException(e);
		}
	}

	private XPathUtils() {
	}
}