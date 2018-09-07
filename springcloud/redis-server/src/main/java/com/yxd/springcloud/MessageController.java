package com.yxd.springcloud;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.yxd.springcloud
 * @Auther: yxd
 * @Date: 2018-09-06 15:05:11
 * @Description:
 */
@RestController
public class MessageController {


    @RequestMapping(value = "/send")
    public String send(){
        return "spring cloud + eureka 分布式架构";
    }



}
