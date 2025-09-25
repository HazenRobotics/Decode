package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class TankDrive{
    DcMotorEx left, right;
    String leftName = "leftDrive", rightName = "rightDrive";

    public TankDrive(HardwareMap hw)
    {
       left = hw.get(DcMotorEx.class, leftName);
       right = hw.get(DcMotorEx.class, rightName);
        left.setDirection(DcMotorSimple.Direction.REVERSE);



    }

    public TankDrive(HardwareMap hw, String leftName, String rightName, String  imuName) {
        left = hw.get(DcMotorEx.class, leftName);
        right = hw.get(DcMotorEx.class, rightName);


    }

    public void drive(double forward, double rotate)
    {

        //Issue with rotating
       left.setPower(forward + rotate);
       right.setPower(forward - rotate);


    }







}
