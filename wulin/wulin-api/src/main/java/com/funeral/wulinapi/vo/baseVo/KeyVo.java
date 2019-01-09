package com.funeral.wulinapi.vo.baseVo;

/**
 * Jaskson_J
 * 用来序列化枚举
 */
public class KeyVo {
	
	private Object value;
	
	private Object text;

	public Object getText() {
		return text;
	}

	public void setText(Object key) {
		this.text = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
