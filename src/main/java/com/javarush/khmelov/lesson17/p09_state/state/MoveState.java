package com.javarush.khmelov.lesson17.p09_state.state;

public class MoveState implements TransformerState {

    @Override
    public void action() {
        System.out.println("move!!!");
    }
}
