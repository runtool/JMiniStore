package com.runtool.jms.session;

import java.io.File;

import com.runtool.jms.file.FileEngine;


/**
 * @author runtool
 * �ó��������Ҫ�࣬��һ�������ļ���ӦΨһһ��session�����������洢�ļ�����������
 */
public class StoreSession {
	private File data;					//data������ʵ�ʴ�ŵ��ļ���ע���߳����ļ���ͬ������
	private FileEngine dbengine;			//�����ļ���������
	
	final static String suffix=".xml";				   //�ļ���׺����ʹ����ֻ���ļ���

	/**
	 * �ļ�������Ȩ��
	 */
	final static String OPERATE_NOCREATE="";			//�����ڣ����½�
	final static String OPERATE_CREATE="";				//�����ڣ����½�
	final static String OPERATE_COVER="";				//���ڣ�����
	
	
	public StoreSession(){
	}

	public StoreSession(File root,String file,String operate){	     //���첢��ɴ�����
		file+=suffix;
		data=new File(root,file);       //֮һ�ļ������ڵ����
		dbengine=new FileEngine(data);
	}
	
	/**
	 * ���ļ����漰N����Ȩ�޺ʹ���ʽ
	 * @param root			Ŀ¼
	 * @param file			�ļ�
	 * @param operate		�򿪷�ʽ
	 * @return
	 */
	public boolean open(File root,String file,String operate){	     //���ļ�������
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

	public boolean close(String file){      //�ر��ļ����ͷ���
		data=null;
		return true;
	}
	
//	public File getDataFile(){				//���������ļ�
//		return data;
//	}
	
	public static void main(String[] args){

	}
	
	/**************************��ȡ���ݲ���*****************************/
	public String getString(String key,String def){
		return this.dbengine.getString(key, def);
	}

	public boolean addObject(String key,Object object){
		return this.dbengine.addObject(key, object);
	}
	/**************************��ȡ���ݲ���over*****************************/
}
