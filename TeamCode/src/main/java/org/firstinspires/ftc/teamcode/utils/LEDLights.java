package org.firstinspires.ftc.teamcode.utils;

import android.graphics.Color;
import android.os.Build;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.Map;

public class LEDLights {
    public static final double GREEN_WEIGHT = 0.500;
    public static final double PURPLE_WEIGHT = 0.722;

    public static final double FAR_WEIGHT = 0.222;

    Servo LED;
    Telemetry telemetry;



    public LEDLights(HardwareMap hw, String name) {
        LED = hw.get(Servo.class, name);
    }

    public LEDLights(HardwareMap hw, Telemetry t) {
// Error        this(hw, "light");
        telemetry = t;
    }



    public void setColor(double color) {
        LED.setPosition(color);
    }
}