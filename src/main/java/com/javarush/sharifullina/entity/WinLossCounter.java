package com.javarush.sharifullina.entity;

public record WinLossCounter(int wins, int losses) {
    public WinLossCounter addWin(){
        return new WinLossCounter(wins+1, losses);
    }
    public WinLossCounter addLoss(){
        return new WinLossCounter(wins, losses+1);
    }
}
