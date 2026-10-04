package com.javarush.zolotarev.logic;

public class Question {
    private final String text;
    private final String optionA;
    private final String optionB;
    private final String optionC;

    public Question(String text, String optionA, String optionB, String optionC) {
        this.text = text;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
    }

    public String getText() {
        return text;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }
}
