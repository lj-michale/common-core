package common.core.common.doc.word;

import java.io.OutputStreamWriter;
import java.util.Map;

import common.core.common.doc.template.TemplateHelper;

public class WordBuilder {

	/**
	 * 根据word存成的xml模板利用freemarker生成word
	 * 
	 * @param data
	 * @param docOutputStream
	 * @param tempalteClassPath
	 */
	public static void buildDocFromXml(java.io.OutputStream docOutputStream, Map<String, Object> data, String tempalteClassPath) {
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(docOutputStream);
		TemplateHelper.processClassPathTempate(tempalteClassPath, data, outputStreamWriter);
	}
}
