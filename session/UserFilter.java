package com.session.redis.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤用户请求，在执行到controller之前替换request对象
 * @describe
 * @author yxd
 * @data 2018-08-24 16:53:38
 *
 */
@WebFilter(urlPatterns="/*")
public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //request对象换成自定义（重写）的request对象
        chain.doFilter(new MyHttpServerRequest((HttpServletRequest)request, (HttpServletResponse)response), response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

}
