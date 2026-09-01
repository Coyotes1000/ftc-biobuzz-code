package org.firstinspires.ftc.teamcode.framework;

import java.util.Set;
import java.util.HashSet;

public abstract class Command {
    public final Set<Subsystem> requirements = new HashSet<>(4);

    public enum State {PENDING, RUNNING, FINISHED}
    protected State state = State.PENDING;

    protected int priority = 0;

    public Command (Subsystem... subsystems) {
        for (Subsystem subsystem : subsystems) {
            requirements.add(subsystem);
        }
    }

    protected void start () {}
    protected void update () {}
    protected void end (boolean interrupted) {}

    public State run () {
        switch (state) {
            case PENDING:
                start();
                requirements.forEach(Subsystem::setBusy);
            case RUNNING:
                update();
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