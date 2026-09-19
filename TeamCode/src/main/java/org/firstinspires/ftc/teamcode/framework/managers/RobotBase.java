package org.firstinspires.ftc.teamcode.framework.managers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.framework.commands.Command;

public abstract class RobotBase {

    protected final Scheduler scheduler = new Scheduler();

    public RobotBase() {}

    public void updateSubsystems() {}

    public void schedule(Command command) {
        scheduler.schedule(command);
    }

    public void updateCommands() {
        scheduler.run();
    }

    public void clear() {
        scheduler.clear();
    }

    public void updateTelemetry(Telemetry telemetry) {}
}
