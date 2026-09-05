package org.firstinspires.ftc.teamcode.framework;

import java.util.Arrays;

import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

public final class Scheduler {

    private static final int MAX_SUBSYSTEMS = 16;
    private static final int MAX_COMMANDS = 32;

    private final Subsystem[] claimedSubsystems = new Subsystem[MAX_SUBSYSTEMS];
    private int claimedSubsystemsCount = 0;

    private final Command[] pendingCommands = new Command[MAX_COMMANDS];
    private int pendingCommandsCount = 0;

    private final Command[] selectedCommands = new Command[MAX_COMMANDS];
    private int selectedCommandsCount = 0;

    private final Command[] rejectedCommands = new Command[MAX_COMMANDS];
    private int rejectedCommandsCount = 0;

    public Scheduler() {}

    public void schedule(Command command) {
        if (command.isFinished()) {
            command.reset();
        }

        insertPendingSorted(command);
    }

    public void run() {
        filterPendingCommands();
        cancelRejectedCommands();
        runSelectedCommands();
    }

    public void clear() {
        cancelSelectedCommands();
        clearArrays();
    }

    private void insertPendingSorted(Command command) {
        int i = pendingCommandsCount - 1;

        while (i >= 0 && compareCommands(command, pendingCommands[i])) {
            pendingCommands[i + 1] = pendingCommands[i--];
        }

        pendingCommands[i + 1] = command;
        pendingCommandsCount++;
    }

    private static boolean compareCommands(Command a, Command b) {
        int priorityCompare = a.getPriority().compareTo(b.getPriority());

        if (priorityCompare != 0) {
            return priorityCompare > 0;
        }

        int stateCompare = a.getState().compareTo(b.getState());

        return stateCompare > 0;
    }

    private void filterPendingCommands() {
        clearClaimedSubsystemsCount();

        clearSelectedCommandsCount();
        clearRejectedCommandsCount();

        for (int i = 0; i < pendingCommandsCount; i++) {
            Command command = pendingCommands[i];

            if (hasSubsystemConflict(command)) {
                rejectedCommands[rejectedCommandsCount++] = command;
                continue;
            }

            selectedCommands[selectedCommandsCount++] = command;

            claimRequirements(command);
        }

        clearPendingCommandsCount();
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
        for (int i = 0; i < claimedSubsystemsCount; i++) {
            if (subsystem == claimedSubsystems[i]) {
                return true;
            }
        }

        return false;
    }

    private void claimRequirements(Command command) {
        for (Subsystem subsystem : command.requirements) {
            claimedSubsystems[claimedSubsystemsCount++] = subsystem;
        }
    }

    private void cancelRejectedCommands() {
        for (int i = 0; i < rejectedCommandsCount; i++) {
            rejectedCommands[i].cancel();
        }
    }

    private void cancelSelectedCommands() {
        for (int i = 0; i < selectedCommandsCount; i++) {
            selectedCommands[i].cancel();
        }
    }

    private void runSelectedCommands() {
        for (int i = 0; i < selectedCommandsCount; i++) {
            Command command = selectedCommands[i];

            command.run();

            if (!command.isFinished()) {
                schedule(command);
            }
        }
    }

    private void clearArrays() {
        Arrays.fill(claimedSubsystems, null);

        Arrays.fill(pendingCommands, null);
        Arrays.fill(selectedCommands, null);
        Arrays.fill(rejectedCommands, null);

        clearArrayCounts();
    }

    private void clearArrayCounts() {
        clearClaimedSubsystemsCount();

        clearPendingCommandsCount();
        clearSelectedCommandsCount();
        clearRejectedCommandsCount();
    }

    private void clearClaimedSubsystemsCount() {
        claimedSubsystemsCount = 0;
    }

    private void clearPendingCommandsCount() {
        pendingCommandsCount = 0;
    }

    private void clearSelectedCommandsCount() {
        selectedCommandsCount = 0;
    }

    private void clearRejectedCommandsCount() {
        rejectedCommandsCount = 0;
    }
}