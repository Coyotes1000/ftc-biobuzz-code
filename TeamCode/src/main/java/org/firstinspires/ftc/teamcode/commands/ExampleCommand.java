package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.framework.Command;

public class ExampleCommand extends Command {

    public ExampleCommand () {
        // Set command priority and subsystem requirements.
        priority = 1;
        addRequirements();
    }

    @Override
    public void start () {
        // Initialise motors, sensors and other hardware.
        state = State.RUNNING;
    }

    @Override
    public void update () {
        // Movement and other code that runs each loop.
        state = State.FINISHED;
    }

    @Override
    public void end (boolean interrupted) {
        // Telemetry and other code that runs when done.
    }

    // Other command specific methods.

}