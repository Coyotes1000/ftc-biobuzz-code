package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.framework.commands.Command;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class ResetAngleCommand extends Command {

    private final Drivetrain drivetrain;

    public ResetAngleCommand(Drivetrain drivetrain) {
        super(drivetrain);

        this.drivetrain = drivetrain;

        this.priority = Priority.MEDIUM;
    }

    @Override
    public void update() {
        drivetrain.resetAngle();

        state = State.ENDING;
    }
}