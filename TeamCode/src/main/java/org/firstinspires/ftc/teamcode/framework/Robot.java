package org.firstinspires.ftc.teamcode.framework;

import org.firstinspires.ftc.teamcode.subsystems.ExampleSubsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;

public final class Robot {
    private static volatile Robot instance = null;

    private HardwareMap hardwareMap;

    public ExampleSubsystem exampleSubsystem;

    private Robot () {}

    public void init (HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        exampleSubsystem = new ExampleSubsystem(hardwareMap);
    }

    public void run () {}

    public static Robot getInstance () {
        if (instance == null) {
            synchronized (Scheduler.class) {
                if (instance == null) {
                    instance = new Robot();
                }
            }
        }

        return instance;
    }
}