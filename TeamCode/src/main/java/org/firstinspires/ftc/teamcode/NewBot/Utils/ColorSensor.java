package org.firstinspires.ftc.teamcode.NewBot.Utils;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


public class ColorSensor
{
    private final ElapsedTime detectionTimer = new ElapsedTime();
    private static final double DELAY_IN_SECONDS = 0.05;
    public enum Color
    {
        RED,
        BLUE
        ,
        None
    }
    DigitalChannel pin0, pin1, pin2, pin3;
    public ColorSensor(HardwareMap hw)
    {
        this(hw, "color0", "color1", "Color", "color2", "color3", "Color2");
    }

    public ColorSensor(HardwareMap hw, String pin0Name, String pin1Name, String crfName, String pin2Name, String pin3Name, String crf2Name)
    {
        this.pin0 = hw.digitalChannel.get(pin0Name);
        this.pin1 = hw.digitalChannel.get(pin1Name);
        this.pin2 = hw.digitalChannel.get(pin2Name);
        this.pin3 = hw.digitalChannel.get(pin3Name);
    }


    //Issue with this method
    public Color getColor()
    {
        boolean col0 = this.pin0.getState();
        boolean col1 = this.pin1.getState();
// pin0 = purple
// pin1 = green

        if (detectionTimer.seconds() < DELAY_IN_SECONDS)
        {
            return Color.None;
        }
        if (col0)
        {
            detectionTimer.reset();
            return Color.RED;
        }
        else if(col1)
        {
            detectionTimer.reset();
            return Color.BLUE;
        }
        return Color.None;
    }

    public Color getSecondaryColor()
    {
        boolean col0 = this.pin2.getState();
        boolean col1 = this.pin3.getState();
// pin0 = purple
// pin1 = green

        if (detectionTimer.seconds() < DELAY_IN_SECONDS)
        {
            return Color.None;
        }
        if (col1)
        {
            detectionTimer.reset();
            return Color.BLUE;
        }
        else if(col0)
        {
            detectionTimer.reset();
            return Color.RED;
        }
        return Color.None;
    }





    public DigitalChannel getPin0()
    {
        return pin0;
    }
    public DigitalChannel getPin1()
    {
        return pin1;
    }
    public DigitalChannel getPin2()
    {
        return pin2;
    }
    public DigitalChannel getPin3()
    {
        return pin3;
    }

    public String toString(){
        return String.format("Pin 0: %b\n"+
                        "Pin 1: %b\n" +
                        "Color: %s\n",
                pin0.getState(),
                pin1.getState(),
                getColor());
    }


}
