package org.firstinspires.ftc.teamcode.framework;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class BaseOpMode extends LinearOpMode {
    public Robot robot = Robot.getInstance();
    public Scheduler scheduler = Scheduler.getInstance();

    public BaseOpMode() {
    }

    public void onInit() {
    }

    public void initUpdate() {
    }

    public void onStart() {
    }

    public void gameUpdate() {
    }

    public void onEnd() {
    }

    @Override
    public final void runOpMode() {
        robot.init(hardwareMap);

        while (opModeInInit()) {
            robot.run();
            initUpdate();
            scheduler.run();
            telemetry.update();
        }

        Scheduler.getInstance().clear();
        onStart();

        while (opModeIsActive()) {
            robot.run();
            gameUpdate();
            scheduler.run();
            telemetry.update();
        }

        onEnd();
        Scheduler.getInstance().clear();
    }
}
