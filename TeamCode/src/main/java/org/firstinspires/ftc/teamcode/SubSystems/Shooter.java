package org.firstinspires.ftc.teamcode.SubSystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class Shooter {
    //Designers may test multiple motors
    //this class must be as modular as possible
    DcMotorEx leftMotor, rightMotor;
    private String lmName = "leftShooter", rmName = "rightShooter";
    private double defaultPower = 0.7;
    private boolean twoMotors = false;
    private VoltageSensor voltageSensor;
    private double nominalVoltage = 12.0;
    private double wheelRadius = 0.05; //meters
    private double kF = 10;           // Feedforward gain
    private double kP = 0;            // Proportional gain
    private double kI = 0;               // Integral gain
    private double kD = 10;
    private static final double TICKS_PER_REV = 537.6;
    private static final double MAX_RPM = 6000;


    public Shooter(HardwareMap hw)
    {
        leftMotor = hw.get(DcMotorEx.class, lmName);
        rightMotor = hw.get(DcMotorEx.class, rmName);

        twoMotors = true;

    }

    public Shooter(HardwareMap hw, String lmName)
    {
        leftMotor = hw.get(DcMotorEx.class, lmName);
        voltageSensor = hw.voltageSensor.iterator().next();
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }
    public double getVelocity(){
        return leftMotor.getVelocity();
    }
    public void setTargetRPM(double targetRPM){
        double targetTicksPerSec = rpmToTicksPerSec(targetRPM);
        leftMotor.setVelocity(targetTicksPerSec);
    }
    public double getVoltageNormalizedVelocity(double targetTicksPerSec) {
        double currentVoltage = voltageSensor.getVoltage();
        double normalization = nominalVoltage / currentVoltage;
        return targetTicksPerSec * normalization;
    }
    public double getVoltage() {
        return voltageSensor.getVoltage();
    }

    public void setVelocity(double ticks){
        double normalizedTicks = getVoltageNormalizedVelocity(ticks);
        leftMotor.setVelocity(normalizedTicks);
    }


    public double getCurrentRPM() {
        double ticksPerSec = leftMotor.getVelocity();
        return ticksPerSecToRPM(ticksPerSec);
    }


    public double calculateTargetRPM(double distanceMeters, double targetHeightMeters, double angleDegrees) {
        double g = 9.81;
        double theta = Math.toRadians(angleDegrees);

        // Projectile motion equation for required exit velocity
        double numerator = distanceMeters * distanceMeters * g;
        double denominator = 2 * Math.cos(theta) * Math.cos(theta) *
                (distanceMeters * Math.tan(theta) - targetHeightMeters);

        if (denominator <= 0) return MAX_RPM; // failsafe
        double vExit = Math.sqrt(numerator / denominator);

        // Convert linear velocity to angular velocity (rad/s)
        double omega = vExit / wheelRadius;

        // Convert rad/s to RPM
        double targetRPM = (omega * 60.0) / (2 * Math.PI);

        // Cap within safe limits
        return Math.min(targetRPM, MAX_RPM);
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

    private double ticksPerSecToRPM(double ticksPerSec) {
        return (ticksPerSec * 60.0) / TICKS_PER_REV;
    }

    private double rpmToTicksPerSec(double rpm) {
        return (rpm * TICKS_PER_REV) / 60.0;
    }


//    public void getRPM()
//    {
//        leftMotor.getVelocity();
//    }

    public void setRPM(double rpm)
    {
        leftMotor.setPower(rpm/6000);
        leftMotor.setVelocityPIDFCoefficients(kP, kI, kD, kF);

    }
    public String getData() {
        if (twoMotors) {
            return "Left Shooter: " + leftMotor.getPower() + "\nRight Shooter: " + rightMotor.getPower();
        } else {
            return "Left Shooter: " + leftMotor.getPower();
        }
    }


}
