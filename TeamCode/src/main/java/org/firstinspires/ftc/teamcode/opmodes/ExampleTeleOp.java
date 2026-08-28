package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.commands.ExampleCommand;
import org.firstinspires.ftc.teamcode.framework.BaseOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Example TeleOp", group = "Examples")
@Disabled
public class ExampleTeleOp extends BaseOpMode {

    public ExampleTeleOp () {}

    @Override
    public void gameUpdate () {
        if (!robot.exampleSubsystem.isBusy()){
            scheduler.schedule(new ExampleCommand());
        }
    }

}