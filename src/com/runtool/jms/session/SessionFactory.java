package com.runtool.jms.session;

import java.io.File;

import com.runtool.jms.util.ClassPackage;
/**
 * @author runtool
 * �������ڹ���StoreSession
 */
public class SessionFactory {
	
	File root;            //session��Ӧ�ĸ�Ŀ¼
	
	public SessionFactory(File root){			//����ӿ����ڰѴ洢�ļ��ŵ�class·��֮��Ŀռ�
		this.root=root;
	}

	public SessionFactory(String rootStr){
		ClassPackage cp=new ClassPackage(rootStr);
		this.root=cp.getAbsolutePath(cp.getClassRoot());
	}
	
	public StoreSession getSession(String file){
		return new StoreSession(root,file,null);
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		SessionFactory sf=new SessionFactory(new File("D:\\"));
//		StoreSession ss=sf.getSession("data");
//		System.out.println(ss.getString("username", "123"));
//		System.out.println(ss.addObject("key1", "fefe"));
		
		SessionFactory sf=new SessionFactory("");
		StoreSession ss=sf.getSession("config");
		StoreSession ss1=sf.getSession("config");
		System.out.println(ss.getString("ip", "www.swu.edu.cn"));

	}

}
