package org.firstinspires.ftc.teamcode.SubSystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LED {
    RevBlinkinLedDriver led;
    private final String name = "led";

    RevBlinkinLedDriver.BlinkinPattern[] lights = {
            RevBlinkinLedDriver.BlinkinPattern.WHITE,
            RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN,
            RevBlinkinLedDriver.BlinkinPattern.RED,
    };

    public LED(HardwareMap hw)
    {
        led = hw.get(RevBlinkinLedDriver.class, name);
    }

    public void reset()
    {
        led.setPattern(lights[0]);
    }

//    public void rangeToggle()
//    {
//        //If Arraylist 1, switch to 2, and vice versa
//        //Maybe use enums
//        led.setPattern();
//    }
}
