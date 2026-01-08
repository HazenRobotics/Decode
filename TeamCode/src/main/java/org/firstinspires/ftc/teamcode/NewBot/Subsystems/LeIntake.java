package org.firstinspires.ftc.teamcode.NewBot.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LeIntake {
    //Wide intake that funnels
    //Gecko wheels / ninja starts
    //Higher RPM moter
    //1 moter

    private DcMotorEx intake;

    private String name = "intake";
    private boolean isFeed = true;

    public LeIntake(HardwareMap hw)
    {
        intake = hw.get(DcMotorEx.class, name);
    }

    private void setPower(double power)
    {
        intake.setPower(power);
    }

    public void feed()
    {
        intake.setPower(0.8);
    }

    public void stop()
    {
        intake.setPower(0);
    }

    public void toggle()
    {
        if(isFeed)
        {
           stop();
        }else {
            feed();
        }
        isFeed = !isFeed;
    }

    public double getPower()
    {
        return intake.getPower();
    }


}
