package com.javarush.khmelov.lesson16.p04_builder.modern;

public class Client {
    public static void main(String[] args) {
        ModernPizza pizza = new ModernPizza.ModernPizzaBuilder()
                .topping("pepperoni")
                .sauce("tomato")
                .dough("thin")
                .build();
    }
}
