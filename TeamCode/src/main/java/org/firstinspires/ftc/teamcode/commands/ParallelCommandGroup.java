package org.firstinspires.ftc.teamcode.commands;

public class ParallelCommandGroup extends CommandGroup {

    public ParallelCommandGroup(Priority priority, Command... commands) {
        super(priority, commands);
    }

    @Override
    public void update() {
        boolean allCommandsFinished = true;

        for (Command command : commands) {
            command.run();

            if (!command.isFinished()) {
                allCommandsFinished = false;
            }
        }

        if (allCommandsFinished) {
            state = State.ENDING;
        }
    }

    @Override
    public void end() {
        if (state == State.RUNNING) {
            for (Command command : commands) {
                command.cancel();
            }
        }
    }

}