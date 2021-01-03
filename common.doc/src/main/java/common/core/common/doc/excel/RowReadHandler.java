package common.core.common.doc.excel;

public interface RowReadHandler {
	public void handle(int sheetIndex, int rowIndex, Object[] rowDatas);
}
