package com.javarush.sharifullina.controller;

import com.javarush.sharifullina.cmd.Command;
import com.javarush.sharifullina.config.Winter;
import com.javarush.sharifullina.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpResolver {

    public Command resolve(HttpServletRequest req) {
        String uri = req.getRequestURI();
        uri = uri.endsWith("/") ? "/home" : uri;
        uri = uri.split("[/?#]")[1].replace("/","");
        String simpleName = convertKebabStyleToCamelCase(uri);
        String packageName = Command.class.getPackageName();
        String className = packageName + "." + simpleName;
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
            return (Command) Winter.getBean(clazz);
        }catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            throw new AppException(e);
        }
    }

    private String convertKebabStyleToCamelCase(String uri) {
        StringBuilder sb = new StringBuilder();
        boolean capitalizeNext = true;
        char[] charArray = uri.toCharArray();
        for (char c : charArray) {
            if(c == '-'){
                capitalizeNext = true;
            }else {
                if(capitalizeNext){
                    sb.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                }else  {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }
}
