package com.traveller.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class SimpleHandler implements HttpHandler {
    private static final Logger log = LoggerFactory.getLogger(SimpleHandler.class);

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
