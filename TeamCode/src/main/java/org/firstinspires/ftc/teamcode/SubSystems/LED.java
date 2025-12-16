package org.firstinspires.ftc.teamcode.SubSystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LED {
    public static final double GREEN_WEIGHT = 0.500;
    public static final double PURPLE_WEIGHT = 0.722;

    Servo LED;
    Telemetry telemetry;
    public LED(HardwareMap hw) {
        LED = hw.get(Servo.class, "led");
    }

    public LED(HardwareMap hw, String name) {
        LED = hw.get(Servo.class, name);
    }

    public LED(HardwareMap hw, Telemetry t) {
// Error        this(hw, "light");
        telemetry = t;
    }

    public void setColor(double color) {
        LED.setPosition(color);
    }
}
