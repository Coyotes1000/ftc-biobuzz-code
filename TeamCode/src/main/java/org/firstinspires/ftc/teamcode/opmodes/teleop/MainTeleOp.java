package org.firstinspires.ftc.teamcode.opmodes.teleop;

import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.framework.opmodes.BaseOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Main", group = "TeleOp")
// @Disabled
public class MainTeleOp extends BaseOpMode {

    public MainTeleOp() {}

    @Override
    protected void onInit() {}

    @Override
    protected void initUpdate() {}

    @Override
    protected void onStart() {}

    @Override
    protected void mainUpdate() {
        if (robot.drivetrain.isIdle()) {
            robot.schedule(new DriveCommand(robot.drivetrain, gamepad1));
        }
    }

    @Override
    protected void onEnd() {}

}