package org.firstinspires.ftc.teamcode.NewBot.Subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.NewBot.Utils.ColorSensor;

public class LeColorSensor {
    public enum Color
    {
        Green,
        Purple,
        None
    }
    DigitalChannel pin0, pin1;
    public LeColorSensor(HardwareMap hw)
    {
        this(hw, "color0", "color1");
    }
    public LeColorSensor(HardwareMap hw, String pin0Name, String pin1Name)
    {
        this.pin0 = hw.digitalChannel.get(pin0Name);
        this.pin1 = hw.digitalChannel.get(pin1Name);
    }

    public ColorSensor.Color getColor(){
        boolean col0 = this.pin0.getState();
        boolean col1 = this.pin1.getState();
// pin0 = purple
// pin1 = green
        if (col0)
        {
            return ColorSensor.Color.Purple;
        }
        else if(col1)
        {
            return ColorSensor.Color.Green;
        }
        return ColorSensor.Color.None;
    }

    public DigitalChannel getPin0()
    {
        return pin0;
    }
    public DigitalChannel getPin1()
    {
        return pin1;
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
