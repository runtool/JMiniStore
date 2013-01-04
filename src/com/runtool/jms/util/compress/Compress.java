package com.runtool.jms.util.compress;

/**
 * 该接口主要提供一对字符串的压缩和解压缩的方法
 * @author runtool
 *
 */
public interface Compress {	
	
	public String compress(String source);				//压缩，主要是写入文件时减少存储
	public String decompress(String source);			//解压，读取文件，解析

}
