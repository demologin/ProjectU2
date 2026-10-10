package com.javarush.quest;

import com.javarush.quest.config.ApplicationContext;
import com.javarush.quest.config.DataConfig;
import com.javarush.quest.config.WebConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class QuestApplication implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = new ApplicationContext();
        context.scan(getClass().getPackageName());

        context.getBean(DataConfig.class).loadData();
        WebConfig webConfig = context.getBean(WebConfig.class);
        webConfig.registerServlets(sce.getServletContext(), context);
    }
}
