package org.firstinspires.ftc.teamcode.NewBot.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LeTransfer {
    //Ninja stars,
    //1 motor
    DcMotorEx transfer;
    String name = "transfer";
    boolean isZero = true;
    double pow = 1.0;

    public LeTransfer(HardwareMap hw)
    {
        transfer = hw.get(DcMotorEx.class, name);
        transfer.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setPower()
    {
        transfer.setPower(pow);
    }
    public void stop()
    {
        transfer.setPower(0);
    }

    private double getPower()
    {
        return transfer.getPower();
    }


    public void togglePower()
    {

        if(isZero)
        {
            setPower();
        }else {
            stop();
        }
        isZero = !isZero;
    }


    public void reverseMotor()
    {
        pow *= -1;
    }

    public String getData()
    {
        return "Transfer Power: " + getPower();
    }
}
