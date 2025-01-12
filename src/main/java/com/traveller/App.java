package com.traveller;

import com.traveller.handler.HttpConnector;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Hello world!
 *
 */
@Slf4j
public class App
{
    public static void main( String[] args )
    {
        try (HttpConnector connector = new HttpConnector()) {
            for (;;) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("mini-tomcat http server was shutdown.");
    }
}
