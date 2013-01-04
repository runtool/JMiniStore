package com.runtool.jms.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * ���෵��һ���洢�ļ���file���ã�ͬʱ��ʼ�����Ӧ���ļ���
 * �ļ���ͬ��
 * @author runtool
 *
 */
public class FileLocker {
	
	private final static String suffix=".xml";				 //�洢�ļ���׺����ʹ����ֻ���ļ���
	private final static String lsuffix=".lock";			//�ļ����ĺ�׺����ʹ����ֻ���ļ���
	
	public final static String PERMISSION_R="R";			//��Ȩ��
	public final static String PERMISSION_W="W";			//дȨ��,����com.runtool.jms.util.FileLocker
	
	private FileLock lock=null;								//�ļ���,��Ա��������֤�����ͷ�ʱ��Ӧ���ļ���Ҳ�ᱻ�ͷ�
	
	public File getFile(File dir,String name,String permission){	//nameֻ���ļ����ƣ��洢�ļ����ļ���������ͬ����׺����ͬ
		  File lockFile = new File(dir,name+lsuffix);       //�ļ����ļ�����׺��Ϊ.lock                  
		  try {                      
			  FileChannel channel = new FileOutputStream(lockFile).getChannel();
			  System.out.println("before file "+name+" lock");
			  lock= channel.lock();
			  System.out.println("after file "+name+" lock");
			  File file=new File(dir,name+suffix);			//���ݴ洢�ļ�
			  return file;
		  }catch (Exception e) {
			  return null;
		}
	}
	
	
	public void unlock(){				  //�ͷ��ļ���
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
