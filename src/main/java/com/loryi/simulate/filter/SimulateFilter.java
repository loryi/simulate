package com.loryi.simulate.filter;

import com.alibaba.fastjson.JSONObject;
import com.loryi.simulate.util.SimulateHelperConst;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author: loryi
 * @description: 模拟接口过滤器
 * @create: 2020/04/08 08:39
 **/
@WebFilter(urlPatterns = "/*", filterName = "模拟接口过滤器")
public class SimulateFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("++++++++模拟接口过滤器初始化中");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //添加跨域调用相关信息 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization");


        String url = request.getRequestURI();
        //后续 请求路径匹配还需要支持正则方式
        if(SimulateHelperConst.urlJson.containsKey(url)){
            //存在对应的url配置地址
            JSONObject json = SimulateHelperConst.urlJson.getJSONObject(url);
            String method = request.getMethod();
            if(json.containsKey(method.toLowerCase())){
                //请求方式也匹配到
                //转发到simulate接口进行数据获取？
                Map params = (Map) json.get(method.toLowerCase());
                //转发？似乎多此一举
                HttpSession session = request.getSession();
                session.setAttribute(url, params);
                session.setAttribute("path", url);
                request.getRequestDispatcher("/simulate").forward(request, response);
            }else{
                //请求方式不匹配
                throw new HttpRequestMethodNotSupportedException(method, json.keySet());
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("++++++++模拟接口过滤器销毁");
    }
}
