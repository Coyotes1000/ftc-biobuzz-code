package org.firstinspires.ftc.teamcode.commands;

public class SequentialCommandGroup extends CommandGroup {

    protected int index = 0;

    public SequentialCommandGroup(Priority priority, Command... commands) {
        super(priority, commands);
    }

    @Override
    public void update() {
        State currentState = commands[index].run();

        if (currentState == Command.State.FINISHED) {
            index++;
        }

        if (index >= commands.length) {
            state = State.FINISHED;
        }
    }

    @Override
    public void end() {
        if (state == State.RUNNING) {
            commands[index].cancel();
        }
    }
}