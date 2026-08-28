package org.firstinspires.ftc.teamcode.framework;

import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class Subsystem {
    public final HardwareMap hardwareMap;

    public enum State {
        IDLE, BUSY
    }

    public State state = State.IDLE;
    
    public Subsystem (HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void update () {}

    public void setIdle () {
        state = State.IDLE;
    }

    public void setBusy () {
        state = State.BUSY;
    }

    public boolean isBusy () {
        return state == State.BUSY;
    }
}