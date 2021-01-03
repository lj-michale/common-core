package common.core.common.xml;

import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import org.w3c.dom.Document;

public final class XMLBinder<T> {
	public static <T> XMLBinder<T> binder(Class<T> beanClass) {
		return new XMLBinder<T>(beanClass);
	}

	private JAXBContext context;
	private Class<T> beanClass;
	private boolean hasXMLRootElementAnnotation;

	private XMLBinder(Class<T> beanClass) {
		try {
			this.beanClass = beanClass;
			hasXMLRootElementAnnotation = beanClass.isAnnotationPresent(XmlRootElement.class);
			context = JAXBContext.newInstance(beanClass);
		} catch (JAXBException e) {
			throw new XMLException(e);
		}
	}

	public String toXML(T bean) {
		try {
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			if (hasXMLRootElementAnnotation)
				marshaller.marshal(bean, writer);
			else {
				marshaller.marshal(new JAXBElement<T>(new QName("", beanClass.getSimpleName()), beanClass, bean), writer);
			}
			return writer.toString();
		} catch (JAXBException e) {
			throw new XMLException(e);
		}
	}

	public T fromXML(String xml) {
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Document document = new XMLParser().setNamespaceAware(true).parse(xml);
			JAXBElement<T> element = unmarshaller.unmarshal(document, beanClass);
			return element.getValue();
		} catch (JAXBException e) {
			throw new XMLException(e);
		}
	}

	public T fromXML(String xml, Charset charSet) {
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Document document = new XMLParser().setNamespaceAware(true).parse(xml, charSet);
			JAXBElement<T> element = unmarshaller.unmarshal(document, beanClass);
			return element.getValue();
		} catch (JAXBException e) {
			throw new XMLException(e);
		}
	}

}
