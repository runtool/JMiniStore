package com.runtool.jms.record;

/**
 * @author runtool
 * 
 */
public class NormalRecord  implements Record{
	
	private String key;
	private String value;
	private String className;
	
	

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return className;
	}
	

}
