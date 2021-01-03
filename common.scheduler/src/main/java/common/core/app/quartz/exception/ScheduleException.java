package common.core.app.quartz.exception;


/**
 * 自定义异常
 * 
 * Created by liyd on 12/19/14.
 */
public class ScheduleException extends RuntimeException {
	
	private String code;
	private String message;
	
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6909300834787162939L;

	/**
     * Constructor
     */
    public ScheduleException() {
        super();
    }

    /**
     * Instantiates a new ScheduleException.
     *
     * @param e the e
     */
    public ScheduleException(Throwable e) {
        super(e);
    }

    /**
     * Constructor
     *
     * @param message the message
     */
    public ScheduleException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param code the code
     * @param message the message
     */
    public ScheduleException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
