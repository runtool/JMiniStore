package com.runtool.jms.util.code;

/**
 * 提供一对编码和解码的接口
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
