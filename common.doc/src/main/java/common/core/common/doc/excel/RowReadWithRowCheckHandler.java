package common.core.common.doc.excel;

public interface RowReadWithRowCheckHandler extends RowReadHandler {
	public boolean check(int sheetIndex, int rowIndex, Object[] rowDatas);
}
