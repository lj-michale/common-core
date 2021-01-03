package commom.core.activemq.common;

public class Constants {
	 	public static final String MQ_NAME = "admin";
	    public static final String MQ_PASSWORD = "admin";
	    public static final String MQ_BROKETURL = "tcp://mq.dev.yitaichang.cn:61616";
	    
	    /**
	     * 消息确认机制类型 ACK_MODE
	     * 1：自动确认
	     * 2：客户端手动确认
	     * 3：自动批量确认
	     * 4：单条消息确认
	     * 0：事务提交并确认
	     */
	    public static final int AUTO_ACKNOWLEDGE = 1;

	    public static final int CLIENT_ACKNOWLEDGE = 2;

	    public static final int DUPS_OK_ACKNOWLEDGE = 3;
	    
	    public static final int INDIVIDUAL_ACKNOWLEDGE = 4;

	    public static final int SESSION_TRANSACTED = 0;
}
