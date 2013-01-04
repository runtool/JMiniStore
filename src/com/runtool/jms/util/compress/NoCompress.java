package com.runtool.jms.util.compress;

/**
 * 该类实现压缩和解压接口，但实际不操作
 * @author runtool
 *
 */
public class NoCompress implements Compress{

	@Override
	public String compress(String source) {
		// TODO Auto-generated method stub
		return source;
	}

	@Override
	public String decompress(String source) {
		// TODO Auto-generated method stub
		return source;
	}

}
