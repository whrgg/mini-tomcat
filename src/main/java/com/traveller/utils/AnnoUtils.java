package com.traveller.utils;

import jakarta.servlet.Servlet;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnoUtils {

    public static String getServletName(Class<?> clazz){
        WebServlet w = clazz.getAnnotation(WebServlet.class);
        if(w!=null&&!w.name().isEmpty()){
            return w.name();
        }
        return defaultNameByClass(clazz);
    }

    private static String defaultNameByClass(Class<?> clazz){
        String name = clazz.getSimpleName();
        return name.toLowerCase();
    }

    public static String[] getServletUrlPatterns(Class<? extends Servlet> clazz){
        WebServlet w = clazz.getAnnotation(WebServlet.class);
        if(w==null){
            return null;
        }
        return arraysToSet(w.value(),w.urlPatterns()).toArray(String[]::new);
    }

    public static Map<String, String> getServletInitParams(Class<? extends Servlet> clazz){
        WebServlet w = clazz.getAnnotation(WebServlet.class);
        if(w==null){
            return Map.of();
        }
        return initParamsToMap(w.initParams());

    }

    private static Set<String> arraysToSet(String [] value, String [] urlPatterns){
        Set<String> set= new HashSet<>();
        if(value!=null){
            for (String s : value) {
                set.add(s);
            }
        }

        if(urlPatterns!=null){
            for (String urlPattern : urlPatterns) {
                set.add(urlPattern);
            }
        }


        return set.size()==0?null:set;
    }


    //WebInitParam 存储的是组件初始化参数
    public static Map<String, String> initParamsToMap(WebInitParam[] params){
        return Arrays.stream(params).collect(Collectors.toMap(p->p.name(),p->p.value()));
    }
}
