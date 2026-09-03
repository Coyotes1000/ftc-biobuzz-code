package org.firstinspires.ftc.teamcode.commands;

public class RaceCommandGroup extends CommandGroup {

    public RaceCommandGroup(Priority priority, Command... commands) {
        super(priority, commands);
    }

    @Override
    public void update() {
        boolean anyCommandsFinished = false;

        for (Command command : commands) {
            command.run();

            if (command.isFinished()) {
                anyCommandsFinished = true;
            }
        }

        if (anyCommandsFinished) {
            state = State.ENDING;
        }
    }

    @Override
    public void end() {
        for (Command command : commands) {
            command.cancel();
        }
    }
}