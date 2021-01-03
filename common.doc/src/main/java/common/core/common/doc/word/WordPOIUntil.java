package common.core.common.doc.word;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class WordPOIUntil {

	public static ByteArrayOutputStream replaceDocx(String srcPath, Map<String, String> map) {
		String[] sp = srcPath.split("\\.");
		if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
			try {
				XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
				// 替换段落中的指定文字
				Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
				while (itPara.hasNext()) {
					XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
					List<XWPFRun> runs = paragraph.getRuns();
					for (int i = 0; i < runs.size(); i++) {
						String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
						for (Map.Entry<String, String> entry : map.entrySet()) {
							oneparaString = oneparaString.replace(entry.getKey(), entry.getValue());
						}
						runs.get(i).setText(oneparaString, 0);
					}
				}
				// 替换表格中的指定文字
				Iterator<XWPFTable> itTable = document.getTablesIterator();
				while (itTable.hasNext()) {
					XWPFTable table = (XWPFTable) itTable.next();
					int rcount = table.getNumberOfRows();
					for (int i = 0; i < rcount; i++) {
						XWPFTableRow row = table.getRow(i);
						List<XWPFTableCell> cells = row.getTableCells();
						for (XWPFTableCell cell : cells) {
							String cellTextString = cell.getText();
							for (Entry<String, String> e : map.entrySet()) {
								if (cellTextString.contains(e.getKey()))
									cellTextString = cellTextString.replace(e.getKey(), e.getValue());
							}
							cell.removeParagraph(0);
							cell.setText(cellTextString);
						}
					}
				}
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				document.write(outStream);
				outStream.close();
				document.close();
				return outStream;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public static ByteArrayOutputStream replaceDoc(String srcPath, Map<String, String> map) {
		HWPFDocument document = null;
		String[] sp = srcPath.split("\\.");
		if (sp[sp.length - 1].equalsIgnoreCase("doc")) {
			try {
				document = new HWPFDocument(new FileInputStream(srcPath));
				Range range = document.getRange();
				for (Map.Entry<String, String> entry : map.entrySet()) {
					range.replaceText(entry.getKey(), entry.getValue());
				}
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				document.write(outStream);
				outStream.close();
				return outStream;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static ByteArrayOutputStream downLoadDoc(String srcPath, Map<String, String> map) {
		HWPFDocument document = null;
			try {
				document = new HWPFDocument(WordPOIUntil.class.getResourceAsStream(srcPath));
				Range range = document.getRange();
				for (Map.Entry<String, String> entry : map.entrySet()) {
					range.replaceText(entry.getKey(), entry.getValue());
				}
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				document.write(outStream);
				outStream.close();
				return outStream;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
}