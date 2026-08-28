package org.firstinspires.ftc.teamcode.framework;

import com.qualcomm.robotcore.hardware.HardwareMap;

public final class Robot {
    private static volatile Robot instance = null;

    private HardwareMap hardware;

    private Robot () {}

    public void init (HardwareMap hardwareMap) {
        hardware = hardwareMap;
    }

    public void run () {

    }

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