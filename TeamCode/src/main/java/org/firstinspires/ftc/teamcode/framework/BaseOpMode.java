package org.firstinspires.ftc.teamcode.framework;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class BaseOpMode extends LinearOpMode{

    public BaseOpMode () {}

    public void onInit () {}

    public void initUpdate () {}

    public void onStart () {}

    public void gameUpdate () {}

    public void onEnd () {}

    @Override
    public final void runOpMode () {
        Robot.getInstance().init(hardwareMap);

        while (opModeInInit()) {
            Robot.getInstance().run();

            initUpdate();

            Scheduler.getInstance().run();
            
            telemetry.update();
        }

        Scheduler.getInstance().clear();
        onStart();

        while (opModeIsActive()) {
            Robot.getInstance().run();

            gameUpdate();

            Scheduler.getInstance().run();

            telemetry.update();
        }

        onEnd();
        Scheduler.getInstance().clear();
    }

}