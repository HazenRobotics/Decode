package org.firstinspires.ftc.teamcode.NewBot.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LeTransfer {
    //Ninja stars,
    //1 motor
    DcMotorEx transfer;
    String name = "transfer";
    boolean isZero = false;
    double pow = 1.0;

    public LeTransfer(HardwareMap hw)
    {
        transfer = hw.get(DcMotorEx.class, name);
        transfer.setDirection(DcMotorSimple.Direction.FORWARD);
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
