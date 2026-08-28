package org.firstinspires.ftc.teamcode.framework;

import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class Subsystem {
    public final HardwareMap hardwareMap;
    
    public Subsystem (HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void update () {}
}