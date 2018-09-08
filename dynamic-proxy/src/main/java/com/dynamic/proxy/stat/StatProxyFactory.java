package com.dynamic.proxy.stat;

/**
 * @Package: com.dynamic.proxy.stat
 * @Auther: yxd
 * @Date: 2018-09-08 10:48:50
 * @Description: 业务变更也需要更改源码，代码可扩展性不高 维护难度大
 */
public class StatProxyFactory extends ProxyServiceFactory {

    protected ManToolsFactory manToolsFactory;
    protected WoManToolsFactory woManToolsFactory;

    public StatProxyFactory(ManToolsFactory manToolsFactory, WoManToolsFactory woManToolsFactory){
        super();
        this.manToolsFactory = manToolsFactory;
        this.woManToolsFactory = woManToolsFactory;
    }

    @Override
    public void saleManTools(String size) {
        // 处理用户需求之前可以添加的业务
        doSomeThingBefore();
        // 用户需求
        manToolsFactory.saleManTools(size);
        // 处理完用户需求之后添加的业务
        doSomeThingEnd();
    }

    @Override
    public void saleWoManTools(float height) {
        // 处理用户需求之前可以添加的业务
        doSomeThingBefore();
        // 用户需求
        woManToolsFactory.saleWoManTools(height);
        // 处理完用户需求之后添加的业务
        doSomeThingEnd();
    }

    // 前置增强业务
    private void doSomeThingBefore() {
        System.out.println("收到用户需求，开始处理。");

    }

    // 后置增强业务
    private void doSomeThingEnd() {
        System.out.println("用户需求处理完毕");
    }
}
