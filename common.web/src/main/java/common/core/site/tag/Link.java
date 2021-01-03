package common.core.site.tag;

public class Link {
	public static final String HREF_AJAX = "javascript:void(0);";
	public static final String CLAZZ_AJAX = "ajax-click";
	public static final String CLAZZ_SELECT = "page-link-selected";

	private String href;
	private String clazz;
	private String text;
	private String dataRender;
	private String dataUrl;

	private Link() {
		super();
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDataRender() {
		return dataRender;
	}

	public void setDataRender(String dataRender) {
		this.dataRender = dataRender;
	}

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public Link setSelectClass(boolean selected) {
		if (selected)
			this.clazz = this.clazz == null ? CLAZZ_SELECT : this.clazz + " " + CLAZZ_SELECT;
		return this;
	}

	public static Link buildLink(String href, String text) {
		Link link = new Link();
		link.href = href;
		link.text = text;
		return link;
	}

	public static Link buildAjaxLink(String href, String text) {
		Link link = new Link();
		link.href = HREF_AJAX;
		link.clazz = CLAZZ_AJAX;
		link.text = text;
		link.dataUrl = href;
		return link;
	}

	public static Link buildAjaxRenderLink(String href, String text, String render) {
		Link link = new Link();
		link.href = HREF_AJAX;
		link.clazz = CLAZZ_AJAX;
		link.text = text;
		link.dataUrl = href;
		link.dataRender = render;
		return link;
	}
}
