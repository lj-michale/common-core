package common.core.common.doc.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import common.core.common.doc.excel.format.WanyuanFormat;
import common.core.common.doc.excel.format.WriteCellFormat;

public class ExcelApiTest {
	@Test
	public void read() throws IOException {
		@SuppressWarnings("resource")
		HSSFWorkbook wb = new HSSFWorkbook(ExcelApiTest.class.getResourceAsStream("/excel-file.xls"));
		HSSFSheet sheet = wb.getSheetAt(0);

		HSSFCell a1 = sheet.getRow(0).getCell(0);
		HSSFCell b2 = sheet.getRow(1).getCell(0);
		HSSFCell c2 = sheet.getRow(1).getCell(1);
		Assert.assertEquals("组1", a1.getStringCellValue());
		Assert.assertEquals("列1", b2.getStringCellValue());
		Assert.assertEquals("列2", c2.getStringCellValue());

		ExcelApiHelper.readHandle(ExcelApiTest.class.getResourceAsStream("/excel-file.xls"), new RowReadHandler() {

			@Override
			public void handle(int sheetIndex, int rowIndex, Object[] rowDatas) {
				if (sheetIndex == 0 && rowIndex == 0) {
					Assert.assertEquals("组1", rowDatas[0]);
				}
				if (sheetIndex == 0 && rowIndex == 1) {
					Assert.assertEquals("列1", rowDatas[0]);
					Assert.assertEquals("列2", rowDatas[1]);
				}
			}
		});
	}

	@Test
	public void writeWithDefaultRowWriteHandler() throws IOException {
		List<Object[]> datas = new ArrayList<>();
		datas.add(new Object[] { "123dd", 456 });
		datas.add(new Object[] { "abc", new Date() });
		File targetFile = new File(new File(System.getProperty("java.io.tmpdir")), "./m1-writeWithDefaultRowWriteHandler1.xls");
		DefaultRowWriteHandler defaultRowWriteHandler = new DefaultRowWriteHandler(datas);
		defaultRowWriteHandler.setRowIndex(5);
		defaultRowWriteHandler.setSheetIndex(1);
		ExcelApiHelper.writeHandle(new FileOutputStream(targetFile), ExcelApiTest.class.getResourceAsStream("/m1.xls"), defaultRowWriteHandler);
		System.out.println("out put file:" + targetFile.getAbsolutePath());
	}

	@Test
	public void writeWithDefaultRowWriteHandlerSum() throws IOException {
		DefaultRowWriteHandler defaultRowWriteHandler = buildRowWriteHandler();
		File targetFile = new File(new File(System.getProperty("java.io.tmpdir")), "./m1-writeWithDefaultRowWriteHandlerSum.xls");
		defaultRowWriteHandler.setRowIndex(5);
		defaultRowWriteHandler.setSheetIndex(2);
		defaultRowWriteHandler.setSumRowData(new Object[] { null, 1234567890 });
		defaultRowWriteHandler.setWriteCellFormats(new WriteCellFormat[] { null, new WanyuanFormat() });
		ExcelApiHelper.writeHandle(new FileOutputStream(targetFile), ExcelApiTest.class.getResourceAsStream("/m1.xls"), defaultRowWriteHandler);
		System.out.println("out put file:" + targetFile.getAbsolutePath());
	}

	private DefaultRowWriteHandler buildRowWriteHandler() {
		List<Object[]> datas = new ArrayList<>();
		datas.add(new Object[] { "123dd", 456 });
		datas.add(new Object[] { "abc", 123456789 });
		DefaultRowWriteHandler defaultRowWriteHandler = new DefaultRowWriteHandler(datas);
		return defaultRowWriteHandler;
	}

	@Test
	public void write() throws IOException {
		File targetFile = new File(new File(System.getProperty("java.io.tmpdir")), "./m1-1.xls");
		ExcelApiHelper.writeHandle(new FileOutputStream(targetFile), ExcelApiTest.class.getResourceAsStream("/m1.xls"), buildTotalData(1, 4));
		System.out.println("out put file:" + targetFile.getAbsolutePath());
		targetFile = new File(new File(System.getProperty("java.io.tmpdir")), "./m1-2.xls");
		ExcelApiHelper.writeHandle(new FileOutputStream(targetFile), ExcelApiTest.class.getResourceAsStream("/m1.xls"), buildTotalData(1, 4), buildTotalData(2, 2));
		System.out.println("out put file:" + targetFile.getAbsolutePath());
		targetFile = new File(new File(System.getProperty("java.io.tmpdir")), "./buildMultRowData-1.xls");
		ExcelApiHelper.writeHandle(new FileOutputStream(targetFile), ExcelApiTest.class.getResourceAsStream("/m1.xls"), buildMultRowData(1, 4));
		System.out.println("out put file:" + targetFile.getAbsolutePath());
	}

	@Test
	public void writeWithoutTemplate() throws IOException {
		File targetFile = new File(new File(System.getProperty("java.io.tmpdir")), "./m4-1.xls");
		ExcelApiHelper.writeHandle(new FileOutputStream(targetFile), buildTotalData(1, 4));
		System.out.println("out put file:" + targetFile.getAbsolutePath());
		targetFile = new File(new File(System.getProperty("java.io.tmpdir")), "./m4-2.xls");
		ExcelApiHelper.writeHandle(new FileOutputStream(targetFile), buildTotalData(1, 4), buildTotalData(2, 2));
		System.out.println("out put file:" + targetFile.getAbsolutePath());
		DefaultRowWriteHandler defaultRowWriteHandler = buildRowWriteHandler();
		defaultRowWriteHandler.setHeaderCols(new String[] { "测试列1", "测试列2" });
		defaultRowWriteHandler.setSheetIndex(4);
		targetFile = new File(new File(System.getProperty("java.io.tmpdir")), "./buildMultRowData-4.xls");
		ExcelApiHelper.writeHandle(new FileOutputStream(targetFile), buildMultRowData(1, 4), defaultRowWriteHandler);
		System.out.println("out put file:" + targetFile.getAbsolutePath());
	}

	private RowWriteHandler buildMultRowData(int sheetIndex, int rowIndex) {
		return new RowWriteHandler() {

			Object[] data = new Object[] { new Object[] { "广东", "深圳平台", 20 }, new Object[] { "广东", "广州平台", 22 } };
			int dataIndex = 0;

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
				return 0;
			}

			@Override
			public Object[] nextRowDatas() {
				if (null == data || data.length == dataIndex)
					return null;
				if (dataIndex < 1)
					return (Object[]) data[dataIndex++];
				else {
					return new Object[] { (Object[]) data[0], (Object[]) data[dataIndex++] };
				}
			}

			@Override
			public List<WriteCell> getWriteCells() {
				return Arrays.asList(new WriteCell(0, 0, "标题" + sheetIndex));
			}

			@Override
			public int[] getTotalCols() {
				return null;
			}

			@Override
			public int getColumnNumber() {
				return 3;
			}

			@Override
			public String[] getHeaderCols() {
				return null;
			}

		};
	}

	private RowWriteHandler buildTotalData(int sheetIndex, int rowIndex) {
		return new RowWriteHandler() {

			Object[] data = new Object[] { new Object[] { "广东", "深圳平台", 20 }, new Object[] { "广东", "广州平台", 22 } };
			int dataIndex = 0;

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
				return 0;
			}

			@Override
			public Object[] nextRowDatas() {
				if (null == data || data.length == dataIndex)
					return null;
				return (Object[]) data[dataIndex++];
			}

			@Override
			public List<WriteCell> getWriteCells() {
				return Arrays.asList(new WriteCell(0, 0, "标题" + sheetIndex));
			}

			@Override
			public int[] getTotalCols() {
				return new int[] { 2 };
			}

			@Override
			public int getColumnNumber() {
				return 3;
			}

			@Override
			public String[] getHeaderCols() {
				return null;
			}

		};
	}

}
