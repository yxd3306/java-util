package com.session.redis.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 重写HttpServerRequest对象
 * @describe
 * @author yxd
 * @data 2018-08-16 11:03:27
 *
 */
public class MyHttpServerRequest extends HttpServletRequestWrapper{

    HttpServletRequest request;
    HttpServletResponse response;
    
    public MyHttpServerRequest(HttpServletRequest request, HttpServletResponse response) {
        super(request);
        this.request=request;
        this.response=response;
    }

    //重写getSession()
    @Override
    public HttpSession getSession() {
        return new MyHttpSession(request,response);
    }
    

}
