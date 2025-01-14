package com.traveller.impl;

import com.traveller.adapter.HttpExchangeResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class HttpServletResponseImpl implements HttpServletResponse {
    final HttpExchangeResponse exchangeResponse;
    int status = 200;
    String contentType;
    public HttpServletResponseImpl(HttpExchangeResponse httpExchangeResponse) {
        this.exchangeResponse=httpExchangeResponse;
    }

    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public boolean containsHeader(String name) {
        return false;
    }

    @Override
    public String encodeURL(String url) {
        return "";
    }

    @Override
    public String encodeRedirectURL(String url) {
        return "";
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        this.status = sc;
        PrintWriter pw = getWriter();
        pw.write(String.format("<h1>%d %s</h1>", sc, msg));
        pw.close();

    }

    @Override
    public void sendError(int sc) throws IOException {
        sendError(sc, "Error");
    }

    @Override
    public void sendRedirect(String location) throws IOException {

    }

    @Override
    public void setDateHeader(String name, long date) {

    }

    @Override
    public void addDateHeader(String name, long date) {

    }

    @Override
    public void setHeader(String name, String value) {
        this.exchangeResponse.getResponseHeaders().set(name, value);
    }

    @Override
    public void addHeader(String name, String value) {

    }

    @Override
    public void setIntHeader(String name, int value) {

    }

    @Override
    public void addIntHeader(String name, int value) {

    }

    @Override
    public void setStatus(int sc) {

    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public String getHeader(String name) {
        return "";
    }

    @Override
    public Collection<String> getHeaders(String name) {
        return List.of();
    }

    @Override
    public Collection<String> getHeaderNames() {
        return List.of();
    }

    @Override
    public String getCharacterEncoding() {
        return "";
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        this.exchangeResponse.sendResponseHeaders(200,0);
        return new PrintWriter(this.exchangeResponse.getResponseBody(),true, StandardCharsets.UTF_8);
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentLengthLong(long len) {

    }

    @Override
    public void setContentType(String type) {
        setHeader("Content-Type", type);
        this.contentType = type;
    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
