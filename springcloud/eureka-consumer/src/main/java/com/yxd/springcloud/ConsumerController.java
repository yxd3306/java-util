package com.yxd.springcloud;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping("/message")
	public String message()throws Exception{
		String result = restTemplate.getForObject("http://messageService/send", String.class,new HashMap());
		System.out.println("调用："+result);
		return result;
	}
	
}
