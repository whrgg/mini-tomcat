package com.traveller.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
@Slf4j
public class SimpleHttpServer implements HttpHandler ,AutoCloseable {
    public static void main(String[] args) {
        String host ="127.0.0.1";
        int port = 8080;
        try(SimpleHttpServer connector = new SimpleHttpServer(host,port)){
            while(true){
                try {
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    final String host;
    final int port;
    final HttpServer server;

    public SimpleHttpServer(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(host,port), 0,"/",this);
        this.server.start();
    }

    @Override
    public void close() throws Exception {
        server.stop(3);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method =exchange.getRequestMethod();
        URI url = exchange.getRequestURI();
        String path = url.getPath();
        String query = url.getQuery();
        log.info("{}:{}?{}", method, path, query);
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/html; charset=utf-8");
        responseHeaders.set("Cache-Control", "no-cache");
        exchange.sendResponseHeaders(200,0);
        String s = "<h1>Hello, world.</h1><p>" + LocalDateTime.now().withNano(0) + "</p>";
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(s.getBytes(StandardCharsets.UTF_8));
        }
    }
}
