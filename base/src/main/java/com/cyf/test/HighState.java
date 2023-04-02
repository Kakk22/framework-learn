package com.cyf.test;

/**
 * @author 陈一锋
 * @date 2023/3/3 12:31 上午
 */
public class HighState implements State{

    private Fan fan;

    public HighState(Fan fan) {
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

    }

    @Override
    public void decreaseSpeed() {
        if (fan.getCurrentState() == this){
            fan.setState(fan.getLowState());
            System.out.println("state update low state");
        }
    }
}
