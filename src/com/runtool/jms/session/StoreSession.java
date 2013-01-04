package com.runtool.jms.session;

import java.io.File;

import com.runtool.jms.file.FileEngine;


/**
 * @author runtool
 * 该程序包的主要类，打开一个数据文件对应唯一一个session，管理整个存储文件的生命周期
 */
public class StoreSession {
	private File data;					//data是数据实际存放的文件，注意线程下文件的同步问题
	private FileEngine dbengine;			//数据文件操作引擎
	
	final static String suffix=".xml";				   //文件后缀名，使用中只用文件名

	/**
	 * 文件打开三种权限
	 */
	final static String OPERATE_NOCREATE="";			//不存在，不新建
	final static String OPERATE_CREATE="";				//不存在，则新建
	final static String OPERATE_COVER="";				//存在，覆盖
	
	
	public StoreSession(){
	}

	public StoreSession(File root,String file,String operate){	     //构造并完成打开任务
		file+=suffix;
		data=new File(root,file);       //之一文件不存在的情况
		dbengine=new FileEngine(data);
	}
	
	/**
	 * 打开文件，涉及N多种权限和打开形式
	 * @param root			目录
	 * @param file			文件
	 * @param operate		打开方式
	 * @return
	 */
	public boolean open(File root,String file,String operate){	     //打开文件并加锁
		file+=suffix;
		data=new File(root,file);
		if(StoreSession.OPERATE_NOCREATE.endsWith(operate)){
			if(data.exists()==false){
				dbengine=new FileEngine(data);
			}
		}else if(StoreSession.OPERATE_CREATE.endsWith(operate)){
			
		}else if(StoreSession.OPERATE_COVER.endsWith(operate)){
			
		}else{
			try {
				throw new Exception("the operate "+operate+"undefined!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		
		return true;
	}

	public boolean close(String file){      //关闭文件并释放锁
		data=null;
		return true;
	}
	
//	public File getDataFile(){				//返回数据文件
//		return data;
//	}
	
	public static void main(String[] args){

	}
	
	/**************************获取数据操作*****************************/
	public String getString(String key,String def){
		return this.dbengine.getString(key, def);
	}

	public boolean addObject(String key,Object object){
		return this.dbengine.addObject(key, object);
	}
	/**************************获取数据操作over*****************************/
}
