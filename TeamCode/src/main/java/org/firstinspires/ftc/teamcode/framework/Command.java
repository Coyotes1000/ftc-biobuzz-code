package org.firstinspires.ftc.teamcode.framework;

import java.util.Set;
import java.util.Collections;
import java.util.HashSet;

public abstract class Command {
    protected Set<Subsystem> requirements = new HashSet<>(4);
    
    protected int priority = 0;

    public enum State {
        PENDING, RUNNING, FINISHED
    }

    protected State state = State.PENDING;

    public Command () {}

    public void addRequirements (Subsystem... subsystems) {
        Collections.addAll(requirements, subsystems);
    }

    public void addRequirements (Set<Subsystem> subsystems) {
        requirements.addAll(subsystems);
    }

    public void start () {
        state = State.RUNNING;
    }

    public void update () {}

    public void end (boolean interrupted) {}

    public State getState () {
        return state;
    }

    public int getPriority () {
        return priority;
    }
}