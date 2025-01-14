package com.traveller.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class FilterChainImpl implements FilterChain {
    public final Filter[] filters;
    public final Servlet servlet;

    final int total;
    int index=0;
    public FilterChainImpl(Filter[] filters, Servlet servlet) {

        //排序做过滤
        this.filters = filters;
        this.servlet = servlet;
        this.total = filters.length;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if (index < total) {
            int current = index;
            index++;
            // 调用下一个Filter处理:
            filters[current].doFilter(request, response, this);
        } else {
            // 调用Servlet处理:
            servlet.service(request, response);
        }
    }
}
