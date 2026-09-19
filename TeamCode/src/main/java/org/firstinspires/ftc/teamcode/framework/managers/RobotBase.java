package org.firstinspires.ftc.teamcode.framework.managers;

public abstract class RobotBase {

    private final Scheduler scheduler = new Scheduler();

    private final Drivetrain drivetrain;
    private final Gamepad gamepad;

    public RobotBase(HardwareMap hardwareMap, Gamepad gamepad) {
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

        drivetrain.setMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.gamepad = gamepad;
    }

    public void updateSubsystems() {
        if (drivetrain.isIdle()) {
            scheduler.schedule(new DriveCommand(drivetrain, gamepad));
        }
    }

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
