package org.firstinspires.ftc.teamcode.commands;

public class SequentialCommandGroup extends CommandGroup {

    protected int index = 0;

    public SequentialCommandGroup(Command... commands) {
        super(commands);
    }

    @Override
    public void update() {
        if (index >= commands.size()) {
            state = State.FINISHED;
            return;
        }

        Command.State currentState = commands.get(index).run();

        if (currentState == Command.State.FINISHED) {
            index++;
        }

        if (index >= commands.size()) {
            state = State.FINISHED;
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted && index < commands.size()) {
            commands.get(index).cancel();
        }
    }
}