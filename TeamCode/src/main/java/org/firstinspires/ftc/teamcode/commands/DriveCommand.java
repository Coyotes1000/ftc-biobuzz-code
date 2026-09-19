package org.firstinspires.ftc.teamcode.commands;

public class DriveCommand extends Command {

    private final Drivetrain drivetrain;
    private final Gamepad gamepad;

    public DriveCommand(Drivetrain drivetrain, Gamepad gamepad) {
        super(drivetrain);

        this.drivetrain = drivetrain;
        this.gamepad = gamepad;

        this.priority = Priority.LOW;
    }

    protected void start() {
        drivetrain.setMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    protected void update() {
        drivetrain.drive(-gamepad.left_stick_y, gamepad.left_stick_x, gamepad.right_stick_x);
    }

    protected void end() {
        drivetrain.stopMotors();
    }

}