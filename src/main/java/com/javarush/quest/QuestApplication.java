package com.javarush.quest;

import com.javarush.quest.config.ApplicationContext;
import com.javarush.quest.config.DataInitializer;
import com.javarush.quest.config.WebInitializer;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class QuestApplication implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = new ApplicationContext();
        context.scan(this.getClass().getPackageName());

        context.getBean(DataInitializer.class).initialize();
        context.getBean(WebInitializer.class).registerServlets(
                sce.getServletContext(), context);
    }
}
