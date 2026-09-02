package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

public abstract class Command {

    public enum State { PENDING, RUNNING, FINISHED }

    public enum Priority { LOW, MEDIUM, HIGH }

    public final Subsystem[] requirements;

    protected State state = State.PENDING;

    protected Priority priority = Priority.MEDIUM;

    public Command(Subsystem... subsystems) {
        requirements = subsystems;
    }

    public State run() {
        if (state == State.FINISHED) {
            return state;
        }

        if (state == State.PENDING) {
            start();
            setRequirementsBusy();
            state = State.RUNNING;
        }

        if (state == State.RUNNING) {
            update();
        }

        if (state == State.FINISHED) {
            end();
            setRequirementsIdle();
        }

        return state;
    }

    public State cancel() {
        if (state == State.FINISHED) {
            return state;
        }

        if (state == State.RUNNING) {
            end();
            setRequirementsIdle();
            state = State.FINISHED;
        }

        if (state == State.PENDING) {
            end();
            setRequirementsIdle();
            state = State.FINISHED;
        }

        return state;
    }

    public State getState() {
        return state;
    }

    public Priority getPriority() {
        return priority;
    }

    protected void start() {}

    protected void update() {}

    protected void end() {}

    private void setRequirementsBusy() {
        for (Subsystem requirement : requirements) {
            requirement.setBusy();
        }
    }

    private void setRequirementsIdle() {
        for (Subsystem requirement : requirements) {
            requirement.setIdle();
        }
    }
}