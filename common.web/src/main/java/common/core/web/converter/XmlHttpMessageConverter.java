package common.core.web.converter;

import java.io.IOException;
import java.util.Arrays;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;

import common.core.app.lang.CharSet;
import common.core.common.util.ObjectUtil;

public class XmlHttpMessageConverter extends Jaxb2RootElementHttpMessageConverter {

	public XmlHttpMessageConverter() {
		super();
		this.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_XML, MediaType.TEXT_XML));
	}

	@Override
	public void setProcessExternalEntities(boolean processExternalEntities) {
		super.setProcessExternalEntities(processExternalEntities);
	}

	@Override
	public boolean isProcessExternalEntities() {
		return super.isProcessExternalEntities();
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return super.canRead(clazz, mediaType);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return (Object.class.isAssignableFrom(clazz) && canWrite(mediaType));
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return super.supports(clazz);
	}

	@Override
	protected Object readFromSource(Class<?> clazz, HttpHeaders headers, Source source) throws IOException {
		return super.readFromSource(clazz, headers, source);
	}

	@Override
	protected Source processSource(Source source) {
		return super.processSource(source);
	}

	@Override
	protected void writeToResult(Object o, HttpHeaders headers, Result result) throws IOException {
		if (result instanceof StreamResult && o != null) {
			StreamResult streamResult = (StreamResult) result;
			streamResult.getOutputStream().write(ObjectUtil.toXml(o).getBytes(CharSet.DEFAULT_CHAR_SET_CLASS));
			return;
		}
		super.writeToResult(o, headers, result);
	}

	@Override
	protected boolean canRead(MediaType mediaType) {
		return super.canRead(mediaType);
	}

	@Override
	protected boolean canWrite(MediaType mediaType) {
		return null != mediaType && super.canWrite(mediaType);
	}

}
