package com.runtool.jms.util.compress;

/**
 * �ýӿ���Ҫ�ṩһ���ַ�����ѹ���ͽ�ѹ���ķ���
 * @author runtool
 *
 */
public interface Compress {	
	
	public String compress(String source);				//ѹ������Ҫ��д���ļ�ʱ���ٴ洢
	public String decompress(String source);			//��ѹ����ȡ�ļ�������

}
