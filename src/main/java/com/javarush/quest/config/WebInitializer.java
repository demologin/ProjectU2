package com.javarush.quest.config;

import com.javarush.quest.config.annotation.Component;
import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.annotation.ServletMetadata;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebInitializer implements ServletMetadata {

    public void registerServlets(ServletContext servletContext,
                                 ApplicationContext applicationContext) {
        Map<Class<?>, Object> beans = applicationContext.getBeans();

        beans.forEach((type, bean) -> {
            if (!HttpServlet.class.isAssignableFrom(type)) return;
            if (!type.isAnnotationPresent(Servlet.class)) return;

            String[] urlPatterns = getUrlPatterns(type);
            if (urlPatterns.length == 0) return;

            servletContext
                    .addServlet(type.getSimpleName(), (HttpServlet) bean)
                    .addMapping(urlPatterns);
        });
    }
}
