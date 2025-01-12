package com.traveller.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.traveller.adapter.HttpExchangeAdapter;
import com.traveller.impl.HttpServletRequestImpl;
import com.traveller.impl.HttpServletResponseImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

@Slf4j
public class HttpConnector implements HttpHandler,AutoCloseable{

    final HttpServer httpServer;

    public HttpConnector() throws IOException{
        String host="0.0.0.0";
        int port=8080;
        this.httpServer = HttpServer.create(new InetSocketAddress(host,port), 0,"/",this);
        this.httpServer.start();
        log.info("miniTomcat http server started at {}:{}...", host, port);
    }


    @Override
    public void close() throws Exception {
        this.httpServer.stop(3);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        log.info("{}: {}?{}", exchange.getRequestMethod(), exchange.getRequestURI().getPath(), exchange.getRequestURI().getRawQuery());
        var adapter =new HttpExchangeAdapter(exchange);
        var request =new HttpServletRequestImpl(adapter);
        var response = new HttpServletResponseImpl(adapter);
        process(request,response);
    }

    void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //TODO
        String name =req.getParameter("name");
        String html = "<h1>Hello, " + (name == null ? "world" : name) + ".</h1>";
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter pw =resp.getWriter();
        pw.write(html);
        pw.close();
    }


}
