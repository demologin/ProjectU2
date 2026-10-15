package com.javarush.zolotarev.logic;

import lombok.Getter;

@Getter
public class Result {
    private final Integer nextStep;
    private final boolean won;
    private final String message;

    private Result(Integer nextStep, boolean won, String message) {
        this.nextStep = nextStep;
        this.won = won;
        this.message = message;
    }

    public static Result next(int step, String message) {
        return new Result(step, false, message);
    }

    public static Result win(String message) {
        return new Result(null, true, message);
    }

    public static Result lose(String message) {
        return new Result(null, false, message);
    }

    public boolean isFinished() {
        return nextStep == null;
    }

}
