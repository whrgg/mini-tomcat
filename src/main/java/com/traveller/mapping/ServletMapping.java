package com.traveller.mapping;

import jakarta.servlet.Servlet;

import java.util.regex.Pattern;

public class ServletMapping extends AbstractMapping{
    public final Servlet servlet;

    public ServletMapping(String urlPattern, Servlet servlet) {
        super(urlPattern);
        this.servlet = servlet;
    }

}
