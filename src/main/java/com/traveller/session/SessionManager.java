package com.traveller.session;

import com.traveller.context.ServletContextImpl;
import com.traveller.utils.DateUtils;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SessionManager implements Runnable{
    ServletContextImpl servletContext;
    Map<String,HttpSessionImpl> sessions=new ConcurrentHashMap<>();
    int inactiveInterval;

    public SessionManager(ServletContextImpl servletContext, int inactiveInterval) {
        this.servletContext = servletContext;
        this.inactiveInterval = inactiveInterval;
        Thread t = new Thread(this, "Session-Cleanup-Thread");
        t.setDaemon(true);
        t.start();
    }


    public HttpSession getSession(String sessionId) {
        HttpSessionImpl session=sessions.get(sessionId);
        if(session==null) {
            session=new HttpSessionImpl(this.servletContext,sessionId,inactiveInterval);
            sessions.put(sessionId,session);
        }else{
            session.lastAccessedTime = System.currentTimeMillis();
        }
        return session;
    }

    public void removeSession(HttpSession session) {
        this.sessions.remove(session.getId());
    }

    @Override
    public void run() {
        for (;;) {
            // 每60秒扫描一次:
            try {
                Thread.sleep(60_000L);
            } catch (InterruptedException e) {
                break;
            }
            // 当前时间:
            long now = System.currentTimeMillis();
            // 遍历Session:
            for (String sessionId : sessions.keySet()) {
                HttpSession session = sessions.get(sessionId);
                // 判断是否过期:
                if (session.getLastAccessedTime() + session.getMaxInactiveInterval() * 1000L < now) {
                    // 删除过期的Session:
                    log.warn("remove expired session: {}, last access time: {}", sessionId, DateUtils.formatDateTimeGMT(session.getLastAccessedTime()));
                    session.invalidate();
                }
            }
        }
    }
}
