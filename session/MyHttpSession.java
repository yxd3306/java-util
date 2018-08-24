package com.session.redis.filter;

import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;


import com.alibaba.fastjson.JSONObject;
import com.session.redis.api.RedisApi;


/**
         重写HttpServletResponse
 * @describe
 * @author yxd
 * @data 2018-08-16 11:03:52
 *
 */
@SuppressWarnings("deprecation")
public class MyHttpSession implements HttpSession{

    //绑定当前线程
    private ThreadLocal<String> local = new ThreadLocal<String>();
    
    HttpServletRequest request;
    HttpServletResponse response;
    
    public MyHttpSession(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated constructor stub
        this.request=request;
        this.response=response;
    }

  //根据name去redis里面取对应信息
    public Object getAttribute(String name) {
        //获取sessionId
        String sessionId = getSessionIdFromCookie();
        if(sessionId!=null) {
            List<String> list = RedisApi.lrange(sessionId);
            if(list!=null&&list.size()>0) {
                return getValueByKey(name,list);
            }
        }
        return null;
    }
    
    //通过key获取redis对应value
    private Object getValueByKey(String key,List<String> list) {
        for(String each:list) {
            JSONObject object = JSONObject.parseObject(each);
            if(object.containsKey(key)) {
                return object.get(key);
            }
        }
        return null;
    }
    
    // 重写session对象的setAttribute方法
    //把对应的name value 存到redis中
    public void setAttribute(String name, Object value) {
        //获取sessionId
        String sessionId = getSessionIdFromCookie();
        if(sessionId==null) {
            sessionId = local.get();
            if(sessionId==null) {
                String sessionCode="";
                try {
                    sessionCode=DateUtil.dateTimeToDateStringIfTimeSymbol(new Date());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                sessionId = sessionCode+UUID.randomUUID();
                //防止一次请求多次调用setAttribute，从而生成多个sessionId
                local.set(sessionId);
            }
        }
        JSONObject jo = new JSONObject();
        jo.put(name, value);
        //将数据写入redis
        RedisApi.lpush(sessionId, jo.toJSONString());
        //sessionId写入cookie中
        writeCookie(sessionId);
    }
    
    //将sessionId存到cookie
    private void writeCookie(String sessionId) {
        // TODO Auto-generated method stub
        Cookie cookie = new Cookie("sessionId", sessionId);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
                      本地cookie获取sessionid
     * Key : sessionId
     * Value : 自定义符号+UUID
     * @return
     */
    private String getSessionIdFromCookie() {
        //获取cookie
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for(Cookie cookie:cookies) {
                if("sessionId".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    
    
    
    
    @Override
    public long getCreationTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getLastAccessedTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getMaxInactiveInterval() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        // TODO Auto-generated method stub
        return null;
    }

    

    @Override
    public Object getValue(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getValueNames() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void putValue(String name, Object value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeAttribute(String name) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeValue(String name) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void invalidate() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isNew() {
        // TODO Auto-generated method stub
        return false;
    }

}
