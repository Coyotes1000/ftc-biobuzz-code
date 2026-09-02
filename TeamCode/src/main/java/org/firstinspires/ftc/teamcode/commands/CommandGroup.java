package org.firstinspires.ftc.teamcode.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

public abstract class CommandGroup extends Command {

    protected final List<Command> commands = new ArrayList<>();

    protected CommandGroup(Command... commands) {
        super(collectRequirements(commands));

        Collections.addAll(this.commands, commands);
    }

    private static Subsystem[] collectRequirements(Command... commands) {
        Set<Subsystem> totalRequirements = new HashSet<>();

        for (Command command : commands) {
            totalRequirements.addAll(command.requirements);
        }

        return totalRequirements.toArray(new Subsystem[0]);
    }

}