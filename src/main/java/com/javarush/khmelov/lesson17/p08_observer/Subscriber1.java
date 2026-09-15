package com.javarush.khmelov.lesson17.p08_observer;

class Subscriber1 implements Subscriber {
    @Override
    public void doAction(String message) {
        System.out.println(message + " from "
                + this.getClass().getSimpleName());
    }
}
