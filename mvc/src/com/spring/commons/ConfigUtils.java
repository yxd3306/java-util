package com.spring.commons;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@SuppressWarnings("unchecked")
public class ConfigUtils {

	public static String getBasePackageName(String str) throws Exception {
		// 创建saxReader对象
		SAXReader reader = new SAXReader();
		// 通过read方法读取一个文件 转换成Document对象
		Document document = reader.read(new File("E:/zhuguang/mvc/config/springmvc.xml"));
		// 获取根节点元素对象
		Element node = document.getRootElement();

		return listNodes(node);
	}

	public static String listNodes(Element node) {
		// 获取当前节点的所有属性节点
		List<Attribute> list = node.attributes();
		// 遍历属性节点
		for (Attribute attr : list) {
			if ("component-scan".equals(node.getName())) {
				return attr.getValue().toString();
			}
		}
		// 当前节点下面子节点迭代器
		Iterator<Element> it = node.elementIterator();
		// 遍历
		while (it.hasNext()) {
			// 获取某个子节点对象
			Element e = it.next();
			// 对子节点进行遍历
			String PackgName = listNodes(e);
			return PackgName;
		}
		return "";

	}
}
