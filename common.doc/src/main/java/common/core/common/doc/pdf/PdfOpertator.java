package common.core.common.doc.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import common.core.common.exception.RuntimeIOException;
import common.core.common.util.IoUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class PdfOpertator {

	public static void mergePdfFiles(String[] files, OutputStream outputStream) {
		try {
			Document document = new Document(new PdfReader(PdfOpertator.class.getResourceAsStream(files[0])).getPageSize(1));

			PdfCopy copy = new PdfCopy(document, outputStream);
			document.open();
			for (int i = 0; i < files.length; i++) {
				PdfReader reader = new PdfReader(PdfOpertator.class.getResourceAsStream(files[i]));
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

	public static void partitionPdfFile(String filepath, int number) {
		Document document = null;
		PdfCopy copy = null;

		try {
			PdfReader reader = new PdfReader(filepath);

			int n = reader.getNumberOfPages();

			if (n < number) {
				System.out.println("The document does not have " + number + " pages to partition !");
				return;
			}

			int size = n / number;
			String staticpath = filepath.substring(0, filepath.lastIndexOf("\\") + 1);
			String savepath = null;
			ArrayList<String> savepaths = new ArrayList<String>();
			for (int i = 1; i <= number; i++) {
				if (i < 10) {
					savepath = filepath.substring(filepath.lastIndexOf("\\") + 1, filepath.length() - 4);
					savepath = staticpath + savepath + "0" + i + ".pdf";
					savepaths.add(savepath);
				} else {
					savepath = filepath.substring(filepath.lastIndexOf("\\") + 1, filepath.length() - 4);
					savepath = staticpath + savepath + i + ".pdf";
					savepaths.add(savepath);
				}
			}

			for (int i = 0; i < number - 1; i++) {
				document = new Document(reader.getPageSize(1));
				copy = new PdfCopy(document, new FileOutputStream(savepaths.get(i)));
				document.open();
				for (int j = size * i + 1; j <= size * (i + 1); j++) {
					document.newPage();
					PdfImportedPage page = copy.getImportedPage(reader, j);
					copy.addPage(page);
				}
				document.close();
			}

			document = new Document(reader.getPageSize(1));
			copy = new PdfCopy(document, new FileOutputStream(savepaths.get(number - 1)));
			document.open();
			for (int j = size * (number - 1) + 1; j <= n; j++) {
				document.newPage();
				PdfImportedPage page = copy.getImportedPage(reader, j);
				copy.addPage(page);
			}
			document.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
