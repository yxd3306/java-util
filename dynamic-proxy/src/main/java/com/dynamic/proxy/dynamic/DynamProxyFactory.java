package com.dynamic.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理模式：jdk动态代理
 * @Package: com.dynamic.proxy.dynamic
 * @Auther: yxd
 * @Date: 2018-09-08 11:22:42
 * @Description: 动态代理对象生产类 InvocationHandler：管理任务调度 Proxy：实现动态代理
 *
 */
public class DynamProxyFactory implements InvocationHandler {

    // 被代理的对象，提供服务。
    private Object factoryObject;

    // 返回代理对象
    public Object getFactory(){
        return factoryObject;
    }


    // 获取proxy动态代理对象
    public Object getProxyInstance(){
        // newProxyInstance  三个参数 1、类加载器 2、接口 3、this包含InvocationHandler
        return Proxy.newProxyInstance(factoryObject.getClass().getClassLoader(), factoryObject.getClass().getInterfaces(), this);
    }


    // 设置代理对象
    public void setFactory(Object factoryObject){
        this.factoryObject = factoryObject;
    }


    @Override
    // 通过动态代理对象对方法进行增强  Method method = public Object invoke
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 处理用户需求之前可以添加的业务
        doSomeThingBefore();
        // 通过反射机制，调用自身invoke方法，把服务对象和参数封装到方法里面，最后返回一个最终提供服务的对象
        Object resultObject = method.invoke(factoryObject, args);
        // 处理完用户需求之后添加的业务
        doSomeThingEnd();
        return resultObject;
    }

    // 前置增强
    private void doSomeThingBefore() {
        System.out.println("收到用户需求，开始处理。");
    }

    // 后置增强
    private void doSomeThingEnd() {
        System.out.println("用户需求处理完毕。");
    }
}
