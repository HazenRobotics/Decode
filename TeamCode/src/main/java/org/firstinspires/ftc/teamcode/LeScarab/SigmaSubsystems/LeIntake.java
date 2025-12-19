package org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LeIntake {
    //Wide intake that funnels
    //Gecko wheels / ninja starts
    //Higher RPM moter
    //1 moter

    private DcMotorEx intake;

    private String name = "intake";

    public LeIntake(HardwareMap hw)
    {
        intake = hw.get(DcMotorEx.class, name);
    }

    public void setPower(double power)
    {
        intake.setPower(power);
    }

    public void feed()
    {
        intake.setPower(0.8);
    }

    public double getPower()
    {
        return intake.getPower();
    }


}
