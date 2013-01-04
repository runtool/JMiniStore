package com.runtool.jms.record;

/**
 * @author runtool
 * 是一条记录的实体接口
 */
public interface Record{
	
	public String getKey();				//返回记录的键
	public String getValue();			//返回记录的值
	public String getClassName();		//返回记录对应的class名称，包括包名；如：com.**.MyString
	
}
