package common.core.web.api.view.views;

public class ViewLoadTypes {
    /*
     * /groupview
     */
	public static final String INSTANT = "instant";
	public static final String LAZY = "lazy";
	public static final String MANUAL = "manual";
	
	/*
	 * /listview
	 */
	public static final String OPEN = "open";
	public static final String SELECT_TYPE="select";
	public static final String READONLY_TYPE="default";
	public static final String REDIRECT_TYPE="redirect";
	public static final String MESSAGE_TYPE="messageType"; // 公告消息
	public static final String PROCESS="process"; // 流程审批	
	public static final String NOTICE_LIST="noticeList"; // 公告	
	public static final String SYSTEM_MSG="systemMsg"; // 系统消息
	public static final String SELECTPERSON="selectPerson"; // 催办 选人
}
