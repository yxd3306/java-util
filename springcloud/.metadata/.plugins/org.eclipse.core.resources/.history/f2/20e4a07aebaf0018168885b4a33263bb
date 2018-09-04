package com.yxd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
// EnableEurekaClient 和 EnableDiscoveryClient 的区别 EnableDiscoveryClient集成zookeeper
@EnableDiscoveryClient
// EurekaServer 注册服务实例 对EurekaServer续约 关闭之前对EurekaServer取消续约
// 查询EurekaServer服务列表
public class HelloServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(HelloServiceApp.class, args);
	}
}
