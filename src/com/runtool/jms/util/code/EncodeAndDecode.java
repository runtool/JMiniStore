package com.runtool.jms.util.code;

/**
 * �ṩһ�Ա���ͽ���Ľӿ�
 * @author runtool
 *
 */
public interface EncodeAndDecode{	
	/**
	 * @param bits
	 * @return
	 */
	public String Encode(byte[] bits);
	
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public byte[] Decode(String str);
}
