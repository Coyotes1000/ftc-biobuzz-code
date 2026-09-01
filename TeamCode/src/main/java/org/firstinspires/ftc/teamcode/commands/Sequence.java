package org.firstinspires.ftc.teamcode.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.firstinspires.ftc.teamcode.framework.Command;

public class Sequence extends Command {
    private List<Command> sequence = new ArrayList<>();

    public int index = 0;

    public Sequence(Command... commands) {
        Collections.addAll(sequence, commands);

        for (Command command : sequence) {
            addRequirements(command.requirements);
        }
    }

    @Override
    public void update() {
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

    @Override
    public void end(boolean interrupted) {
        sequence.get(index).end(interrupted);
    }
}
