package common.core.web.api.view.views;

import java.util.List;

@ApiResponsePlugin
public class SelectViews {
	
	private String type;

	private String title;
	
	private boolean choose; //是否能多选 true是  false 不能
	
	private List<Select> select;
	
	private List<ButtonView> buttonViews;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isChoose() {
		return choose;
	}

	public void setChoose(boolean choose) {
		this.choose = choose;
	}

	public List<Select> getSelect() {
		return select;
	}

	public void setSelect(List<Select> select) {
		this.select = select;
	}

	public List<ButtonView> getButtonViews() {
		return buttonViews;
	}

	public void setButtonViews(List<ButtonView> buttonViews) {
		this.buttonViews = buttonViews;
	}

	@Override
	public String toString() {
		return "SelectViews [type=" + type + ", title=" + title + ", choose=" + choose + ", select=" + select
				+ ", buttonViews=" + buttonViews + "]";
	}
	
}
