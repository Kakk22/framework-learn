package com.cyf.test;

/**
 * @author 陈一锋
 * @date 2023/3/3 12:31 上午
 */
public class LowState implements State{
    private Fan fan;

    public LowState(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void turnOn() {

    }

    @Override
    public void turnOff() {
        if (fan.getCurrentState() != fan.getOffState()) {
            fan.setState(fan.getOffState());
            System.out.println("state update off state");
        }
    }

    @Override
    public void increaseSpeed() {
        if (fan.getCurrentState() == this){
            fan.setState(fan.getHighState());
            System.out.println("state update high state");
        }
    }

    @Override
    public void decreaseSpeed() {

    }
}
