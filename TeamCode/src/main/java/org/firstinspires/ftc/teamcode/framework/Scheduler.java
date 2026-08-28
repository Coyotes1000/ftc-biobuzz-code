package org.firstinspires.ftc.teamcode.framework;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public final class Scheduler {
    private static volatile Scheduler instance = null;

    private final List<Command> incomingCommands = new ArrayList<>(30);
    private final List<Command> pendingCommands = new ArrayList<>(30);

    private final List<Command> selectedCommands = new ArrayList<>(10);
    private final List<Command> rejectedCommands = new ArrayList<>(10);

    private final Set<Subsystem> activeSubsystems = new HashSet<>(8);

    private final Comparator<Command> comparison = Comparator.comparingInt(Command::getPriority).thenComparing(Command::getState).reversed();

    private Scheduler () {}

    public synchronized void schedule (Command command) {
        incomingCommands.add(command);
    }

    public synchronized void run () {
        pendingCommands.addAll(incomingCommands);
        incomingCommands.clear();

        pendingCommands.sort(comparison);
        activeSubsystems.clear();

        for (Command command : pendingCommands) {
            if (Collections.disjoint(command.requirements, activeSubsystems)) {
                selectedCommands.add(command);
                activeSubsystems.addAll(command.requirements);
            } else {
                rejectedCommands.add(command);
            }
        }

        pendingCommands.clear();
        activeSubsystems.clear();

        for (Command command : rejectedCommands) {
            switch (command.state) {
                case PENDING:
                    break;
                case RUNNING:
                    command.end(true);
                    command.requirements.forEach(Subsystem::setIdle);
                    break;
                case FINISHED:
                    command.end(false);
                    command.requirements.forEach(Subsystem::setIdle);
                    break;
            }
        }

        for (Command command : selectedCommands) {
            switch (command.state) {
                case PENDING:
                    command.start();
                    pendingCommands.add(command);
                    command.requirements.forEach(Subsystem::setBusy);
                    break;
                case RUNNING:
                    command.update();
                    pendingCommands.add(command);
                    break;
                case FINISHED:
                    command.end(false);
                    command.requirements.forEach(Subsystem::setIdle);
                    break;
            }
        }

        selectedCommands.clear();
        rejectedCommands.clear();
    }

    public synchronized void clear () {
        incomingCommands.clear();
        pendingCommands.clear();

        selectedCommands.clear();
        rejectedCommands.clear();

        activeSubsystems.clear();
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
