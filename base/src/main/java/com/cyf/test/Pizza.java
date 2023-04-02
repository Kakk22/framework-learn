package com.cyf.test;

import java.util.ArrayList;

/**
 * @author 陈一锋
 * @date 2023/3/2 11:48 下午
 */
public class Pizza {

    private final String pizzaType;
    private final int sizeInInch;
    private final String crustType;
    private final ArrayList<String> extraToppings;
    private final String sauce;
    private final int cheeseLevel;
    public Pizza(String pizzaType, int sizeInInch, String crustType, ArrayList<String>
            extraToppings, String sauce, int cheeseLevel) {
        this.pizzaType = pizzaType;
        this.sizeInInch = sizeInInch;
        this.crustType = crustType;
        this.extraToppings = extraToppings;
        this.sauce = sauce;
        this.cheeseLevel = cheeseLevel;
    }
}
