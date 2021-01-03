package common.core.web.exception;

/**
 * @author: tdz
 * @date: 2019/5/8 15:27
 * @description: TODO(这里用一句话描述这个类的作用)
 */
public class CustomErrorException extends RuntimeException {
    private String errorCode;
    private String errorMsg;


    public CustomErrorException(String errorCode, String errorMsg){
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
