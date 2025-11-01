package org.firstinspires.ftc.teamcode.SubSystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    //Designers may test multiple motors
    //this class must be as modular as possible
    DcMotorEx leftMotor, rightMotor;
    Limelight3A limelight;
    private String lmName = "leftShooter", rmName = "rightShooter", limelightName = "limelight";
    private double defaultPower = 0.7;
    private boolean twoMotors = false;
    public enum ShootingStates
    {
        Far, near
    }


    //Add Two Servos for controlling the pushing of the ball
    public Shooter(HardwareMap hw)
    {
        leftMotor = hw.get(DcMotorEx.class, lmName);
        rightMotor = hw.get(DcMotorEx.class, rmName);
//        limelight = hw.get(Limelight3A.class, limelightName);
        twoMotors = true;


    }

    public Shooter(HardwareMap hw, String lmName)
    {
        leftMotor = hw.get(DcMotorEx.class, lmName);
//        limelight = hw.get(Limelight3A.class, limelightName);
    }

    public Shooter(HardwareMap hw, String lmName, String rmName)
    {
        leftMotor = hw.get(DcMotorEx.class, lmName);
        rightMotor = hw.get(DcMotorEx.class, rmName);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        limelight = hw.get(Limelight3A.class, limelightName);
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

//    public void getRPM()
//    {
//        leftMotor.getVelocity();
//    }

    public void setRPM(double rpm)
    {
        leftMotor.setPower(rpm/6000);
    }
    public String getData() {
        if (twoMotors) {
            return "Left Shooter: " + leftMotor.getPower() + "\nRight Shooter: " + rightMotor.getPower();
        } else {
            return "Left Shooter: " + leftMotor.getPower();
        }
    }


}
