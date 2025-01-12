package com.traveller.adapter;

import com.sun.net.httpserver.Headers;

import java.io.OutputStream;

public interface HttpExchangeResponse{
    Headers getResponseHeaders();
    void sendResponseHeaders(int statusCode, long respLength);
    OutputStream getResponseBody();
}
