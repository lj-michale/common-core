package common.core.common.doc.excel;

import java.util.List;

/**
 * 写入excel处理类
 *
 */
public interface RowWriteHandler {

	/**
	 * @return Sheet的索引，从0开始
	 */
	public int getSheetIndex();

	/**
	 * @return 数据输出的行索引，从0开始
	 */
	public int getRowIndex();

	/**
	 * @return 数据输出的列索引，从0开始
	 */
	public int getColumnIndex();

	/**
	 * @return 数据输出总列数
	 */
	public int getColumnNumber();

	/**
	 * @return 下一行数据
	 */
	public Object[] nextRowDatas();

	/**
	 * @return 单独写入的单元格式数据，如标题等
	 */
	public List<WriteCell> getWriteCells();

	/**
	 * @return 需要做数据汇总的列索引号
	 */
	public int[] getTotalCols();

	/**
	 * @return 标题头
	 */
	public String[] getHeaderCols();
}
