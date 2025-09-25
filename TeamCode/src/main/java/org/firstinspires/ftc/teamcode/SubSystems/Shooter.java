package org.firstinspires.ftc.teamcode.SubSystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    //Designers may test multiple motors
    //this class must be as modular as possible
    DcMotorEx leftMotor, rightMotor;
    private String lmName = "leftShooter", rmName = "rightShooter";
    private double defaultPower = 0.7;
    private boolean twoMotors = false;


    //Add Two Servos for controlling the pushing of the ball
    public Shooter(HardwareMap hw)
    {
        leftMotor = hw.get(DcMotorEx.class, lmName);
        rightMotor = hw.get(DcMotorEx.class, rmName);
        twoMotors = true;

    }

    public Shooter(HardwareMap hw, String lmName)
    {
        leftMotor = hw.get(DcMotorEx.class, lmName);

    }

    public Shooter(HardwareMap hw, String lmName, String rmName)
    {
        leftMotor = hw.get(DcMotorEx.class, lmName);
        rightMotor = hw.get(DcMotorEx.class, rmName);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        twoMotors = true;
    }

    public void shoot() {
        if(!twoMotors)
        {

            leftMotor.setPower(defaultPower);

        }else {
            leftMotor.setPower(defaultPower);
            rightMotor.setPower(defaultPower);

        }
    }

    public void reset() {
        if(!twoMotors)
        {

            leftMotor.setPower(0);

        }else {
            leftMotor.setPower(0);
            rightMotor.setPower(0);

        }
    }

    public void shoot(double power)
    {
        if(!twoMotors)
        {
            leftMotor.setPower(power);
        }else {
            leftMotor.setPower(power);
            rightMotor.setPower(power);
        }
    }

    public double getPower()
    {
           return leftMotor.getPower();

    }



}
