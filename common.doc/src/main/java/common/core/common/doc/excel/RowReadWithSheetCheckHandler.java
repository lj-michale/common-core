package common.core.common.doc.excel;

public interface RowReadWithSheetCheckHandler extends RowReadHandler {
	public boolean check(int sheetIndex, String sheetName);
}
