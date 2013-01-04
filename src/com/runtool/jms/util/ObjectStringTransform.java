package com.runtool.jms.util;

import it.sauronsoftware.base64.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class ObjectStringTransform{

	public static String ObjectToString(Object object){    //将对象序列化成字符串
		   String res=null;
		   ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
		  
		   
		   OutputStreamWriter osw=new OutputStreamWriter(byteArrayOutputStream);
	       ObjectOutputStream objectOutputStream ;  
	        try {
//	        	 FileOutputStream fos=new FileOutputStream(new File("D:\\ss.txt"));
	        	 
	        	objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject(object);
				
				res = byteArrayOutputStream.toString("utf-8");
				res=java.net.URLEncoder.encode(res, "UTF-8");  
				System.out.println(res);
				res=java.net.URLDecoder.decode(res, "UTF-8");  
				System.out.println(res);
				
				 Base64.encode(byteArrayOutputStream.toByteArray());

//				res=byteArrayOutputStream.toString(10);
				
				objectOutputStream.close();  
		        byteArrayOutputStream.close();				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}      
	        return res;
	}
	
	
	public static Object StringToObject(String str){		//将字符串反序列化成对象
		Object object=null;
		ByteArrayInputStream bai = new ByteArrayInputStream(str.getBytes());
		try {
			ObjectInputStream ois=new ObjectInputStream(bai);
			object=ois.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	
	
	public static void main(String[] args){
		Object oo="handonghai";
//		System.out.println((String)ObjectStringTransform.StringToObject(ObjectStringTransform.ObjectToString(oo)));
	System.out.println(ObjectStringTransform.ObjectToString(oo));
	}
}
