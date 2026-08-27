package org.firstinspires.ftc.teamcode.framework;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class Command {

    public List<Subsystem> requirements = new ArrayList<>();

    public Integer priority = 0;

    public enum State {
        PENDING,
        RUNNING,
        FINISHED
    }

    public State state = State.PENDING;

    public Command () {

    }

    private void setRequirements (Subsystem... subsystems) {
        requirements.clear();
        Collections.addAll(requirements, subsystems);
    }

    public void start () {

    }

    public void update () {

    }

    public void end (Boolean interrupted) {

    }
    
}