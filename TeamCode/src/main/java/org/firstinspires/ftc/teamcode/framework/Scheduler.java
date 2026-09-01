package org.firstinspires.ftc.teamcode.framework;

import java.util.List;
import java.util.Set;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public final class Scheduler {
    private static volatile Scheduler instance = null;

    private final Set<Subsystem> claimedSubsystems = new HashSet<>(16);

    private final List<Command> pendingCommands = new ArrayList<>(32);
    private final List<Command> selectedCommands = new ArrayList<>(32);
    private final List<Command> rejectedCommands = new ArrayList<>(32);

    private final Comparator<Command> comparison = Comparator.comparingInt(Command::getPriority).thenComparing(Command::getState).reversed();

    private Scheduler () {}

    public synchronized void schedule (Command command) {
        pendingCommands.add(command);
    }

    private void filterCommands () {
        for (Command command : pendingCommands) {
            if (Collections.disjoint(command.requirements, claimedSubsystems)) {
                selectedCommands.add(command);
                claimedSubsystems.addAll(command.requirements);
            }
            else {
                rejectedCommands.add(command);
            }
        }
    }

    private void runCommands () {
        
    }

    private void cancelCommands () {

    }

    public synchronized void run () {
        pendingCommands.sort(comparison);
        claimedSubsystems.clear();

        filterPendingCommands();

        pendingCommands.clear();

        cancelRejectedCommands();
        runSelectedCommands();

        selectedCommands.clear();
        rejectedCommands.clear();
    }

    public synchronized void clear () {
        claimedSubsystems.clear();

        pendingCommands.clear();

        selectedCommands.clear();
        rejectedCommands.clear();
    }

    public static Scheduler getInstance () {
        if (instance == null) {
            synchronized (Scheduler.class) {
                if (instance == null) {
                    instance = new Scheduler();
                }
            }
        }

        return instance;
    }
}
