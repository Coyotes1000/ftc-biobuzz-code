package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.commands.ExampleCommand;
import org.firstinspires.ftc.teamcode.commands.Sequence;
import org.firstinspires.ftc.teamcode.framework.BaseOpMode;
import org.firstinspires.ftc.teamcode.framework.Scheduler;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Example Auto", group = "Examples")
@Disabled
public class ExampleAuto extends BaseOpMode {

    @Override
    public void onStart () {
        Scheduler.getInstance().schedule(new Sequence(
            new ExampleCommand(),
            new ExampleCommand()
        ));
    }

}