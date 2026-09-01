package org.firstinspires.ftc.teamcode.framework;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class BaseOpMode extends LinearOpMode {

    protected Robot robot;

    public BaseOpMode() {
    }

    @Override
    public final void runOpMode() {
        robot = new Robot(hardwareMap);
        onInit();

        while (opModeInInit()) {
            robot.updateSubsystems();
            initUpdate();
            robot.updateCommands();
            robot.updateTelemetry(telemetry);
        }

        robot.clearCommands();
        onStart();

        while (opModeIsActive()) {
            robot.updateSubsystems();
            mainUpdate();
            robot.updateCommands();
            robot.updateTelemetry(telemetry);
        }

        onEnd();
    }

    protected void onInit() {
    }

    protected void initUpdate() {
    }

    protected void onStart() {
    }

    protected void mainUpdate() {
    }

    protected void onEnd() {
    }
}
