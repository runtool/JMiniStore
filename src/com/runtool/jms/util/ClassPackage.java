package com.runtool.jms.util;

import java.io.File;

/**
 * @author runtool
 * 本类操作的是包，也就是编译后class对应的文件夹，不直接操作文件
 */
public class ClassPackage {
	
	private String packageName;				//包的名称
	
	public ClassPackage(String packageName){
		if(packageName==null)   packageName="";                    //排除null的情况
		
		//先正则表达式判断packageName的格式必须是*.*.*的格式
		this.packageName=packageName;
	}
	
	/**
	 * 返回相对路径，相对路径不能判断对应文件夹是否存在！！
	 * 返回字符串格式为/com/runtool
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
	 * 返回绝对路径，绝对路径对应的文件和文件夹必须存在
	 * 返回字符串格式与操作系统一致；windows下为d:\com\runtool，linux下为/homt
	 * 该函数可能返回null,返回null时表示没有得到有效的目录
	 */	
	public File getAbsolutePath(File root){		
		if(root==null||root.isDirectory()==false)  return null;
		String path="";
		String[] temp=packageName.split("\\.");
		for(int i=0;i<temp.length;i++){
				path=path+"/"+temp[i];
				if(new File(root,path).exists()==false||new File(root,path).isDirectory()==false)  return null;
				//判断对应层级文件夹是否存在
				//System.out.println("path--->>>"+path);absolute
		}
		File file=new File(root,path);
		return file;
	}

	/**
	 * 得到class的根的绝对路径
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
