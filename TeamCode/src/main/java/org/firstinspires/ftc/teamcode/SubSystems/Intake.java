package org.firstinspires.ftc.teamcode.SubSystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Intake {
    private String name = "intake";
    DcMotorEx intake, otherIntake;

    private double intakePow, otherIntakePow;
    private boolean twoMotors = false;
    public Intake(HardwareMap hw)
    {
        intake = hw.get(DcMotorEx.class, name);
    }
    public Intake(HardwareMap hw, String name)
    {
        intake = hw.get(DcMotorEx.class, name);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public Intake(HardwareMap hw, String leftName, String rightName)
    {
        intake = hw.get(DcMotorEx.class, leftName);
        otherIntake = hw.get(DcMotorEx.class, rightName);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        otherIntake.setDirection(DcMotorSimple.Direction.FORWARD);
        twoMotors = true;
    }

//    public Intake(HardwareMap hw, String name1, String name2)
//    {
//        intake = hw.get(DcMotorEx.class, name1);
//        intake = hw.get(DcMotorEx.class, name2);
//        intake.setDirection(DcMotorSimple.Direction.REVERSE);
//    }

    public void setPower(double power)
    {
        if(twoMotors)
        {
            intake.setPower(power);
            otherIntake.setPower(power);
        }else {
            intake.setPower(power);
        }

    }
    public void intakeToggle(double power)
    {
        if(twoMotors)
        {
            intakePow = (intakePow == power) ? 0: power;
            otherIntakePow = (intakePow == power) ? 0: power;
            intake.setPower(intakePow);
            otherIntake.setPower(otherIntakePow);
        }else {
            intakePow = (intakePow == power) ? 0: power;
            intake.setPower(intakePow);
        }

    }

    public void setPowerWithTime(double power, int time) throws InterruptedException {
        if(twoMotors)
        {
            intake.setPower(power);
            otherIntake.setPower(power);
            Thread.sleep(time);
        }else {
            intake.setPower(power);
            Thread.sleep(time);
        }

    }
    public double getCurrent()
    {
        return intake.getCurrent(CurrentUnit.AMPS);
    }

    public double getPower()
    {
        return intake.getPower();
    }
    public String getData() {
        return "Intake power: " + intake.getPower();
    }

}
