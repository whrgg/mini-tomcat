package com.traveller.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
@Slf4j
public class HelloHttpSessionListener implements HttpSessionListener {


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info(">>> HttpSession created: {}", se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info(">>> HttpSession destroyed: {}", se.getSession());
    }
}
