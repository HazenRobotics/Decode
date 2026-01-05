package org.firstinspires.ftc.teamcode.NewBot.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LeTransfer {
    //Ninja stars,
    //1 motor
    DcMotorEx transfer;
    String name = "transfer";
    boolean isFull = false;
    double downPow = 0.5, upPow = 0.5;

    public LeTransfer(HardwareMap hw)
    {
        transfer = hw.get(DcMotorEx.class, name);
        transfer.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setPower()
    {
        transfer.setPower(downPow);
    }

    public void setPower(double pow)
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

        if(isFull)
        {
            setPower();
        }else {
            stop();
        }
        isFull = !isFull;
    }


    public void reverseMotor()
    {
        downPow *= -1;
    }

    public String getData()
    {
        return "Transfer Power: " + getPower();
    }
}
