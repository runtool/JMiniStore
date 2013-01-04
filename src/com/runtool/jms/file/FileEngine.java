package com.runtool.jms.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.runtool.jms.record.NormalRecord;
import com.runtool.jms.record.Record;


/**
 * ����key-value��xml���ݴ洢�Ͷ�ȡ
 * ֻ�����ַ����Ĳ�����������������η�װ
 * ����������������XML������jar������ʵ�ֻ���DOM4J
 * @author runtool
 */
public class FileEngine {
	
	public final String TAG_ROOT="resources";				  //����ǩ����
	public final String TAG_ITEM="item";				  //���ݼ�¼��ǩ
	public final String TAG_KEY="name";					  //���ݼ�¼��������
	public final String TAG_VALUE="";				  //���ݼ�¼ֵ���ԣ�����
	public final String TAG_CLASSNAME="type";	  //���ݼ�¼��Ӧ��class���ԣ��磺string��Ӧ����java.lang.String
	
	private File file=null;						//���ݴ�ŵ��ļ�
	private Document doc=null;		
	
	private boolean isCommit=true;				//Ĭ��������д���ļ�
	
	public FileEngine(File file){			//ע������ͬ�����⣬����
		this.file=file;
		SAXReader saxReader=new SAXReader();   
		try {
			doc=saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}  
		System.out.println("root-->>"+doc.getRootElement().getName());
	}


	public String getString(String key,String def){   	     //����һ��String����,�����ڣ�����Ĭ��ֵdef
		Element rootElt = doc.getRootElement();				 // ��ȡ���ڵ�
		@SuppressWarnings("unchecked")
		List<Element> nodes = rootElt.elements(TAG_ITEM); 
		String id="";
		for(Element node:nodes){
			id=node.attributeValue(TAG_KEY);				//��ȡ��ֵ
			if(key.endsWith(id))      return  node.getText();
//			System.out.println(id);
		}
		return def;
	}
	
	/**
	 * @param key ����������¼��id
	 * @return  ��¼
	 */
	public Record getRecord(String key){
		NormalRecord record=null;
		Element rootElt = doc.getRootElement();				 // ��ȡ���ڵ�
		@SuppressWarnings("unchecked")
		List<Element> nodes = rootElt.elements(TAG_ITEM); 
		String id="";
		for(Element node:nodes){
			id=node.attributeValue(TAG_KEY);				//��ȡ��ֵ
			if(key.endsWith(id)) {
				record=new NormalRecord();
				record.setKey(id);
				record.setClassName(node.attributeValue(TAG_CLASSNAME));
				record.setValue(node.getText());
			}
		}
		if(record==null)   System.out.println("the record which key is"+ key+" is not exist!!!");
		return record;
	}
	
	/**
	 * ����һ����¼������ʧ�ܻ��¼���ڶ�����false
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addObject(String key,Object object){
		if(isExist(key)==true){
			System.out.println("the record which key is "+key+" is aleady exist!!!");
			return false;   
		}
		String value=object.toString();
		String className=object.getClass().getName();
		Element root= doc.getRootElement();				 // ��ȡ���ڵ�
		Element e = DocumentHelper.createElement(TAG_ITEM);
		
		e.addAttribute(TAG_KEY, key);
		e.addText(value);
		e.addAttribute(TAG_CLASSNAME, className);
		root.add(e);
		doCommit();
		
		return true;
	}
	
	public boolean addString(String key,String value){
		if(isExist(key)==true){
			System.out.println("the record which key is "+key+" is aleady exist!!!");
			return false;   
		}
		String className="java.lang.String";
		Element root= doc.getRootElement();				 // ��ȡ���ڵ�
		Element e = DocumentHelper.createElement(TAG_ITEM);
		
		e.addAttribute(TAG_KEY, key);
		e.addText(value);
		e.addAttribute(TAG_CLASSNAME, className);
		root.add(e);
		doCommit();
		
		return true;
	}
	
	/**
	 * ����һ����¼,��¼�����Զ���ķ�ʽд��,��¼���ڸ��£������ڲ���
	 * @param key
	 * @param object
	 * @return
	 */
	public boolean setObject(String key,Object object){
		if(isExist(key)==true){		//�޸�
			
		}else{
			this.addObject(key, object);
		}
		doCommit();
		return  true;
	}
	
/***********************************������ύcommit***************************************************/
	public boolean commit(){						     //ǿ���ύ��ʵ���ǰ�dom��������д���ļ���
		
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileWriter(file));
			writer.write(doc); 
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		} 
		return true;
	}

	private void doCommit(){							//�������þ����Ƿ��ύ
		if(isCommit==true) commit();
	}

	public void setCommit(boolean isCommit){			//�����ύ������Ϣ
		this.isCommit=isCommit;
	}
	
/***********************************************************************************************/

	/********************�жϼ�¼�Ƿ����***************************/
/**
 * �ж�key���Ƶļ�¼�Ƿ����,��¼���ڷ���true
 * @param key  ����ѯ��¼����
 * @return
 */
 	public boolean isExist(String key){
		Element rootElt = doc.getRootElement();				    // ��ȡ���ڵ�
		@SuppressWarnings("unchecked")
		List<Element> nodes = rootElt.elements(TAG_ITEM); 
		String id="";
		for(Element node:nodes){
			id=node.attributeValue(TAG_KEY);				   //��ȡ��ֵ
			if(key.endsWith(id))      return  true;
		}
		return false;
	}

	/*************************************************************/
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File file=new File("d:\\data.xml");
		FileEngine parser=new FileEngine(file);
		parser.addObject("001", "001");
		parser.addObject("002", "002");
		parser.addObject("003", "003");
		
//		Record r=parser.getRecord("username");
//		System.out.println(r.getValue());
//		System.out.println(r.getClassName());
	}

}
