package com.cyf.test;

import java.util.ArrayList;

/**
 * @author 陈一锋
 * @date 2023/3/2 11:05 下午
 */
public class PizzaBuilder implements Builder{

    private String pizzaType;
    private int sizeInInch;
    private String crustType;
    private ArrayList<String> extraToppings;
    private String sauce;
    private int cheeseLevel;

    @Override
    public void setPizzaType(String pizzaType) {

    }

    @Override
    public void setSizeInInch(int sizeInInch) {

    }

    @Override
    public void setCrustType(String crustType) {

    }

    @Override
    public void setExtraToppings(ArrayList<String> extraToppings) {

    }

    @Override
    public void setSauce(String sauce) {

    }

    @Override
    public void setCheeseLevel(int cheeseLevel) {

    }

    public Pizza getResult() {
        return new Pizza(pizzaType, sizeInInch, crustType, extraToppings, sauce, cheeseLevel);
    }
}
