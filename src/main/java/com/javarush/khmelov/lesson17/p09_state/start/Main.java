package com.javarush.khmelov.lesson17.p09_state.start;


import com.javarush.khmelov.lesson17.p09_state.context.TransformerContext;
import com.javarush.khmelov.lesson17.p09_state.state.FireState;
import com.javarush.khmelov.lesson17.p09_state.state.MoveState;
import com.javarush.khmelov.lesson17.p09_state.state.TransformerState;

public class Main {

    public static void main(String[] args) {

        TransformerContext context = new TransformerContext();
        
        TransformerState stateMove = new MoveState();
        TransformerState stateFire = new FireState();

        context.setState(stateFire);
        context.action();

        context.setState(stateMove);
        context.action();
        
    }
}
