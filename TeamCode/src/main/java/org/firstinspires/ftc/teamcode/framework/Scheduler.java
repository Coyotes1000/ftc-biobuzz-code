package org.firstinspires.ftc.teamcode.framework;

import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

public final class Scheduler {

    private static final int MAX_SUBSYSTEMS = 16;
    private static final int MAX_COMMANDS = 32;

    private final Subsystem[] claimedSubsystems = new Subsystem[MAX_SUBSYSTEMS];
    private int claimedSize = 0;

    private final Command[] pendingCommands = new Command[MAX_COMMANDS];
    private int pendingSize = 0;

    private final Command[] selectedCommands = new Command[MAX_COMMANDS];
    private int selectedSize = 0;

    private final Command[] rejectedCommands = new Command[MAX_COMMANDS];
    private int rejectedSize = 0;

    public Scheduler() {}

    public void schedule(Command command) {
        if (command == null || pendingSize >= pendingCommands.length) {
            return;
        }

        if (command.isFinished()) {
            command.reset();
        }

        int i = pendingSize - 1;

        while (i >= 0 && compareCommands(command, pendingCommands[i]) < 0) {
            pendingCommands[i + 1] = pendingCommands[i];
            i--;
        }

        pendingCommands[i + 1] = command;
        pendingSize++;
    }

    public void run() {
        selectedSize = 0;
        rejectedSize = 0;
        claimedSize = 0;

        filterPendingCommands();

        pendingSize = 0;

        cancelRejectedCommands();
        runSelectedCommands();
    }

    public void clear() {
        for (int i = 0; i < selectedSize; i++) {
            selectedCommands[i].cancel();
        }

        for (int i = 0; i < MAX_COMMANDS; i++) {
            pendingCommands[i] = null;
            selectedCommands[i] = null;
            rejectedCommands[i] = null;
        }

        for (int i = 0; i < MAX_SUBSYSTEMS; i++) {
            claimedSubsystems[i] = null;
        }

        pendingSize = 0;
        selectedSize = 0;
        rejectedSize = 0;
        claimedSize = 0;
    }

    private void filterPendingCommands() {
        for (int i = 0; i < pendingSize; i++) {
            Command command = pendingCommands[i];

            if (hasSubsystemConflict(command)) {
                rejectedCommands[rejectedSize++] = command;
                continue;
            }

            selectedCommands[selectedSize++] = command;

            for (Subsystem subsystem : command.requirements) {
                claimedSubsystems[claimedSize++] = subsystem;
            }
        }
    }

    private boolean hasSubsystemConflict(Command command) {
        for (Subsystem subsystem : command.requirements) {
            if (isSubsystemClaimed(subsystem)) {
                return true;
            }
        }

        return false;
    }

    private boolean isSubsystemClaimed(Subsystem subsystem) {
        for (int i = 0; i < claimedSize; i++) {
            if (subsystem == claimedSubsystems[i]) {
                return true;
            }
        }

        return false;
    }

    private void cancelRejectedCommands() {
        for (int i = 0; i < rejectedSize; i++) {
            rejectedCommands[i].cancel();
        }
    }

    private void runSelectedCommands() {
        for (int i = 0; i < selectedSize; i++) {
            Command command = selectedCommands[i];

            command.run();

            if (!command.isFinished()) {
                schedule(command);
            }
        }
    }

    private static int compareCommands(Command a, Command b) {
        int priorityCompare = b.getPriority().compareTo(a.getPriority());

        if (priorityCompare != 0) {
            return priorityCompare;
        }

        int stateCompare = b.getState().compareTo(a.getState());

        return stateCompare;
    }
}