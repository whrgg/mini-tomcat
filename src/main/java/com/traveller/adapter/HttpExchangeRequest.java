package com.traveller.adapter;

import java.net.URI;

public interface HttpExchangeRequest{
    String getRequestMethod();
    URI getRequstURI();
}