package com.yxd.springcloud;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 暴露http调用服务的Controller组件类
@RestController
public class MessageController {

	
	@RequestMapping(value= "/send")
	public String send() {
		String str = "喻湘东发布的服务，port:8083";
		System.out.println(str);
		return str;
	}
	
	
	
	
}
