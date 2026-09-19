package org.firstinspires.ftc.teamcode.framework;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.Command;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public final class Robot {

    private final Scheduler scheduler = new Scheduler();

    private final Drivetrain drivetrain;

    public Robot(HardwareMap hardwareMap) {
        DcMotor frontLeftDrive = hardwareMap.get(DcMotor.class, "Front_Left_Drive");
        DcMotor frontRightDrive = hardwareMap.get(DcMotor.class, "Front_Left_Drive");
        DcMotor backLeftDrive = hardwareMap.get(DcMotor.class, "Front_Left_Drive");
        DcMotor backRightDrive = hardwareMap.get(DcMotor.class, "Front_Left_Drive");

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);

        IMU imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        imu.initialize(new IMU.Parameters(orientationOnRobot));

        drivetrain = new Drivetrain(frontLeftDrive, frontRightDrive, backLeftDrive, backRightDrive, imu);
    }

    public void updateSubsystems() {}

    public void scheduleCommand(Command command) {
        scheduler.schedule(command);
    }

    public void updateCommands() {
        scheduler.run();
    }

    public void clearCommands() {
        scheduler.clear();
    }

    public void updateTelemetry(Telemetry telemetry) {}
}
