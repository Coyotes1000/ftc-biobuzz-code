package org.firstinspires.ftc.teamcode.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

public class Sequence extends Command {
    private final List<Command> sequence = new ArrayList<>();

    protected int index = 0;

    public Sequence(Command... commands) {
        super(collectRequirements(commands));

        Collections.addAll(sequence, commands);
    }

    @Override
    public void update() {
        if (index >= sequence.size()) {
            state = State.FINISHED;
            return;
        }

        Command.State currentState = sequence.get(index).run();

        if (currentState == Command.State.FINISHED) {
            index++;
        }

        if (index >= sequence.size()) {
            state = State.FINISHED;
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted && index < sequence.size()) {
            sequence.get(index).cancel();
        }
    }

    private static Subsystem[] collectRequirements(Command... commands) {
        Set<Subsystem> totalRequirements = new HashSet<>();

        for (Command command : commands) {
            totalRequirements.addAll(command.requirements);
        }

        return totalRequirements.toArray(new Subsystem[0]);
    }
}
