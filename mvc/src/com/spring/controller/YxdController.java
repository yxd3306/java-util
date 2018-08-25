package com.spring.controller;

import com.spring.annotion.Controller;
import com.spring.annotion.Qualifier;
import com.spring.annotion.RequestMapping;
import com.spring.service.YxdService;

@Controller("yxdController")
public class YxdController {
	
	@Qualifier("yxdServiceImpl")
	private YxdService yxdService;
	
	@RequestMapping("insert")
	public void insert() {
		System.out.println("访问了YxdController中的insert方法");
	}
	
	@RequestMapping("delete")
	public String delete() {
		return "访问了YxdController中的delete方法";
	}
}
