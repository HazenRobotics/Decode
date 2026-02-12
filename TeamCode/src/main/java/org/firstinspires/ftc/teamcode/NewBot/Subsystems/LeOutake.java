package org.firstinspires.ftc.teamcode.NewBot.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class LeOutake {

    //Motor stuff
    //2 flywheels
    //3200 rmp - 6000 rmp motor currently
    //2 motors - don’t direct drive
    DcMotorEx leftMotor, rightMotor;
    private String leftName = "leftFlyWheel", rightName = "rightFlyWheel";
    private double nominalVoltage = 12.0;
    private VoltageSensor voltageSensor;
    private static final double TICKS_PER_REV = 537.6;
    private final double P = 60;
    private final double F = 16.4;

    public LeOutake(HardwareMap hw)
    {
        leftMotor = hw.get(DcMotorEx.class, leftName);
        rightMotor = hw.get(DcMotorEx.class, rightName);

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        voltageSensor = hw.voltageSensor.iterator().next();
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        PIDFCoefficients pidf = new PIDFCoefficients(P, 0, 0, F);
        leftMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
        rightMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
    }

    public void updatePID(Double P, Double F){
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        leftMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        rightMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

    }

    //This method is redundant
    public double getVelocity()
    {
        return leftMotor.getVelocity();
    }

    public double getCurrent()
    {
        return leftMotor.getCurrent(CurrentUnit.AMPS);
    }

    //AngularVelocity btw
    public void setVelocity(double velocity)
    {
        //Some reason the right motor spins way faster
        //Issue the rightMotor always goes to 2000 even when constraining
        leftMotor.setVelocity(velocity);
        rightMotor.setVelocity(velocity);
    }
    public double getVoltageNormalizedVelocity(double targetTicksPerSec) {
        double currentVoltage = voltageSensor.getVoltage();
        double normalization = nominalVoltage / currentVoltage;
        return targetTicksPerSec * normalization;
    }
    public String getData() {
        return "Left Shooter: " + leftMotor.getVelocity() + "\nRight Shooter: " + rightMotor.getVelocity();

    }
}