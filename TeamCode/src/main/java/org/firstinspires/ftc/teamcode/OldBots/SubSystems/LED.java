package org.firstinspires.ftc.teamcode.OldBots.SubSystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LED {

    RevBlinkinLedDriver LED;
    Telemetry telemetry;
    public LED(HardwareMap hw) {
        LED = hw.get(RevBlinkinLedDriver.class, "led");
    }

    public LED(HardwareMap hw, String name) {
        LED = hw.get(RevBlinkinLedDriver.class, name);
    }

    public LED(HardwareMap hw, Telemetry t) {
// Error        this(hw, "light");
        telemetry = t;
    }

    public void setColor(RevBlinkinLedDriver.BlinkinPattern color) {
        LED.setPattern(color);
    }
}
