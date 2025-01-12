package com.traveller.adapter;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;





public class HttpExchangeAdapter implements HttpExchangeRequest,HttpExchangeResponse{
    final HttpExchange exchange;

    public HttpExchangeAdapter(HttpExchange exchange){
        this.exchange = exchange;
    }

    @Override
    public String getRequestMethod() {
        return exchange.getRequestMethod();
    }

    @Override
    public URI getRequstURI() {
        return exchange.getRequestURI();
    }

    @Override
    public Headers getResponseHeaders() {
        return exchange.getResponseHeaders();
    }

    @Override
    public void sendResponseHeaders(int statusCode, long respLength) {
        try {
            exchange.sendResponseHeaders(statusCode,respLength);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OutputStream getResponseBody() {
        return exchange.getResponseBody();
    }
}
