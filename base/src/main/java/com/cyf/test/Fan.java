package com.cyf.test;

/**
 * @author 陈一锋
 * @date 2023/3/3 12:31 上午
 */
public class Fan {

    private State offState;
    private State lowState;
    private State highState;
    private State currentState;
    public Fan() {
        offState = new OffState(this);
        lowState = new LowState(this);
        highState = new HighState(this);
        currentState = offState;
    }
    public void setState(State state) {
        currentState = state;
    }
    public State getOffState() {
        return offState;
    }
    public State getLowState() {
        return lowState;
    }
    public State getHighState() {
        return highState;
    }

    public State getCurrentState() {
        return currentState;
    }
}
