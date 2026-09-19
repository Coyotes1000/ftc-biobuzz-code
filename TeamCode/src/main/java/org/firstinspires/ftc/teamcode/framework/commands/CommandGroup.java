package org.firstinspires.ftc.teamcode.framework.commands;

import java.util.Arrays;

import org.firstinspires.ftc.teamcode.framework.subsystems.Subsystem;

public abstract class CommandGroup extends Command {

    private static final int MAX_SUBSYSTEMS = 16;

    protected final Command[] commands;

    protected CommandGroup(Priority priority, Command... commands) {
        super(collectRequirements(commands));

        this.commands = commands;

        this.priority = priority;
    }

    private static Subsystem[] collectRequirements(Command... commands) {
        Subsystem[] totalRequirements = new Subsystem[MAX_SUBSYSTEMS];

        int totalRequirementsCount = 0;

        for (Command command : commands) {
            for (Subsystem requirement : command.getRequirements()) {
                boolean uniqueRequirement = true;

                for (int i = 0; i < totalRequirementsCount; i++) {
                    if (totalRequirements[i] == requirement) {
                        uniqueRequirement = false;
                        break;
                    }
                }

                if (uniqueRequirement) {
                    totalRequirements[totalRequirementsCount++] = requirement;
                }
            }
        }

        return Arrays.copyOf(totalRequirements, totalRequirementsCount);
    }
}