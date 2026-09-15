package com.javarush.khmelov.lesson17.p09_state.state;

public class FireState implements TransformerState{

    @Override
    public void action() {
        System.out.println("fire!!!");
    }

}
