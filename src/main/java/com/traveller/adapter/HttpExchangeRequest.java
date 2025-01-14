package com.traveller.adapter;

import com.sun.net.httpserver.Headers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

public interface HttpExchangeRequest{
    String getRequestMethod();
    URI getRequstURI();

    Headers getRequestHeaders();

    URI getRequestURI();

    InetSocketAddress getLocalAddress();

    byte[] getRequestBody() throws IOException;
}