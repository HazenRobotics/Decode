package org.firstinspires.ftc.teamcode.NewBot.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LeLED {
    //TODO: One LED For Distance(First get the camera based distance better working)
    public enum Colors {
        ORANGE(0.3),
        YELLOW(0.35),
        LIME(0.5),
        GREEN(0.50),
        CYAN(0.55),
        BLUE(0.60),
        PURPLE(0.65),
        PINK(0.7),
        WHITE(0.8);
        public final double weight;

        Colors(double weight) {
            this.weight = weight;
        }
    }

    Servo leftLed;
    Servo rightLed;
    String leftName = "leftLed", rightName = "rightLed";

    public LeLED(HardwareMap hw)
    {
        leftLed = hw.get(Servo.class, leftName);
        rightLed = hw.get(Servo.class, rightName);
    }

    public void setColor(Colors color) {
        leftLed.setPosition(color.weight);
        rightLed.setPosition(color.weight);
    }




}
