package common.core.common.doc.excel;

import java.util.List;

import common.core.common.doc.excel.format.WriteCellFormat;

public class DefaultRowWriteHandler implements RowWriteHandler {

	private int sheetIndex = 0;
	private int rowIndex = 1;
	private int columnIndex = 0;
	private int columnNumber = 0;
	private List<Object[]> datas = null;
	private List<WriteCell> writeCells = null;
	private int[] totalCols = null;
	private int dataIndex = 0;
	private WriteCellFormat[] writeCellFormats;
	private Object[] sumRowData;
	private String[] headerCols;

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	@Override
	public int getSheetIndex() {
		return sheetIndex;
	}

	@Override
	public int getRowIndex() {
		return rowIndex;
	}

	@Override
	public int getColumnIndex() {
		return columnIndex;
	}

	@Override
	public int getColumnNumber() {
		return columnNumber;
	}

	public void setDatas(List<Object[]> datas) {
		this.datas = datas;
	}

	public void setWriteCells(List<WriteCell> writeCells) {
		this.writeCells = writeCells;
	}

	public void setTotalCols(int[] totalCols) {
		this.totalCols = totalCols;
	}

	private Object[] formatWriteCell(Object[] objs) {
		if (null == objs || null == writeCellFormats || writeCellFormats.length <= 1) {
			return objs;
		}
		Object[] formatObjs = new Object[objs.length];
		for (int i = 0; i < objs.length; i++) {
			formatObjs[i] = objs[i];
			if (null != formatObjs[i] && formatObjs[i].getClass().isArray()) {
				// 数组循环格式化
				formatObjs[i] = formatWriteCell((Object[]) formatObjs[i]);
				continue;
			}

			if (writeCellFormats.length > i && null != writeCellFormats[i]) {
				formatObjs[i] = writeCellFormats[i].format(formatObjs[i]);
			}
		}
		return formatObjs;
	}

	@Override
	public Object[] nextRowDatas() {
		if (null != datas && datas.size() > dataIndex) {
			Object[] objs = datas.get(dataIndex++);
			if (datas.size() == dataIndex && null != sumRowData && sumRowData.length > 1) {
				// 最后一行增加合计行
				sumRowData[0] = "合计：";
				return formatWriteCell(new Object[] { objs, sumRowData });
			}
			return formatWriteCell(objs);
		}
		return null;
	}

	@Override
	public List<WriteCell> getWriteCells() {
		return writeCells;
	}

	@Override
	public int[] getTotalCols() {
		return totalCols;
	}

	public DefaultRowWriteHandler(List<Object[]> datas) {
		super();
		this.datas = datas;
		if (null != datas && datas.size() > 0) {
			this.columnNumber = datas.get(0).length;
		}
	}

	public DefaultRowWriteHandler(List<Object[]> datas, int[] totalCols) {
		this(datas);
		this.totalCols = totalCols;
	}

	public DefaultRowWriteHandler(List<Object[]> datas, List<WriteCell> writeCells, int[] totalCols) {
		this(datas);
		this.writeCells = writeCells;
		this.totalCols = totalCols;
	}

	public WriteCellFormat[] getWriteCellFormats() {
		return writeCellFormats;
	}

	public void setWriteCellFormats(WriteCellFormat[] writeCellFormats) {
		this.writeCellFormats = writeCellFormats;
	}

	public Object[] getSumRowData() {
		return sumRowData;
	}

	public void setSumRowData(Object[] sumRowData) {
		this.sumRowData = sumRowData;
	}

	@Override
	public String[] getHeaderCols() {
		return this.headerCols;
	}

	public void setHeaderCols(String[] headerCols) {
		this.headerCols = headerCols;
	}

}
