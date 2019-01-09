package com.funeral.wulinapi.Enum;

/**
 * 自定义状态码枚举类
 * Jackson_J
 */
public enum ErrorCodeEnum {
	UNKNOWN_EXCEPTION(801,"未知异常","warn"),
	SYSTEM_ERROR(802,"系统错误","error");

	private Integer errorCode;
	private String errorMsg;
	private String errorType;


	private  ErrorCodeEnum(Integer errorCode, String errorMsg, String errorType) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.errorType = errorType;
	}

	/**
	 * Getter method for property <tt>errorCode</tt>.
	 *
	 * @return property value of errorCode
	 */
	public Integer getErrorCode() {
		return errorCode;
	}

	/**
	 * Setter method for property <tt>errorCode</tt>.
	 *
	 * @param errorCode value to be assigned to property errorCode
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Getter method for property <tt>errorMsg</tt>.
	 *
	 * @return property value of errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * Setter method for property <tt>errorMsg</tt>.
	 *
	 * @param errorMsg value to be assigned to property errorMsg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * Getter method for property <tt>errorType</tt>.
	 *
	 * @return property value of errorType
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * Setter method for property <tt>errorType</tt>.
	 *
	 * @param errorType value to be assigned to property errorType
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
}
