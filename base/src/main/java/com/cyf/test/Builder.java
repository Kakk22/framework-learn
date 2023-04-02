package com.cyf.test;

import java.util.ArrayList;

/**
 * @author 陈一锋
 * @date 2023/3/2 11:06 下午
 */
public interface Builder {
    void setPizzaType(String pizzaType);
    void setSizeInInch(int sizeInInch);
    void setCrustType(String crustType);
    void setExtraToppings(ArrayList<String> extraToppings);
    void setSauce(String sauce);
    void setCheeseLevel(int cheeseLevel);
}
