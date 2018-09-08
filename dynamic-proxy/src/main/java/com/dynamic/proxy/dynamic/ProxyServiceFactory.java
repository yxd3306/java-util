package com.dynamic.proxy.dynamic;


/**
 * @Package: com.dynamic.proxy.stat
 * @Auther: yxd
 * @Date: 2018-09-08 10:47:51
 * @Description: 提供服务
 */
public class ProxyServiceFactory implements ManToolsFactory, WoManToolsFactory, ChilderToolsFactory{



    @Override
    public void saleManTools(String size) {
        System.out.println("根据您的需求为您预约一个尺寸为："+size+"的女模");
    }


//    @Override
//    public void saleWoManTools(folt height) {
//        System.out.println("根据您的需求为您预约一个身高为："+height+"的男模");
//    }

    @Override
    public void saleWoManTools(float height) {
        System.out.println("根据您的需求为您预约一个身高为："+height+"的男模");
    }

    @Override
    public void saleMilkBrand(String milkBrand) {
        System.out.println("根据您的需求为您代购品牌为："+milkBrand+"的奶粉");
    }
}
