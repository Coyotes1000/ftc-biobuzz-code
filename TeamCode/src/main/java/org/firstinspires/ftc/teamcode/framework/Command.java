package org.firstinspires.ftc.teamcode.framework;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Command {
    public final Set<Subsystem> requirements = new HashSet<>(4);

    protected int priority = 0;

    public enum State {PENDING, RUNNING, FINISHED}
    protected State state = State.PENDING;

    public Command (Subsystem... subsystems) {
        Collections.addAll(requirements, subsystems);
    }

    protected void start () {}
    protected void update () {}
    protected void end (boolean interrupted) {}

    public State run () {
        switch (state) {
            case PENDING:
                start();
                requirements.forEach(Subsystem::setBusy);
                break;
            case RUNNING:
                update();
                break;
            case FINISHED:
                end(false);
                requirements.forEach(Subsystem::setIdle);
                break;
        }

        return state;
    }

    public State cancel () {
        switch (state) {
            case PENDING:
                break;
            case RUNNING:
                end(true);
                requirements.forEach(Subsystem::setIdle);
                break;
            case FINISHED:
                end(false);
                requirements.forEach(Subsystem::setIdle);
                break;
        }
        
        return state;
    }

    public State getState () {
        return state;
    }

    public int getPriority () {
        return priority;
    }
}