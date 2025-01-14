package com.traveller.session;

import com.traveller.context.ServletContextImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;

public class HttpSessionImpl implements HttpSession {

    ServletContextImpl servletContext;
    String sessionId;
    int maxInactiveInterval;
    long creationTime;
    long lastAccessedTime;
    Attributes attributes;

    public HttpSessionImpl(ServletContextImpl servletContext, String sessionId, int maxInactiveInterval) {
        this.servletContext = servletContext;
        this.sessionId = sessionId;
        this.creationTime = this.lastAccessedTime = System.currentTimeMillis();
        this.attributes = new Attributes(true);
        setMaxInactiveInterval(maxInactiveInterval);
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getId() {
        return this.sessionId;
    }

    @Override
    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }

    @Override
    public int getMaxInactiveInterval() {
        return this.maxInactiveInterval;
    }

    @Override
    public Object getAttribute(String name) {
        return this.attributes.getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        checkValid();
        return this.attributes.getAttributeNames();
    }

    @Override
    public void setAttribute(String name, Object value) {
        checkValid();
        if (value == null) {
            removeAttribute(name);
        } else {
            this.attributes.setAttribute(name, value);
        }

    }

    @Override
    public void removeAttribute(String name) {
        checkValid();
        this.attributes.removeAttribute(name);
    }

    @Override
    public void invalidate() {
        this.servletContext.sessionManager.removeSession(this);
        this.sessionId = null;
    }

    @Override
    public boolean isNew() {
        return this.creationTime==this.lastAccessedTime;
    }
    void checkValid() {
        if (this.sessionId == null) {
            throw new IllegalStateException("Session is already invalidated.");
        }
    }
}
