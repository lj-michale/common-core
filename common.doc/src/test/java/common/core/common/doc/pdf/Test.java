package common.core.common.doc.pdf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class Test {

	public static void main(String[] args) throws Exception {

		// html2pdf(readFromFile(), "E:\\Test\\iText_test.pdf");

		File file = new File("E:\\Test\\iText_test.pdf");
		File parent = file.getParentFile();
		// 如果pdf保存路径不存在，则创建路径
		if (!parent.exists()) {
			parent.mkdirs();
		}

		html2pdf(readFromFile(), new FileOutputStream(file));

		// htmlCodeComeString("分页测试", "E:\\Test\\iText_test.pdf");
	}

	private static String readFromFile() throws FileNotFoundException, IOException {
		File file = new File("E:\\Test\\voucher.html");// 指定要读取的文件
		FileReader reader = new FileReader(file);// 获取该文件的输入流
		char[] bb = new char[1024];// 用来保存每次读取到的字符
		String str = "";// 用来将每次读取到的字符拼接，当然使用StringBuffer类更好
		int n;// 每次读取到的字符长度
		while ((n = reader.read(bb)) != -1) {
			str += new String(bb, 0, n);
		}
		reader.close();// 关闭输入流，释放连接
		return str;
	}

	public static void htmlCodeComeString(String htmlCode, String pdfPath) {
		Rectangle pageSize = new Rectangle(1100, 490);
		Document doc = new Document(pageSize);
		try {
			File file = new File(pdfPath);
			File parent = file.getParentFile();
			// 如果pdf保存路径不存在，则创建路径
			if (!parent.exists()) {
				parent.mkdirs();
			}
			PdfWriter.getInstance(doc, new FileOutputStream(file));
			doc.open();

			// 解决中文问题
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
			for (int i = 0; i < 3; i++) {
				doc.newPage();
				Paragraph t = new Paragraph(htmlCode + i, FontChinese);
				doc.add(t);
			}
			doc.close();
			System.out.println("文档创建成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void html2pdf(String html, OutputStream outputStream) {
		Document document = new Document(new Rectangle(900, 500));
		try {
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();
			InputStream htmlStream = new ByteArrayInputStream(html.getBytes());
			// InputStream cssStream =
			// request.getServletContext().getResourceAsStream("/resources/css/pdf.css");

			XMLWorkerHelper.getInstance().parseXHtml(writer, document, htmlStream, null, Charset.forName("UTF-8"),
					new XMLWorkerFontProvider() {
						@Override
						public Font getFont(final String fontname, final String encoding, final boolean embedded,
								final float size, final int style, final BaseColor color) {
							BaseFont bf = null;
							try {
								bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
							} catch (Exception e) {
								e.printStackTrace();
							}
							Font font = new Font(bf, size, style, color);
							font.setColor(color);
							return font;
						}
					});
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		document.close();
	}

}
