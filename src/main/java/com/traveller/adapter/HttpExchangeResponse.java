package com.traveller.adapter;

import com.sun.net.httpserver.Headers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public interface HttpExchangeResponse{
    Headers getResponseHeaders();
    void sendResponseHeaders(int statusCode, long respLength) throws IOException;
    OutputStream getResponseBody();

    InetSocketAddress getRemoteAddress();
}
