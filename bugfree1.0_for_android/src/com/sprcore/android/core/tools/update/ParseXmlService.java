package com.sprcore.android.core.tools.update;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ParseXmlService
{
	public VersionModel parseXml(InputStream inStream) throws Exception
	{
		VersionModel model = new VersionModel();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		
		// ʵ��һ���ĵ��������
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// ͨ���ĵ����������ȡһ���ĵ�������
		DocumentBuilder builder = factory.newDocumentBuilder();
		// ͨ���ĵ�ͨ���ĵ��������һ���ĵ�ʵ��
		Document document = builder.parse(inStream);
		//��ȡXML�ļ���ڵ�
		Element root = document.getDocumentElement();
		//��������ӽڵ�
		NodeList childNodes = root.getChildNodes();
		for (int j = 0; j < childNodes.getLength(); j++)
		{
			//�����ӽڵ�
			Node childNode = (Node) childNodes.item(j);
			if (childNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element childElement = (Element) childNode;
				//�汾��
				if ("version".equals(childElement.getNodeName()))
				{
					String version = childElement.getFirstChild().getNodeValue();
					try {
						model.setVersion(Integer.parseInt(version));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//������
				else if (("name".equals(childElement.getNodeName())))
				{
					model.setName(childElement.getFirstChild().getNodeValue());
				}
				//���ص�ַ
				else if (("url".equals(childElement.getNodeName())))
				{
					model.setUrl(childElement.getFirstChild().getNodeValue());
				}
			}
		}
		//������ô�����modelȡΪnull
		if(model.getVersion()==0 || model.getName()==null || model.getUrl()==null){
			model = null;
		}	
		return model;
	}
}
