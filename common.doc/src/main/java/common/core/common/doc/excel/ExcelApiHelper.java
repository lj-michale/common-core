package common.core.common.doc.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;

public class ExcelApiHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelApiHelper.class);

	public static void readHandle(InputStream is, RowReadHandler readHandler) {
		LOGGER.debug("ExcelApiHelper readHandle start with {}", readHandler);
		try {
			@SuppressWarnings("resource")
			HSSFWorkbook wb = new HSSFWorkbook(is);
			int sheets = wb.getNumberOfSheets();
			for (int sheetIndex = 0; sheetIndex < sheets; sheetIndex++) {
				HSSFSheet sheet = wb.getSheetAt(sheetIndex);
				if (readHandler instanceof RowReadWithSheetCheckHandler && !((RowReadWithSheetCheckHandler) readHandler).check(sheetIndex, sheet.getSheetName())) {
					continue;
				}
				int rows = sheet.getLastRowNum() + 1;
				for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
					HSSFRow rowCells = sheet.getRow(rowIndex);
					if (null == rowCells)
						continue;
					Object[] rowDatas = new Object[rowCells.getLastCellNum() + 1];
					for (int cellIndex = 0; cellIndex < rowDatas.length; cellIndex++) {
						HSSFCell cell = rowCells.getCell(cellIndex);
						if (null != cell) {
							Object value = readCellValue(cell);
							rowDatas[cellIndex] = value;
						}
					}
					if (readHandler instanceof RowReadWithRowCheckHandler && !((RowReadWithRowCheckHandler) readHandler).check(sheetIndex, rowIndex, rowDatas)) {
						continue;
					}
					readHandler.handle(sheetIndex, rowIndex, rowDatas);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			LOGGER.debug("ExcelApiHelper readHandle end with {}", readHandler);
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static Object readCellValue(HSSFCell cell) {
		if (cell == null)
			return null;
		Object value = null;
		if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {

			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
				SimpleDateFormat sdf = null;
				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
					sdf = new SimpleDateFormat("HH:mm");
				} else {// 日期
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				Date date = cell.getDateCellValue();
				value = sdf.format(date);
			} else if (cell.getCellStyle().getDataFormat() == 58) {
				// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				double result = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(result);
				value = sdf.format(date);
			} else {
				value = cell.getNumericCellValue();
			}

		} else {

			value = cell.getStringCellValue();
		}
		return value;
	}

	public static void writeHandle(OutputStream outputStream, RowWriteHandler... rowWriteHandlers) {
		writeHandle(outputStream, null, rowWriteHandlers);
	}

	public static void writeHandle(OutputStream outputStream, InputStream templateInputStream, RowWriteHandler... rowWriteHandlers) {
		LOGGER.debug("ExcelApiHelper writeHandle start");
		try {

			// excel模板路径
			// 读取excel模板
			HSSFWorkbook wb = null;
			if (null != templateInputStream) {
				wb = new HSSFWorkbook(templateInputStream);
			} else {
				wb = new HSSFWorkbook();
			}
			// 读取了模板内所有sheet内容
			for (RowWriteHandler rowWriteHandler : rowWriteHandlers) {
				writeWithHandler(wb, rowWriteHandler);
			}

			// 修改模板内容导出新模板
			wb.write(outputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			LOGGER.debug("ExcelApiHelper writeHandle end");
			try {
				if (null != templateInputStream)
					templateInputStream.close();

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			try {
				outputStream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}

	private static void writeWithHandler(HSSFWorkbook wb, RowWriteHandler rowWriteHandler) {
		int sheetIndex = rowWriteHandler.getSheetIndex();
		int rowIndex = rowWriteHandler.getRowIndex();
		int columnIndex = rowWriteHandler.getColumnIndex();
		// init Sheet
		while (wb.getNumberOfSheets() <= sheetIndex) {
			LOGGER.debug("createSheet");
			wb.createSheet();
		}
		HSSFSheet writableSheet = wb.getSheetAt(sheetIndex);

		int[] totalCols = rowWriteHandler.getTotalCols();
		Object[] totalColRow = new Object[rowWriteHandler.getColumnNumber()];
		totalColRow[0] = "合计";
		Object[] rowDatas = null;

		// 标题
		if (null != rowWriteHandler.getHeaderCols() && rowWriteHandler.getHeaderCols().length > 0) {
			wirteRowData(writableSheet, rowWriteHandler.getHeaderCols(), rowIndex++, columnIndex, rowWriteHandler.getHeaderCols().length);
		}

		// 数据
		while (true) {
			rowDatas = rowWriteHandler.nextRowDatas();
			if (null == rowDatas)
				break;

			// Object[] in rowDatas
			if (null != rowDatas[0] && rowDatas[0] instanceof Object[]) {
				for (Object objects : rowDatas) {
					wirteRowData(writableSheet, (Object[]) objects, rowIndex++, columnIndex, rowWriteHandler.getColumnNumber());
				}
				continue;
			}

			wirteRowData(writableSheet, rowDatas, rowIndex++, columnIndex, rowWriteHandler.getColumnNumber());

			// total
			for (int i = 0; null != totalCols && i < totalCols.length; i++) {
				int totalIndex = totalCols[i];
				Object item = rowDatas[totalIndex];
				if (null == item)
					continue;
				if (item instanceof Integer) {
					totalColRow[totalIndex] = null == totalColRow[totalIndex] ? item : (Integer) totalColRow[totalIndex] + (Integer) item;
				} else if (item instanceof Double) {
					totalColRow[totalIndex] = null == totalColRow[totalIndex] ? item : (Double) totalColRow[totalIndex] + (Double) item;
				} else if (item instanceof Float) {
					totalColRow[totalIndex] = null == totalColRow[totalIndex] ? item : (Float) totalColRow[totalIndex] + (Float) item;
				} else if (item instanceof BigDecimal) {
					totalColRow[totalIndex] = null == totalColRow[totalIndex] ? item : ((BigDecimal) totalColRow[totalIndex]).add((BigDecimal) item);
				} else if (item instanceof Long) {
					totalColRow[totalIndex] = null == totalColRow[totalIndex] ? item : (Long) totalColRow[totalIndex] + (Long) item;
				}
			}
		}

		if (null != totalCols && totalCols.length > 0) {
			wirteRowData(writableSheet, totalColRow, rowIndex, columnIndex, rowWriteHandler.getColumnNumber());
		}

		if (null != rowWriteHandler.getWriteCells()) {
			for (WriteCell writeCell : rowWriteHandler.getWriteCells()) {
				HSSFRow writeRow = writableSheet.getRow(writeCell.getRowIndex());
				if (null == writeRow) {
					writeRow = writableSheet.createRow(writeCell.getRowIndex());
				}
				HSSFCell cell = writeRow.getCell(writeCell.getColIndex());
				if (null == cell) {
					cell = writeRow.createCell(writeCell.getColIndex());
				}
				ExcelApiHelper.setCellValue(cell, writeCell.getValue());
			}
		}
	}

	private static void wirteRowData(HSSFSheet writableSheet, Object[] rowDatas, int rowIndex, int col, int maxCol) {
		HSSFRow writeRow = writableSheet.getRow(rowIndex);
		if (null == writeRow) {
			writeRow = writableSheet.createRow(rowIndex);
		}
		for (Object data : rowDatas) {
			HSSFCell cell = writeRow.getCell(col);
			if (null == cell) {
				cell = writeRow.createCell(col);
			}
			if (maxCol == col) {
				break;
			}
			col++;
			if (null == data)
				continue;
			ExcelApiHelper.setCellValue(cell, data);
		}
	}

	private static void setCellValue(HSSFCell cell, Object data) {
		Class<? extends Object> dataType = data.getClass();
		if (String.class == dataType) {
			cell.setCellValue((String) data);
		} else if (short.class == dataType || Short.class == dataType) {
			cell.setCellValue((Short) data);
		} else if (int.class == dataType || Integer.class == dataType) {
			cell.setCellValue((Integer) data);
		} else if (long.class == dataType || Long.class == dataType) {
			cell.setCellValue((Long) data);
		} else if (float.class == dataType || Float.class == dataType) {
			cell.setCellValue((Float) data);
		} else if (double.class == dataType || Double.class == dataType || Number.class == dataType) {
			cell.setCellValue((Double) data);
		} else if (BigDecimal.class == dataType) {
			cell.setCellValue(((BigDecimal) data).doubleValue());
		} else if (java.sql.Time.class == dataType || java.sql.Date.class == dataType || java.sql.Timestamp.class == dataType || java.util.Date.class == dataType) {
			cell.setCellValue((java.util.Date) data);
		} else {
			cell.setCellValue(data.toString());
		}
	}

}
