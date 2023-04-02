package com.cyf.stragety;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈一锋
 * @date 2023/4/2 11:09 上午
 */
public class Demo {

    interface  Airline {
        BigDecimal cost();
        String type();
    }

    static class Airline1 implements Airline{
        @Override
        public BigDecimal cost() {
            return null;
        }

        @Override
        public String type() {
            return null;
        }
    }
    static class Airline2 implements Airline{
        @Override
        public BigDecimal cost() {
            return null;
        }

        @Override
        public String type() {
            return null;
        }
    }

    public static void main(String[] args) {
        Map<String,Airline> factory = new HashMap<>();
        factory.put("arrilne1",new Airline1());
        factory.put("arrilne2",new Airline2());

        Airline airline = factory.get("arrilne1");
        BigDecimal cost = airline.cost();

    }
}
