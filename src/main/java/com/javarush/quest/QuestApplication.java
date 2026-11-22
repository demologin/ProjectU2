package com.javarush.quest;

import com.javarush.quest.config.ApplicationContext;
import com.javarush.quest.config.DataConfig;
import com.javarush.quest.config.WebConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

import static com.javarush.quest.exception.ErrorMessage.*;

@Slf4j
@WebListener
public class QuestApplication implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            log.info("Loading application context");
            ApplicationContext context = new ApplicationContext();
            String basePackage = getClass().getPackageName();
            context.scan(basePackage);
            log.info("Application context initialized");

            log.info("Loading data config");
            DataConfig dataConfig = context.getBean(DataConfig.class);
            dataConfig.loadData();

            log.info("Registering servlets");
            ServletContext servletContext = servletContextEvent.getServletContext();
            WebConfig webConfig = context.getBean(WebConfig.class);
            webConfig.registerServlets(servletContext, context);

            log.info("Quest Application started");
        } catch (Exception e) {
            log.error("Application failed to start due to errors", e);
            throw new RuntimeException(STARTUP_FAILED, e);
        }
    }
}
