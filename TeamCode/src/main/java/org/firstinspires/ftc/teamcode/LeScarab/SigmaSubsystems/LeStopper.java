package org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LeStopper {
    Servo stopper;
    String name = "stopper";
    boolean isFull = true;

    public LeStopper(HardwareMap hw)
    {
        stopper = hw.get(Servo.class, name);
    }


    public void setPositon(double pos)
    {
        stopper.setPosition(pos);
    }

    public void toggle()
    {
        isFull = !isFull;
        if(isFull)
        {
            setPositon(0);

        }else {
            setPositon(0.7);
        }


    }

}
