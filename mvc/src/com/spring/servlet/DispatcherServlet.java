package com.spring.servlet;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.annotion.Controller;
import com.spring.annotion.Qualifier;
import com.spring.annotion.RequestMapping;
import com.spring.annotion.Service;
import com.spring.commons.ConfigUtils;
import com.spring.controller.YxdController;
@SuppressWarnings({"rawtypes","unchecked"})
@WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 定义一个扫面的全权限定类名的集合com.spring.annotion.Controller.class
	private List<String> packeNames = new ArrayList<String>();

	// 定义一个装实例session IOC容器
	private Map<String, Object> instanceMaps = new HashMap<String, Object>();

	// 方法链的集合对象String:/yxdController/insert insert
	private Map<String, Method> handlerMaps = new HashMap<String, Method>();

	@Override
	public void init() throws ServletException {
		try {
			// 需要拿到基础包名称
			String basePackageName = ConfigUtils.getBasePackageName("E:/zhuguang/mvc/config/springmvc.xml");
			System.out.println("basePackageName:" + basePackageName);
			// 全自动扫描
			scanBase(basePackageName);
			// 找到每一个bean的实例
			foundBeansInstance();
			// 注入通过Qualifier.class对象注入实例
			springIoc();
			// 执行方法链去处理
			handlerMaps();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// web层实例对象
	private void handlerMaps() throws Exception {
		if(packeNames.size()==0) {
			System.err.println("初始化Bean组件异常，没有扫描到任何Bean组件!");
			// 直接返回
			return;
		}
		for(Map.Entry<String, Object> entry : instanceMaps.entrySet()) {
			// 拿到Controller层上面的classUri ==类注解上面的参数
			if(entry.getValue().getClass().isAnnotationPresent(Controller.class)) {
				// request:/yxdController/insert
				Controller controllerAnnotation = entry.getValue().getClass().getAnnotation(Controller.class);
				// classUri:yxdController
				String classUri = controllerAnnotation.value();
				Method[] methods = entry.getValue().getClass().getMethods();
				for(Method method : methods) {
					// 方法类型上面含有这个RequestMapping说明需要进行映射处理
					if(method.isAnnotationPresent(RequestMapping.class)) {
						// 拿到每个方法字节码上面的RequestMapping.class的注解对象（实例）
						RequestMapping requestMapping = (RequestMapping)method.getAnnotation(RequestMapping.class);
						String methodUri = requestMapping.value();
						// key:/yxdController/insert    value:method
						handlerMaps.put("/"+classUri+"/"+methodUri, method);
					}
				}
			}
		}
	}

	// IOC注入
	private void springIoc() throws Exception {
		if(packeNames.size()==0) {
			System.err.println("初始化Bean组件异常，没有扫描到任何Bean组件!");
			// 直接返回
			return;
		}
		for(Map.Entry<String, Object> entry : instanceMaps.entrySet()) {
			// 拿到每个实例当中所有的依赖注入的属性类型
			// entry.getValue() ===实例对象
			Field[] fields = entry.getValue().getClass().getDeclaredFields();
			for(Field field : fields) {
				// 凡是field上面含有Qualifier标识就说明需要依赖注入
				if(field.isAnnotationPresent(Qualifier.class)) {
					// 注入实例 例：yxdServiceImpl
					String qualifierParam = ((Qualifier)field.getAnnotation(Qualifier.class)).value();
					// 根据注入参数在instanceMaps缓存当中找到相对应的实例注入
					field.setAccessible(true);
					field.set(entry.getValue(), instanceMaps.get(qualifierParam));
				}
				else {
					continue;
				}
			}
		}
	}

	// 找到实例
	private void foundBeansInstance() throws Exception {
		if(packeNames.size()==0) {
			System.err.println("初始化Bean组件异常，没有扫描到任何Bean组件!");
			// 直接返回
			return;
		}
		// com.spring.annotion.Controller.class
		for(String className:packeNames) {
			// ccName：获取到该文件的字节码对象
			Class ccName = Class.forName(className);
			// 代表字节码ccName是控制层
			if(ccName.isAnnotationPresent(Controller.class)) {
				// 拿到控制层实例
				Object ControllerInstance = ccName.newInstance();
				Controller controllerAnnotationObject = (Controller) ccName.getAnnotation(Controller.class);
				String controllerAnnotationParam = controllerAnnotationObject.value();
				instanceMaps.put(controllerAnnotationParam, ControllerInstance);
			}
			// Service层Bean组件注册
			else if(ccName.isAnnotationPresent(Service.class)) {
				// 拿到Service层实例
				Object serviceInstance = ccName.newInstance();
				Service serviceAnnotationObject = (Service) ccName.getAnnotation(Service.class);
				String serviceAnnotationParam = serviceAnnotationObject.value();
				instanceMaps.put(serviceAnnotationParam, serviceInstance);
			}
			else {
				continue;
			}
		}
	}

	// 全自动扫描实现
	private void scanBase(String basePackageName) {
		/*URL url = this.getClass().getClassLoader()
				.getResource(basePackageName.replaceAll("\\.", "/"));
		File dir = new File(url.getFile());
		for(File file:dir.listFiles()) {
			if(file.isDirectory()) {
				// 递归
				scanBase(basePackageName+"."+file.getName());
			}
			// 找到全包类名 com.spring.controller
			packeNames.add(basePackageName+"."
							+file.getName().replace(".class", "").trim());
			System.out.println("Spring容器扫描到的类有："+basePackageName+"."
								+file.getName().replace(".class", "").trim());
		}*/
		URL url = this.getClass().getClassLoader().getResource(basePackageName.replaceAll("\\.", "/"));
		File dir = new File(url.getFile());
		for(File file:dir.listFiles()) {
			if(file.isDirectory()) {
				scanBase(basePackageName+"."+file.getName());
			}
			else {
				packeNames.add(basePackageName+"."
						+file.getName().replace(".class", "").trim());
				System.out.println("Spring容器扫描到的类有："+basePackageName+"."
						+file.getName().replace(".class", "").trim());
			}
		}
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		String projectName = req.getContextPath();
		String path = uri.replace(projectName, "");
		Method method = handlerMaps.get(path);
		PrintWriter outPrintWriter = resp.getWriter();
		if(method==null) {
			outPrintWriter.write("您访问的这个资源找不到，请检查您的访问地址!");
			return;
		}
		String classname= uri.split("/")[2];
		YxdController yxd = (YxdController) instanceMaps.get(classname);
		try {
			method.invoke(yxd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
