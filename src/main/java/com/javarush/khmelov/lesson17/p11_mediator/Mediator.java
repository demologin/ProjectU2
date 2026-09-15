package com.javarush.khmelov.lesson17.p11_mediator;

abstract class Mediator {
    public abstract void send(String message, Colleague colleague);
}
