package common.core.common.doc.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.exception.RuntimeIOException;
import common.core.common.util.IoUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PdfBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(PdfBuilder.class);

	public static void build(InputStream pdfTemplateInputSteam, Map<String, Object> data, OutputStream outputStream) {
		LOGGER.debug("PdfBuilder build start");
		try {
			PdfReader reader = new PdfReader(pdfTemplateInputSteam);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PdfStamper ps = new PdfStamper(reader, bos);
			AcroFields fields = ps.getAcroFields();
			fillData(fields, data);
			ps.setFormFlattening(true);
			ps.close();
			outputStream.write(bos.toByteArray());
		} catch (IOException | DocumentException e) {
			LOGGER.error(e.getMessage(), e);
			IoUtil.close(pdfTemplateInputSteam);
			IoUtil.close(outputStream);
		} finally {
			LOGGER.debug("PdfBuilder build end");
		}
	}

	public static void fillData(AcroFields fields, Map<String, Object> data) throws IOException, DocumentException {
		for (String key : fields.getFields().keySet()) {
			Object value = data.get(key);
			value = null != value ? value : data.get(buildSimpleKey(key));
			value = null != value ? value : "";
			fields.setField(key, value.toString(), true);
		}
	}

	private static String buildSimpleKey(String key) {
		key = key.replaceAll(".*\\.", "");
		key = key.replaceAll("\\[.*\\]", "");
		return key;
	}

	public static void mergePdfFilesAndBuild(String[] classPathFiles, Map<String, Object> data, OutputStream outputStream) throws FileNotFoundException {
		try {
			Document document = new Document(new PdfReader(PdfOpertator.class.getResourceAsStream(classPathFiles[0])).getPageSize(1));

			PdfCopy copy = new PdfCopy(document, outputStream);
			document.open();
			for (int i = 0; i < classPathFiles.length; i++) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				InputStream inputStream = PdfOpertator.class.getResourceAsStream(classPathFiles[i]);
				AssertErrorUtils.assertNotNull(inputStream, "not find classPathFiles:{}", classPathFiles[i]);
				PdfBuilder.build(inputStream, data, byteArrayOutputStream);

				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
				PdfReader reader = new PdfReader(byteArrayInputStream);
				int n = reader.getNumberOfPages();
				for (int j = 1; j <= n; j++) {
					document.newPage();
					PdfImportedPage page = copy.getImportedPage(reader, j);
					copy.addPage(page);
				}
			}
			document.close();
		} catch (IOException | DocumentException e) {
			throw new RuntimeIOException(e.getMessage(), e);
		} finally {
			IoUtil.close(outputStream);
		}
	}

}
