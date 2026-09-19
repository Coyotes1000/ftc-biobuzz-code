package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

import com.qualcomm.robotcore.hardware.Gamepad;

public class DriveCommand extends Command {

    private final Drivetrain drivetrain;
    private final Gamepad gamepad;

    public DriveCommand (Drivetrain drivetrain, Gamepad gamepad) {
        super(drivetrain);
        this.priority = Priority.LOW;

        this.drivetrain = drivetrain;
        this.gamepad = gamepad;
    }

    protected void start () {

    }

    protected void update () {
        drivetrain.drive(-gamepad.left_stick_y, gamepad.left_stick_x, gamepad.right_stick_x);
    }

    protected void end () {

    }

}