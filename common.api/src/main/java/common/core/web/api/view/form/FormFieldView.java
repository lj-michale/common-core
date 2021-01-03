package common.core.web.api.view.form;

public class FormFieldView {
	
    private String title;
    private String action;
    
    private String viewName; //子级模板插件名称
    
    
    
	public FormFieldView() {
		super();
	}
	
	public FormFieldView(String title, String action, String viewName) {
		super();
		this.title = title;
		this.action = action;
		this.viewName = viewName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public String toString() {
		return "FormFieldView [title=" + title + ", action=" + action + ", viewName=" + viewName + "]";
	}

   

   
}
