package com.runtool.jms.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 该类返回一个存储文件的file引用，同时初始化其对应的文件锁
 * 文件的同步
 * @author runtool
 *
 */
public class FileLocker {
	
	private final static String suffix=".xml";				 //存储文件后缀名，使用中只用文件名
	private final static String lsuffix=".lock";			//文件锁的后缀名，使用中只用文件名
	
	public final static String PERMISSION_R="R";			//读权限
	public final static String PERMISSION_W="W";			//写权限,枷锁com.runtool.jms.util.FileLocker
	
	private FileLock lock=null;								//文件锁,成员变量，保证对象释放时对应的文件锁也会被释放
	
	public File getFile(File dir,String name,String permission){	//name只是文件名称，存储文件和文件锁名称相同，后缀名不同
		  File lockFile = new File(dir,name+lsuffix);       //文件锁文件，后缀名为.lock                  
		  try {                      
			  FileChannel channel = new FileOutputStream(lockFile).getChannel();
			  System.out.println("before file "+name+" lock");
			  lock= channel.lock();
			  System.out.println("after file "+name+" lock");
			  File file=new File(dir,name+suffix);			//数据存储文件
			  return file;
		  }catch (Exception e) {
			  return null;
		}
	}
	
	
	public void unlock(){				  //释放文件锁
			try {
				lock.release();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ;
			}
	}


	@Override
	protected void finalize(){
//		unlock();
		System.out.println("protected void finalize()");
	}

	public static void main(String[] args){
		FileLocker fl=new FileLocker();
		fl.getFile(new File("D:\\"), "data","");
		fl=null;
		System.gc();
		FileLocker flf=new FileLocker();
		flf.getFile(new File("D:\\"), "data","");
		
		
	}
	
}
