package common.core.web.api;

import com.alibaba.fastjson.JSON;

/**
 * @program: apis-merchant-parent
 * @description: 异常信息模型,包含class
 * @author: jia.chen
 * @create: 2018-12-15 14:42
 **/
public class ExceptionBaseApiResponse extends BaseApiResponse {
    @ApiOptionDoc(demoValue = "java.lang.Exception", description = "异常信息类")
    private String className;
    @ApiOptionDoc(demoValue = "{}", description = "异常信息序列化")
    private String exceptionStr;

    public ExceptionBaseApiResponse() {
        super();
    }

    public ExceptionBaseApiResponse(String code, Exception exception) {
        super(code, exception.getMessage());
        try {
            this.className = exception.getClass().getName();
            this.exceptionStr = JSON.toJSONString(exception);
        }catch (Exception e){
            this.className = e.getClass().getName();
            this.exceptionStr = JSON.toJSONString(e);
        }

    }

    public ExceptionBaseApiResponse(String code, String message, Exception exception) {
        super(code, message);
        try {
            this.className = exception.getClass().getName();
            this.exceptionStr = JSON.toJSONString(exception);
        } catch (Exception e) {
            this.className = e.getClass().getName();
            this.exceptionStr = JSON.toJSONString(e);
        }
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getExceptionStr() {
        return exceptionStr;
    }

    public void setExceptionStr(String exceptionStr) {
        this.exceptionStr = exceptionStr;
    }
}
