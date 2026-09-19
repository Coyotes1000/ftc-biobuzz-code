package org.firstinspires.ftc.teamcode.framework.commands;

public abstract class CommandGroup extends Command {

    protected final Command[] commands;

    protected CommandGroup(Priority priority, Command... commands) {
        super(collectRequirements(commands));

        this.commands = commands;

        this.priority = priority;
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