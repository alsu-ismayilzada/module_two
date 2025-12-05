package com.example.module_two.task15.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.logging.Logger;

@WebListener
public class MyListener implements ServletContextListener {

    Logger logger = Logger.getLogger(MyListener.class.getName());

    public void contextInitialized(ServletContextEvent sce) {
        logger.info("MyListener contextInitialized");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("MyListener contextDestroyed");
    }
}
