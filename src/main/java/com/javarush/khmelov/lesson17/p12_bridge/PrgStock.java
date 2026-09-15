package com.javarush.khmelov.lesson17.p12_bridge;

class PrgStock extends Prg {
    PrgStock(Dev developer) {
        super(developer);
    }

    @Override
    void developProgram() {
        System.out.println("Stock dev in progress...");
        developer.writeCode();
    }
}
