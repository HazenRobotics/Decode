package org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LeTransfer {
    //Ninja stars,
    //1 motor
    DcMotorEx transfer;
    String name = "transfer";
    boolean isZero = true;
    double pow = 0.8;

    public LeTransfer(HardwareMap hw)
    {
        transfer = hw.get(DcMotorEx.class, name);
    }

    public void setPower(double power)
    {
        transfer.setPower(power);
    }

    public double getPower()
    {
        return transfer.getPower();
    }

    public void togglePower()
    {
        isZero = !isZero;
        if(isZero)
        {
            transfer.setPower(pow);
        }else {
            transfer.setPower(0);
        }
    }

    public void reverseMotor()
    {
        pow *= -1;
    }
}
