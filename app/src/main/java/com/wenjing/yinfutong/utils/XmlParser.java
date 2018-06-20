package com.wenjing.yinfutong.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

public class XmlParser {
	private Element mRootElement;
		
	public XmlParser(InputStream ins){
		if(ins == null){
			throw new NullPointerException("The inputstream is null");
		}
		
		Document mDom = null;
		try {
			mDom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ins);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
		if(mDom != null){
			mRootElement = mDom.getDocumentElement();
		} else {
			throw new NullPointerException("Can not parse the xml");
		}
	}
	
	public Element getRootElement(){
		return mRootElement;
	}
	
	
}
