package common.core.common.xml;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import common.core.common.util.IoUtil;

public class XMLParser {
	private boolean namespaceAware = false;
	private boolean ignoreEntity = true;

	public Document parse(File file) {
		InputStream inputStream = null;
		try {
			DocumentBuilder builder = createBuilder();
			inputStream = new BufferedInputStream(new FileInputStream(file));
			return builder.parse(inputStream);
		} catch (ParserConfigurationException | IOException | SAXException e) {
			throw new XMLException(e);
		} finally {
			IoUtil.close(inputStream);
		}
	}

	public Document parse(String xmlText) {
		return parse(xmlText.getBytes(Charset.defaultCharset()));
	}

	public Document parse(String xmlText, Charset charSet) {
		return parse(xmlText.getBytes(charSet));
	}

	public Document parse(byte[] bytes) {
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			DocumentBuilder builder = createBuilder();
			return builder.parse(inputStream);
		} catch (ParserConfigurationException | IOException | SAXException e) {
			throw new XMLException(e);
		} finally {
			IoUtil.close(inputStream);
		}
	}

	private DocumentBuilder createBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setValidating(false);
		builderFactory.setNamespaceAware(namespaceAware);
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		if (ignoreEntity)
			builder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
					return new InputSource(new StringReader(""));
				}
			});
		return builder;
	}

	public XMLParser setNamespaceAware(boolean namespaceAware) {
		this.namespaceAware = namespaceAware;
		return this;
	}

	public XMLParser setIgnoreEntity(boolean ignoreEntity) {
		this.ignoreEntity = ignoreEntity;
		return this;
	}
}
