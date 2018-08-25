package com.spring.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SpringMvc 实际上是基于方法处理模式 所以它可以使用单例模式
 * Status:实际上是基于类处理模式
 * Handler为什么不抽取接口？原因就是Handler处理器实际是一个抽象的
 * 采用框架级别的适配器模式
 * RequestMapping:用来确定找哪个Handler去处理，细化到方法（Method）对象
 * 最后实现请求落地处理最后一站
 * @author yxd
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
	String value() default "";
}
