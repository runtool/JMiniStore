package com.runtool.jms.util;

import java.io.File;

/**
 * @author runtool
 * ����������ǰ���Ҳ���Ǳ����class��Ӧ���ļ��У���ֱ�Ӳ����ļ�
 */
public class ClassPackage {
	
	private String packageName;				//��������
	
	public ClassPackage(String packageName){
		if(packageName==null)   packageName="";                    //�ų�null�����
		
		//��������ʽ�ж�packageName�ĸ�ʽ������*.*.*�ĸ�ʽ
		this.packageName=packageName;
	}
	
	/**
	 * �������·�������·�������ж϶�Ӧ�ļ����Ƿ���ڣ���
	 * �����ַ�����ʽΪ/com/runtool
	 */
	public String getRrelativePath(){			
		String path="";
		String[] temp=packageName.split("\\.");
		for(int i=0;i<temp.length;i++){
				path=path+"/"+temp[i];
				//System.out.println("path--->>>"+path);absolute
		}
		return path;		
	}

	
	/**
	 * ���ؾ���·��������·����Ӧ���ļ����ļ��б������
	 * �����ַ�����ʽ�����ϵͳһ�£�windows��Ϊd:\com\runtool��linux��Ϊ/homt
	 * �ú������ܷ���null,����nullʱ��ʾû�еõ���Ч��Ŀ¼
	 */	
	public File getAbsolutePath(File root){		
		if(root==null||root.isDirectory()==false)  return null;
		String path="";
		String[] temp=packageName.split("\\.");
		for(int i=0;i<temp.length;i++){
				path=path+"/"+temp[i];
				if(new File(root,path).exists()==false||new File(root,path).isDirectory()==false)  return null;
				//�ж϶�Ӧ�㼶�ļ����Ƿ����
				//System.out.println("path--->>>"+path);absolute
		}
		File file=new File(root,path);
		return file;
	}

	/**
	 * �õ�class�ĸ��ľ���·��
	 * @return
	 */
	public File getClassRoot(){
		File root= new File(this.getClass().getResource("/").getPath());
		return root;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPackage cp=new ClassPackage("com");
//		System.out.println(cp.getRrelativePath());
//		System.out.println(cp.getAbsolutePath(new File("d:\\")));
		System.out.println(cp.getClassRoot());

	}

}
