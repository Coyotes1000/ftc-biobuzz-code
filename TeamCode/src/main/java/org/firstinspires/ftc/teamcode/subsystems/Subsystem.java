package org.firstinspires.ftc.teamcode.subsystems;

public abstract class Subsystem {

    public enum State { IDLE, BUSY }

    protected State state = State.IDLE;

    public Subsystem() {}

    public void setIdle() {
        state = State.IDLE;
    }

    public void setBusy() {
        state = State.BUSY;
    }

    public boolean isIdle() {
        return state == State.IDLE;
    }

    public boolean isBusy() {
        return state == State.BUSY;
    }
}