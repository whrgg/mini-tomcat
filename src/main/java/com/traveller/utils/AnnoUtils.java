package com.traveller.utils;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.Servlet;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;

import java.util.*;
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

    public static String getFilterName(Class<? extends Filter> clazz){
        WebFilter wf = clazz.getAnnotation(WebFilter.class);
        if(wf==null||wf.filterName().isEmpty()){
            return defaultNameByClass(clazz);
        }
        return wf.filterName();
    }

    public static Map<String, String> getFilterInitParams(Class<? extends Filter> clazz){
        WebFilter wf = clazz.getAnnotation(WebFilter.class);
        WebInitParam[] webInitParams = wf.initParams();
        if(webInitParams!=null&&webInitParams.length>0){
            return initParamsToMap(webInitParams);
        }
        return Map.of();
    }

    public static String[] getFilterUrlPatterns(Class<?> clazz){
        WebFilter wf = clazz.getAnnotation(WebFilter.class);
        if (wf==null){
            return new String[0];
        }
        return arraysToSet(wf.value(), wf.urlPatterns()).toArray(String[]::new);
    }

    public static EnumSet<DispatcherType> getFilterDispatcherTypes(Class<? extends Filter> clazz) {
        WebFilter w = clazz.getAnnotation(WebFilter.class);
        if (w == null) {
            return EnumSet.of(DispatcherType.REQUEST);
        }
        return EnumSet.copyOf(Arrays.asList(w.dispatcherTypes()));
    }

}
