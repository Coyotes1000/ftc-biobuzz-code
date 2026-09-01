package org.firstinspires.ftc.teamcode.commands;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

public abstract class Command {

    public enum State {
        PENDING, RUNNING, FINISHED
    }

    public final Set<Subsystem> requirements = new HashSet<>(4);

    protected State state = State.PENDING;
    protected int priority = 0;

    public Command(Subsystem... subsystems) {
        Collections.addAll(requirements, subsystems);
    }

    public State run() {
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

    public State cancel() {
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

    public State getState() {
        return state;
    }

    public int getPriority() {
        return priority;
    }

    protected void start() {
    }

    protected void update() {
    }

    protected void end(boolean interrupted) {
    }
}
