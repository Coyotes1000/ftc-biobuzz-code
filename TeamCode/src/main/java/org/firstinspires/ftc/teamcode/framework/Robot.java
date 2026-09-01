package org.firstinspires.ftc.teamcode.framework;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;

public final class Robot {

    private final Scheduler scheduler = new Scheduler();

    public Robot(HardwareMap hardwareMap) {
    }

    public void updateSubsystems() {
    }

    public void updateCommands() {
        scheduler.run();
    }

    public void clearCommands() {
        scheduler.clear();
    }

    public void updateTelemetry(Telemetry telemetry) {
    }
}
