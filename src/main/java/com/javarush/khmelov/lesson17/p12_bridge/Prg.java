package com.javarush.khmelov.lesson17.p12_bridge;

abstract class Prg {
    protected Dev developer;

    Prg(Dev developer) {
        this.developer = developer;
    }

    abstract void developProgram();
}
