package com.javarush.khmelov.lesson17.p11_mediator;

abstract class Colleague {

    private final Mediator mediator;

    Colleague(Mediator mediator) {
        this.mediator = mediator;
    }

    void send(String message) {
        mediator.send(message, this);
    }

    public abstract void notify(String message);
}
