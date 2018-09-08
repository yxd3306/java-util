package com.dynamic.proxy.stat;

/**
 * @Package: com.dynamic.proxy.stat
 * @Auther: yxd
 * @Date: 2018-09-08 10:47:51
 * @Description: 静态代理 提供服务  业务变更也需要更改源码，代码可扩展性不高 维护难度大
 */
public class ProxyServiceFactory implements ManToolsFactory ,WoManToolsFactory{



    @Override
    public void saleManTools(String size) {
        System.out.println("根据您的需求为您预约一个尺寸为："+size+"的女模");
    }


    @Override
    public void saleWoManTools(float height) {
        System.out.println("根据您的需求为您预约一个身高为："+height+"的男模");
    }
}
