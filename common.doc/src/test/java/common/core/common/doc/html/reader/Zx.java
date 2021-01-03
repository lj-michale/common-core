package common.core.common.doc.html.reader;

import java.util.Date;
import java.util.List;

import common.core.common.doc.html.annotation.HtmlReaderElement;
import common.core.common.doc.html.annotation.HtmlReaderType;

public class Zx {

	private String cname;
	private String sno;

	@HtmlReaderElement(path = { "#00300010001 table tr:eq(1) td:eq(2)" })
	private int intVal;// 信用汇总提示 其他贷款笔数

	@HtmlReaderElement(path = { "#00300030001 table tr:eq(1) td:eq(3)" })
	private double doubleVal;// 未结清贷款信息汇总 合同总额

	// @HtmlReaderElement(path = {})
	private Date dateVal;
	@HtmlReaderElement(path = { "#00700110001 table tr:gt(0)" }, type = HtmlReaderType.COUNT)
	private int tableTotal; // 信贷审批查询记录明细 查询次数
	@HtmlReaderElement(path = { "#0040004 table.single tr:eq(1) td:eq(5)" }, type = HtmlReaderType.SUM)
	private double sum;// 贷记卡 授信额度
	@HtmlReaderElement(path = { "#0040004 table.single tr:eq(1) td:eq(5)" }, type = HtmlReaderType.GET)
	private String ceilings;// 贷记卡 授信额度

	@HtmlReaderElement(path = { "#0040004 table.single" }, type = HtmlReaderType.WRAPER_LIST)
	private List<Cart> cartList;// 贷记卡

	@HtmlReaderElement(path = { "#0040004 table.single" }, type = HtmlReaderType.WRAPER_OBJECT)
	private Cart cartObject;// 贷记卡

	@HtmlReaderElement(path = { "#0040004 table.single" }, type = HtmlReaderType.WRAPER_OBJECT, checkPath = { "#0040004 table:eq(1)  tr:eq(0) th:eq(0)" }, checkValue = { "业务号" })
	private Cart cartCheckTrueObject;// 贷记卡

	@HtmlReaderElement(path = { "#0040004 table.single" }, type = HtmlReaderType.WRAPER_OBJECT, checkPath = { "#0040004 table:eq(1) tr:eq(0) th:eq(0)" }, checkValue = { "业务号1" })
	private Cart cartCheckFalseObject;// 贷记卡

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public int getTableTotal() {
		return tableTotal;
	}

	public void setTableTotal(int tableTotal) {
		this.tableTotal = tableTotal;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public int getIntVal() {
		return intVal;
	}

	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}

	public Date getDateVal() {
		return dateVal;
	}

	public void setDateVal(Date dateVal) {
		this.dateVal = dateVal;
	}

	public double getDoubleVal() {
		return doubleVal;
	}

	public void setDoubleVal(double doubleVal) {
		this.doubleVal = doubleVal;
	}

	public String getCeilings() {
		return ceilings;
	}

	public void setCeilings(String ceilings) {
		this.ceilings = ceilings;
	}

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

	public Cart getCartObject() {
		return cartObject;
	}

	public void setCartObject(Cart cartObject) {
		this.cartObject = cartObject;
	}

	@Override
	public String toString() {
		return "Zx [cname=" + cname + ", sno=" + sno + ", intVal=" + intVal + ", doubleVal=" + doubleVal + ", dateVal=" + dateVal + ", tableTotal=" + tableTotal + ", sum=" + sum + ", ceilings=" + ceilings + ", cartList=" + cartList + ", cartObject=" + cartObject + ", cartCheckTrueObject="
				+ cartCheckTrueObject + ", cartCheckFalseObject=" + cartCheckFalseObject + "]";
	}

}
