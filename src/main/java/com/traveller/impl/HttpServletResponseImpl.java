package com.traveller.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


import com.traveller.adapter.HttpExchangeResponse;
import com.traveller.session.HttpHeaders;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class HttpServletResponseImpl implements HttpServletResponse {

    final HttpExchangeResponse exchangeResponse;
    final HttpHeaders headers;

    int status = 200;
    int bufferSize = 1024;
    Boolean callOutput = null;
    ServletOutputStream output;
    PrintWriter writer;

    String contentType;
    long contentLength = 0;
    List<Cookie> cookies = null;
    boolean committed = false;

    public HttpServletResponseImpl(HttpExchangeResponse exchangeResponse) {
        this.exchangeResponse = exchangeResponse;
        this.headers = new com.traveller.session.HttpHeaders(exchangeResponse.getResponseHeaders());
        this.setContentType("text/html");
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (callOutput == null) {
            commitHeaders(0);
            this.output = new ServletOutputStreamImpl(this.exchangeResponse.getResponseBody());
            this.callOutput = Boolean.TRUE;
            return this.output;
        }
        if (callOutput.booleanValue()) {
            return this.output;
        }
        throw new IllegalStateException("Cannot open output stream when writer is opened.");
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (callOutput == null) {
            commitHeaders(0);
            this.writer = new PrintWriter(this.exchangeResponse.getResponseBody(), true, StandardCharsets.UTF_8);
            this.callOutput = Boolean.FALSE;
            return this.writer;
        }
        if (!callOutput.booleanValue()) {
            return this.writer;
        }
        throw new IllegalStateException("Cannot open writer when output stream is opened.");
    }

    @Override
    public void setContentLength(int len) {
        this.contentLength = len;
    }

    @Override
    public void setContentLengthLong(long len) {
        this.contentLength = len;
    }

    @Override
    public void setContentType(String type) {
        this.contentType = type;
        setHeader("Content-Type", contentType);
    }

    @Override
    public void setBufferSize(int size) {
        if (this.callOutput != null) {
            throw new IllegalStateException("Output stream or writer is opened.");
        }
        if (size < 0) {
            throw new IllegalArgumentException("Invalid size: " + size);
        }
        this.bufferSize = size;
    }

    @Override
    public int getBufferSize() {
        return this.bufferSize;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (this.callOutput == null) {
            throw new IllegalStateException("Output stream or writer is not opened.");
        }
        if (this.callOutput.booleanValue()) {
            this.output.flush();
        } else {
            this.writer.flush();
        }
    }

    @Override
    public void resetBuffer() {
        checkNotCommitted();
    }

    @Override
    public boolean isCommitted() {
        return this.committed;
    }

    @Override
    public void reset() {
        checkNotCommitted();
        this.status = 200;
        this.headers.clearHeaders();
    }

    @Override
    public void addCookie(Cookie cookie) {
        checkNotCommitted();
        if (this.cookies == null) {
            this.cookies = new ArrayList<>();
        }
        this.cookies.add(cookie);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        checkNotCommitted();
        this.status = sc;
        commitHeaders(-1);
    }

    @Override
    public void sendError(int sc) throws IOException {
        sendError(sc, "Error");
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        checkNotCommitted();
        this.status = 302;
        this.headers.setHeader("Location", location);
        commitHeaders(-1);
    }

    @Override
    public void setStatus(int sc) {
        checkNotCommitted();
        this.status = sc;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    // header operations //////////////////////////////////////////////////////

    @Override
    public boolean containsHeader(String name) {
        return this.headers.containsHeader(name);
    }

    @Override
    public String getHeader(String name) {
        return this.headers.getHeader(name);
    }

    @Override
    public Collection<String> getHeaders(String name) {
        List<String> hs = this.headers.getHeaders(name);
        if (hs == null) {
            return List.of();
        }
        return hs;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return Collections.unmodifiableSet(this.headers.getHeaderNames());
    }

    @Override
    public void setDateHeader(String name, long date) {
        checkNotCommitted();
        this.headers.setDateHeader(name, date);
    }

    @Override
    public void addDateHeader(String name, long date) {
        checkNotCommitted();
        this.headers.addDateHeader(name, date);
    }

    @Override
    public void setHeader(String name, String value) {
        checkNotCommitted();
        this.headers.setHeader(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        checkNotCommitted();
        this.headers.addHeader(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        checkNotCommitted();
        this.headers.setIntHeader(name, value);
    }

    @Override
    public void addIntHeader(String name, int value) {
        checkNotCommitted();
        this.headers.addIntHeader(name, value);
    }

    void commitHeaders(long length) throws IOException {
        this.exchangeResponse.sendResponseHeaders(this.status, length);
        this.committed = true;
    }

    public void cleanup() throws IOException {
        if (this.callOutput != null) {
            if (this.callOutput.booleanValue()) {
                this.output.close();
            } else {
                this.writer.close();
            }
        }
    }

    // check if not committed:
    void checkNotCommitted() {
        if (this.committed) {
            throw new IllegalStateException("Response is committed.");
        }
    }

    @Override
    public String getCharacterEncoding() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setCharacterEncoding(String charset) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setLocale(Locale loc) {
        // TODO Auto-generated method stub
    }

    @Override
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encodeURL(String url) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encodeRedirectURL(String url) {
        // TODO Auto-generated method stub
        return null;
    }
}