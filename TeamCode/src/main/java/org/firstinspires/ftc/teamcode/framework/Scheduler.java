package org.firstinspires.ftc.teamcode.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

public final class Scheduler {

    private static final Comparator<Command> COMPARATOR = Comparator.comparingInt(Command::getPriority)
            .thenComparing(Command::getState).reversed();

    private final Set<Subsystem> claimedSubsystems = new HashSet<>(16);

    private final List<Command> pendingCommands = new ArrayList<>(32);
    private final List<Command> selectedCommands = new ArrayList<>(32);
    private final List<Command> rejectedCommands = new ArrayList<>(32);

    public Scheduler() {
    }

    public synchronized void schedule(Command command) {
        pendingCommands.add(command);
    }

    public synchronized void run() {
        pendingCommands.sort(COMPARATOR);
        claimedSubsystems.clear();

        filterPendingCommands();

        pendingCommands.clear();

        cancelRejectedCommands();
        runSelectedCommands();

        selectedCommands.clear();
        rejectedCommands.clear();
    }

    public synchronized void clear() {
        claimedSubsystems.clear();

        pendingCommands.clear();

        selectedCommands.clear();
        rejectedCommands.clear();
    }

    private boolean hasSubsystemConflict(Command command) {
        return !Collections.disjoint(command.requirements, claimedSubsystems);
    }

    private void filterPendingCommands() {
        for (Command command : pendingCommands) {
            if (hasSubsystemConflict(command)) {
                rejectedCommands.add(command);
                continue;
            }

            selectedCommands.add(command);
            claimedSubsystems.addAll(command.requirements);
        }
    }

    private void cancelRejectedCommands() {
        for (Command command : rejectedCommands) {
            command.cancel();
        }
    }

    private void runSelectedCommands() {
        for (Command command : selectedCommands) {
            Command.State currentState = command.run();

            if (currentState != Command.State.FINISHED) {
                schedule(command);
            }
        }
    }
}
