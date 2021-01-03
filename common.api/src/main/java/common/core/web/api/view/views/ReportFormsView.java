package common.core.web.api.view.views;

import java.util.Arrays;
import java.util.List;

@ApiResponsePlugin
public class ReportFormsView  {
	
	private String title;

	private FormView formView;
    
	private ListView listView;
	
	private  boolean withOrderNo=false; //是否需要序号列
	
	private String orderNumber;  //固定列数
	
	private List<ButtonView> buttonViews;
    
	private String[] orderTitles;//可排序的列
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public FormView getFormView() {
		return formView;
	}

	public void setFormView(FormView formView) {
		this.formView = formView;
	}

	public ListView getListView() {
		return listView;
	}

	public void setListView(ListView listView) {
		this.listView = listView;
	}

	public boolean isWithOrderNo() {
		return withOrderNo;
	}

	public void setWithOrderNo(boolean withOrderNo) {
		this.withOrderNo = withOrderNo;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<ButtonView> getButtonViews() {
		return buttonViews;
	}

	public void setButtonViews(List<ButtonView> buttonViews) {
		this.buttonViews = buttonViews;
	}
    
   

	public String[] getOrderTitles() {
		return orderTitles;
	}

	public void setOrderTitles(String[] orderTitles) {
		this.orderTitles = orderTitles;
	}

	public ReportFormsView (FormView formView, ListView listView) {
		super();
		this.formView = formView;
		this.listView = listView;
	}
	
	public ReportFormsView (FormView formView, ListView listView, List<ButtonView> buttonViews) {
		super();
		this.formView = formView;
		this.listView = listView;
		this.buttonViews = buttonViews;
	}

	@Override
	public String toString() {
		return "ReportFormsView [title=" + title + ", formView=" + formView + ", listView=" + listView
				+ ", withOrderNo=" + withOrderNo + ", orderNumber=" + orderNumber + ", buttonViews=" + buttonViews
				+ ", orderTitles=" + Arrays.toString(orderTitles) + "]";
	}

	
    
	
	
	
	
}
