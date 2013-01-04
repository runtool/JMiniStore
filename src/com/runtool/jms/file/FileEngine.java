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
 * 基于key-value的xml数据存储和读取
 * 只基于字符串的操作，其他操作需二次封装
 * 该类依赖其它操作XML的三方jar包，本实现基于DOM4J
 * @author runtool
 */
public class FileEngine {
	
	public final String TAG_ROOT="resources";				  //根标签名称
	public final String TAG_ITEM="item";				  //数据记录标签
	public final String TAG_KEY="name";					  //数据记录名称属性
	public final String TAG_VALUE="";				  //数据记录值属性，无用
	public final String TAG_CLASSNAME="type";	  //数据记录对应的class属性，如：string对应的是java.lang.String
	
	private File file=null;						//数据存放的文件
	private Document doc=null;		
	
	private boolean isCommit=true;				//默认是马上写入文件
	
	public FileEngine(File file){			//注意数据同步问题，枷锁
		this.file=file;
		SAXReader saxReader=new SAXReader();   
		try {
			doc=saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}  
		System.out.println("root-->>"+doc.getRootElement().getName());
	}


	public String getString(String key,String def){   	     //返回一个String对象,不存在，返回默认值def
		Element rootElt = doc.getRootElement();				 // 获取根节点
		@SuppressWarnings("unchecked")
		List<Element> nodes = rootElt.elements(TAG_ITEM); 
		String id="";
		for(Element node:nodes){
			id=node.attributeValue(TAG_KEY);				//获取键值
			if(key.endsWith(id))      return  node.getText();
//			System.out.println(id);
		}
		return def;
	}
	
	/**
	 * @param key 主键，即记录的id
	 * @return  记录
	 */
	public Record getRecord(String key){
		NormalRecord record=null;
		Element rootElt = doc.getRootElement();				 // 获取根节点
		@SuppressWarnings("unchecked")
		List<Element> nodes = rootElt.elements(TAG_ITEM); 
		String id="";
		for(Element node:nodes){
			id=node.attributeValue(TAG_KEY);				//获取键值
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
	 * 增加一条记录，增加失败或记录存在都返回false
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
		Element root= doc.getRootElement();				 // 获取根节点
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
		Element root= doc.getRootElement();				 // 获取根节点
		Element e = DocumentHelper.createElement(TAG_ITEM);
		
		e.addAttribute(TAG_KEY, key);
		e.addText(value);
		e.addAttribute(TAG_CLASSNAME, className);
		root.add(e);
		doCommit();
		
		return true;
	}
	
	/**
	 * 设置一条记录,记录都是以对象的方式写入,记录存在更新，不存在插入
	 * @param key
	 * @param object
	 * @return
	 */
	public boolean setObject(String key,Object object){
		if(isExist(key)==true){		//修改
			
		}else{
			this.addObject(key, object);
		}
		doCommit();
		return  true;
	}
	
/***********************************多操作提交commit***************************************************/
	public boolean commit(){						     //强制提交，实质是把dom对象重新写入文件中
		
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

	private void doCommit(){							//根据配置决定是否提交
		if(isCommit==true) commit();
	}

	public void setCommit(boolean isCommit){			//设置提交配置信息
		this.isCommit=isCommit;
	}
	
/***********************************************************************************************/

	/********************判断记录是否存在***************************/
/**
 * 判断key名称的记录是否存在,记录存在返回true
 * @param key  待查询记录主键
 * @return
 */
 	public boolean isExist(String key){
		Element rootElt = doc.getRootElement();				    // 获取根节点
		@SuppressWarnings("unchecked")
		List<Element> nodes = rootElt.elements(TAG_ITEM); 
		String id="";
		for(Element node:nodes){
			id=node.attributeValue(TAG_KEY);				   //获取键值
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
