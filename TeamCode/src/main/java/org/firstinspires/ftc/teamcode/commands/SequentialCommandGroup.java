package org.firstinspires.ftc.teamcode.commands;

public class SequentialCommandGroup extends CommandGroup {

    protected int index;

    public SequentialCommandGroup(Priority priority, Command... commands) {
        super(priority, commands);
    }

    @Override
    public void start() {
        index = 0;
    }

    @Override
    public void update() {
        commands[index].run();

        if (commands[index].isFinished()) {
            index++;
        }

        if (index >= commands.length) {
            state = State.ENDING;
        }
    }

    @Override
    public void end() {
        if (state == State.RUNNING) {
            commands[index].cancel();
        }
    }
}