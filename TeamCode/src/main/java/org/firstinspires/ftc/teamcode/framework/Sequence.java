package org.firstinspires.ftc.teamcode.framework;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public class Sequence extends Command{

    private List<Command> sequence = new ArrayList<>();

    private Integer index = 0;

    public Sequence (Command... commands) {
        Collections.addAll(sequence, commands);

        Set<Subsystem> allRequirements = new HashSet<>();

        for (Command command : sequence) {
            allRequirements.addAll(command.requirements);
        }

        setRequirements(new ArrayList<>(allRequirements));
    }

    @Override
    public void update () {

        switch (sequence.get(index).state) {
            case PENDING:
                sequence.get(index).start();
                break;
            case RUNNING:
                sequence.get(index).update();
                break;
            case FINISHED:
                sequence.get(index).end(false);
                index++;
                break;
        }

        if (index >= sequence.size()) {
            state = State.FINISHED;
        }

    }

}