package com.runtool.jms.record;

/**
 * @author runtool
 * ��һ����¼��ʵ��ӿ�
 */
public interface Record{
	
	public String getKey();				//���ؼ�¼�ļ�
	public String getValue();			//���ؼ�¼��ֵ
	public String getClassName();		//���ؼ�¼��Ӧ��class���ƣ������������磺com.**.MyString
	
}
