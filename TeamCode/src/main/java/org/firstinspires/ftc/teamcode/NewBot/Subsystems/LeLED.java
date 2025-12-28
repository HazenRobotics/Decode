package org.firstinspires.ftc.teamcode.NewBot.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LeLED {
    public static final double ORANGE_WEIGHT = 0.3;
    public static final double YELLOW_WEIGHT = 0.35;
    public static final double LIME_WEIGHT = 0.5;
    public static final double GREEN_WEIGHT = 0.50;
    public static final double CYAN_WEIGHT = 0.55;
    public static final double BLUE_WEIGHT = 0.60;
    public static final double PURPLE_WEIGHT = 0.65;
    public static final double PINK_WEIGHT = 0.7;

    public static final double WHITE_WEIGHT = 0.8;

    Servo leftLed;
    Servo rightLed;
    String leftName = "leftLed", rightName = "rightLed";

    public LeLED(HardwareMap hw)
    {
        leftLed = hw.get(Servo.class, leftName);
        rightLed = hw.get(Servo.class, rightName);
    }

    public void setColor(double color) {
        leftLed.setPosition(color);
        rightLed.setPosition(color);
    }


}
