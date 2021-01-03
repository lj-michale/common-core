package common.core.notify.constant;

import java.io.Serializable;

/**
 * 接口结果返回
 * 
 * @author user
 *
 * @param <T>
 */
public class ModelResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 数据模型
	 */
	private T model;
	/**
	 * 是否成功
	 */
	private boolean success = false;
	/**
	 * 错误码
	 */
	private String errCode;
	/**
	 * 错误消息
	 */
	private String errMsg;
	/**
	 * 异常堆栈信息
	 */
	private String detailStack;

	public ModelResult() {

	}

	public ModelResult(String errCode, String errMsg) {
		this.success = false;
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public boolean isSuccess() {
		return success;
	}

	public ModelResult<T> withError(String errCode, String errMsg) {
		this.success = false;
		this.errCode = errCode;
		this.errMsg = errMsg;
		return this;
	}

	public ModelResult<T> withModel(T t) {
		this.success = true;
		this.model = t;
		return this;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
		this.success = true;
	}

	/*public ModelResult<T> withStackTrace(Throwable t) {
		ByteArrayOutputStream buf = null;
		try {
			buf = new ByteArrayOutputStream();
			t.printStackTrace(new PrintWriter(buf, true));
			this.detailStack = buf.toString();
			return this;
		} finally {
			if (buf != null) {
				try {
					buf.close();
				} catch (IOException e) {
				}
			}
		}
	}*/

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getDetailStack() {
		return detailStack;
	}
}
