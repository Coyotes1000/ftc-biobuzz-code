package org.firstinspires.ftc.teamcode.opmodes.auto;

import org.firstinspires.ftc.teamcode.commands.ResetAngleCommand;
import org.firstinspires.ftc.teamcode.framework.commands.Command;
import org.firstinspires.ftc.teamcode.framework.commands.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.framework.opmodes.BaseOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Main", group = "TeleOp")
// @Disabled
public class MainAuto extends BaseOpMode {

    public MainAuto() {}

    @Override
    protected void onInit() {
        robot.schedule(new ResetAngleCommand(robot.drivetrain));
    }

    @Override
    protected void initUpdate() {}

    @Override
    protected void onStart() {
        robot.schedule(new SequentialCommandGroup(
            Command.Priority.HIGH
        ));
    }

    @Override
    protected void mainUpdate() {}

    @Override
    protected void onEnd() {}

}