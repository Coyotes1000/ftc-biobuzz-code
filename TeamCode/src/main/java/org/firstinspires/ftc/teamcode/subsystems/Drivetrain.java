package org.firstinspires.ftc.teamcode.subsystems;

public class Drivetrain extends Subsystem {

    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;

    private final DcMotor[] motors;

    private final IMU imu;

    public Drivetrain(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight, IMU imu) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;

        this.motors = new DcMotor[] { this.frontLeft, this.frontRight, this.backLeft, this.backRight };

        this.imu = imu;
    }

    public void setMotorModes(DcMotor.RunMode runMode) {
        for (int i = 0; i < motors.length; i++) {
            motors[i].setMode(runMode);
        }
    }

    public void resetAngle() {
        imu.resetYaw();
    }

    public void drive(float forward, float right, float rotate) {
        double theta = Math.atan2(forward, right);
        double r = Math.hypot(right, forward);

        theta = AngleUnit.normalizeRadians(theta - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r * Math.sin(theta);
        double newRight = r * Math.cos(theta);

        double frontLeftPower = newForward + newRight + rotate;
        double frontRightPower = newForward - newRight - rotate;
        double backLeftPower = newForward + newRight - rotate;
        double backRightPower = newForward - newRight + rotate;

        double maxPower = 1.0;
        double maxSpeed = 0.5;

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        frontLeft.setPower(maxSpeed * (frontLeftPower / maxPower));
        frontRight.setPower(maxSpeed * (frontRightPower / maxPower));
        backLeft.setPower(maxSpeed * (backLeftPower / maxPower));
        backRight.setPower(maxSpeed * (backRightPower / maxPower));
    }

    public void stopMotors() {
        for (int i = 0; i < motors.length; i++) {
            motors[i].setPower(0);
        }
    }
}