package com.javarush.khmelov.lesson17.p11_mediator;

class ConcreteColleague2 extends Colleague {

    ConcreteColleague2(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void notify(String message) {
        System.out.println("Colleague2 gets message: " + message);
    }
}
