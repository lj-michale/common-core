package common.core.common.doc.pdf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class Html2PdfUtils {

	/**
	 * html to pdf
	 * 
	 * @param pageContentList
	 *            pdf所有页的内容,Html元素,可以用freemaker生成
	 * @param cssStream
	 *            css样式流
	 * @param width
	 *            pdf 宽度
	 * @param height
	 *            pdf 高度
	 * @param outputStream
	 *            pdf 输出流
	 */
	public static void html2pdf(List<String> pageContentList, InputStream cssStream, int width, int height,
			OutputStream outputStream) {
		Document document = new Document(new Rectangle(width, height));
		try {
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();
			for (int i = 0; i < pageContentList.size(); i++) {
				document.newPage();
				InputStream htmlStream = new ByteArrayInputStream(pageContentList.get(i).getBytes());
				XMLWorkerHelper.getInstance().parseXHtml(writer, document, htmlStream, cssStream,
						Charset.forName("UTF-8"), new XMLWorkerFontProvider() {
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
	}
}
