package org.firstinspires.ftc.teamcode.commands;

import java.util.HashSet;
import java.util.Set;

import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

public abstract class CommandGroup extends Command {

    protected final Command[] commands;

    protected CommandGroup(Priority priority, Command... commands) {
        super(collectRequirements(commands));

        this.commands = commands;

        this.priority = priority;
    }

    @Override
    public void reset() {
        super.reset();

        for (Command command : commands) {
            command.reset();
        }
    }

    private static Subsystem[] collectRequirements(Command... commands) {
        Set<Subsystem> totalRequirements = new HashSet<>();

        for (Command command : commands) {
            for (Subsystem requirement : command.getRequirements()) {
                totalRequirements.add(requirement);
            }
        }

        return totalRequirements.toArray(new Subsystem[0]);
    }
}