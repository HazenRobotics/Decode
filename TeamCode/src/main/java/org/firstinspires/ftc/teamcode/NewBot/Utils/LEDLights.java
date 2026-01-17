package org.firstinspires.ftc.teamcode.NewBot.Utils;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LEDLights {



    public static final double RED_WEIGHT = 0.25;
    public static final double ORANGE_WEIGHT = 0.3;
    public static final double YELLOW_WEIGHT = 0.35;
    public static final double LIME_WEIGHT = 0.5;
    public static final double GREEN_WEIGHT = 0.50;
    public static final double CYAN_WEIGHT = 0.55;
    public static final double BLUE_WEIGHT = 0.60;
    public static final double PURPLE_WEIGHT = 0.65;
    public static final double PINK_WEIGHT = 0.7;

    public static final double WHITE_WEIGHT = 0.8;


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