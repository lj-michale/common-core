package common.core.web.api.view.form;

public class FormFieldOperationTypes {

	public static final String INPUT = "input";
	public static final String DATE = "date";
	public static final String DATETIME = "datetime";
	public static final String TIME = "time";
	public static final String HIDDEN = "hidden";
	public static final String RADIO = "radio";
	public static final String CHECKBOX = "checkbox"; //复选框
	public static final String IMG = "img";
	public static final String VIDEO = "video";
	public static final String REC = "rec";
	public static final String GPS = "gps";
	public static final String SELECT = "select";
	public final static String TEXTAREA = "Textarea";
	public final static String HTMLFRAGMENT = "HtmlFragment";
	public final static String AUTOCOMPLETE = "Autocomplete";
	public final static String REQUEST = "Request"; //请求
	public final static String FIELD = "Field"; //表单（跳转下一个界面编辑）
	public final static String DOUBLEDATE = "doubleDate"; //双时间选择
	public final static String ADDRESS = "address"; //地址选择（省市区，可编辑详细地址）
	public final static String SECTIONTITLE = "sectionTitle"; //分区头（不可编辑） 
	public final static String NOTICETITLE = "noticeTitle"; //分区头提示信息 
	public final static String POPUPINPUT = "PopUpInput"; //弹出输入框
	public static final String MULTISELECT = "multiSelect"; //跳转请求复选框
	public static final String Button = "button";
	public static final String UPLOAD = "upload";  //上传  带有云文档
	public static final String UPLOAD_NO_Clound = "uploadNoCloud";  //上传  不带带有云文档
	public static final String YearDate = "yearDate"; //时间选择器年 suffix
	public static final Boolean IS_SUFFIX =true; //链接去掉后缀
	public static final String  SECRET_INPUT = "secretInput";
	public static final String  SECRET_BANK_INPUT = "bankSelect";
	public static final String  ONE_SELECT = "oneSelect";
	public static final String  LINKAGE_EDIT = "linkageEdit"; // 推荐人/经办人二级联动
	public static final String  LINKAGE_HIDDEN = "linkageHidden"; // 中行联动隐藏字段
	public static final String YEAR_DATE = "yearDate";
	public static final String CARD_RECOCGNIZE = "cardRecocgnize";
	
	public static final String PHOTO_RECOGNITION = "photoRecognition";//拍照识别
	public static final String FORM_DELETE = "formDelete";
}
