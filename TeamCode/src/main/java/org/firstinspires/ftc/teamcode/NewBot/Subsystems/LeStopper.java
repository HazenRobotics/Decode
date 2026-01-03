package org.firstinspires.ftc.teamcode.NewBot.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LeStopper {
    //Will look at improving abstraction for this class
    Servo leftStopper, rightStopper;
    String leftStopperName = "leftStopper", rightStopperName = "rightStopper";
    boolean isFull = false;

    public LeStopper(HardwareMap hw)
    {
        leftStopper = hw.get(Servo.class, leftStopperName);
        rightStopper = hw.get(Servo.class, rightStopperName);
        rightStopper.setDirection(Servo.Direction.REVERSE);
    }


    public void setPositon(double pos)
    {
        leftStopper.setPosition(pos);
        rightStopper.setPosition(pos);
    }

    public void toggle()
    {
        isFull = !isFull;
        if(isFull)
        {
            setPositon(0.4);

        }else
        {
            setPositon(0.7);
        }


    }

}
