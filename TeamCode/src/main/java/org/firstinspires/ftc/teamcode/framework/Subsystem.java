package org.firstinspires.ftc.teamcode.framework;

public abstract class Subsystem {
    public enum State {
        IDLE, BUSY
    }

    protected State state = State.IDLE;

    private Runnable onIdle;
    private Runnable onBusy;

    public Subsystem() {
    }

    public void setOnIdle(Runnable callback) {
        onIdle = callback;
    }

    public void setOnBusy(Runnable callback) {
        onBusy = callback;
    }

    public void setIdle() {
        if (state != State.IDLE) {
            state = State.IDLE;

            if (onIdle != null) {
                onIdle.run();
            }
        }
    }

    public void setBusy() {
        if (state != State.BUSY) {
            state = State.BUSY;

            if (onBusy != null) {
                onBusy.run();
            }
        }
    }

    public State getState() {
        return state;
    }
}
