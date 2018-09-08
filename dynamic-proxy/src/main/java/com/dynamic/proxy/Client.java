package com.dynamic.proxy;


import com.dynamic.proxy.dynamic.*;

/**
 * @Package: com.dynamic.proxy
 * @Auther: yxd
 * @Date: 2018-09-08 10:56:39
 * @Description:
 */
public class Client {

    public static void main(String[] args) {
//        ManToolsFactory manToolsFactory = new ProxyServiceFactory();
//        WoManToolsFactory woManToolsFactory = new ProxyServiceFactory();
//
//        StatProxyFactory proxyProject = new StatProxyFactory(manToolsFactory,woManToolsFactory);
//        proxyProject.saleManTools("E");
//        proxyProject.saleWoManTools(1.8f);

        ManToolsFactory manToolsFactory = new ProxyServiceFactory();
        WoManToolsFactory woManToolsFactory = new ProxyServiceFactory();
        ChilderToolsFactory childerToolsFactory = new ProxyServiceFactory();

        DynamProxyFactory dynamProxyFactory = new DynamProxyFactory();
        dynamProxyFactory.setFactory(manToolsFactory);
        ManToolsFactory dynamProxyFactoryObect1 = (ManToolsFactory) dynamProxyFactory.getProxyInstance();
        dynamProxyFactoryObect1.saleManTools("E");

        System.out.println("======================================");

        dynamProxyFactory.setFactory(woManToolsFactory);
        WoManToolsFactory dynamProxyFactoryObect2 = (WoManToolsFactory) dynamProxyFactory.getProxyInstance();
        //dynamProxyFactoryObect2.saleWoManTools("1.8m");
        dynamProxyFactoryObect2.saleWoManTools(1.8f);

        System.out.println("======================================");

        dynamProxyFactory.setFactory(childerToolsFactory);
        ChilderToolsFactory dynamProxyFactoryObect3 = (ChilderToolsFactory) dynamProxyFactory.getProxyInstance();
        dynamProxyFactoryObect3.saleMilkBrand("三鹿");


    }

}
