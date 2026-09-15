package com.javarush.khmelov.lesson17.p10_strategy;

public interface AuthStrategy {
    boolean checkLogin(String name, String password);
}
