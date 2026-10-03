package com.javarush.quest.config;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.config.annotation.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;

@Component
public class WebInitializer {

    public void registerServlets(ServletContext servletContext,
                                 ApplicationContext applicationContext) {
        applicationContext.getBeans().forEach((type, bean) -> {
            if (HttpServlet.class.isAssignableFrom(type)
                        && type.isAnnotationPresent(Servlet.class)) {
                String[] urlPatterns = type.getAnnotation(Servlet.class).value();

                if (urlPatterns.length > 0) {
                    servletContext.addServlet(type.getSimpleName(), (HttpServlet) bean)
                                  .addMapping(urlPatterns);
                }
            }
        });
    }
}
