package common.core.web.exception;

/**
 * 成功与系统异常使用通用返回
 * 各系统内部具体请求失败错误代码按照约定自定义声明自己的错误码子类，多模块可以对应多个类
 * 格式示例：platform_000001，merchant_000001,consumer_000001
 * platform表示所属系统，00表示系统子模块最大支持99个，0001表示具体错误，最大支持9999个。
 * 
 */
public class BaseReturnInfo {

	public static final String SUCCESS_CODE = "success";
	public static final String SUCCESS_MSG = "请求成功";

	public static final String ERROR_CODE = "error";
	public static final String ERROR_MSG = "请求失败";
}
