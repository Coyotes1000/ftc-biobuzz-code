package org.firstinspires.ftc.teamcode.framework;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public final class Scheduler {

    private static volatile Scheduler instance = null;

    private final Map<Subsystem, Integer> priorityMap = new HashMap<>();

    private final List<Command> selectedCommands = new ArrayList<>();
    private final List<Command> pendingCommands = new ArrayList<>();
    private final List<Command> rejectedCommands = new ArrayList<>();

    private Scheduler () {

    }

    public void schedule (Command command) {
        pendingCommands.add(command);
    }

    public void run () {
        
        pendingCommands.sort((a,b) -> Integer.compare(a.priority, b.priority));

        priorityMap.clear();

        for (Command command : pendingCommands) {
            for (Subsystem requirement : command.requirements) {
                priorityMap.put(requirement, command.priority);
            }
        }

        selectionLoop:
        for (Command command : pendingCommands) {
            for (Subsystem requirement : command.requirements) {
                if (command.priority < priorityMap.get(requirement)) {
                    rejectedCommands.add(command);
                    continue selectionLoop;
                }
            }

            selectedCommands.add(command);
        }

        pendingCommands.clear();

        for (Command command : selectedCommands) {
            switch (command.state) {
                case PENDING:
                    command.start();
                    pendingCommands.add(command);
                    break;
                case RUNNING:
                    command.update();
                    pendingCommands.add(command);
                    break;
                case FINISHED:
                    command.end(false);
                    break;
            }
        }

        for (Command command : rejectedCommands) {
            switch (command.state) {
                case PENDING:
                    break;
                case RUNNING:
                    command.end(true);
                    break;
                case FINISHED:
                    command.end(false);
                    break;
            }
        }

        selectedCommands.clear();
        rejectedCommands.clear();
    }

    public void clear () {
        priorityMap.clear();
        selectedCommands.clear();
        pendingCommands.clear();
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