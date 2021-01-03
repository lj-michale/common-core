package common.core.web.api.view.views;

import java.util.List;

@ApiResponsePlugin
public class FilterListView {
	
	private String title;

	private FormView formView;
    
	private ListView listView;
	
	private List<ButtonView> buttonViews;
	
	public String getTitle(){
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
    
	public FilterListView(FormView formView, ListView listView) {
		super();
		this.formView = formView;
		this.listView = listView;
	}
	
	public FilterListView(FormView formView, ListView listView, List<ButtonView> buttonViews) {
		super();
		this.formView = formView;
		this.listView = listView;
		this.buttonViews = buttonViews;
	}

	public List<ButtonView> getButtonViews() {
		return buttonViews;
	}

	public void setButtonViews(List<ButtonView> buttonViews) {
		this.buttonViews = buttonViews;
	}

	@Override
	public String toString() {
		return "FilterListView [title=" + title + ", formView=" + formView + ", listView=" + listView + ", buttonViews="
				+ buttonViews + "]";
	}

	
	
}
