package common.core.common.doc.html.reader;

import java.util.Date;

import common.core.common.doc.html.annotation.HtmlReaderElement;
import common.core.common.doc.html.annotation.HtmlReaderType;

public class Cart {
	@HtmlReaderElement(path = { "tr:eq(1) td:eq(2)" }, type = HtmlReaderType.GET)
	private String type;
	@HtmlReaderElement(path = { "tr:eq(1) td:eq(4)" }, type = HtmlReaderType.GET)
	private Date pubDate;
	@HtmlReaderElement(path = { "tr:eq(1) td:eq(5)" }, type = HtmlReaderType.GET)
	private double ceiling;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public double getCeiling() {
		return ceiling;
	}

	public void setCeiling(double ceiling) {
		this.ceiling = ceiling;
	}

	@Override
	public String toString() {
		return "Cart [type=" + type + ", pubDate=" + pubDate + ", ceiling=" + ceiling + "]";
	}

}
