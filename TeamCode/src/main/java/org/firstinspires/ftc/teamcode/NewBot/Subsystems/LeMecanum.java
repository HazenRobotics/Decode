package org.firstinspires.ftc.teamcode.NewBot.Subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class LeMecanum {
    //Need to figure out abstraction for this class
    //TODO: Keep the core initialization, but move the math out to wrapper class
    DcMotorEx leftTop, leftBottom, rightTop, rightBottom;
    private double CM_2_INCHES = 0.39370079;
    private double WHEEL_DIAMETER = 104; //mm
    private int TICKS_PER_ROT = 4096;
    private double forwardConst = 0.7;
    private double INCHES_PER_ROTATION = (WHEEL_DIAMETER * CM_2_INCHES * Math.PI) / 10; //Distance in inches per rotation
    private double TICKS_PER_INCH = (TICKS_PER_ROT / INCHES_PER_ROTATION); //# of Ticks per Inch of distance
    IMU imu;
    String leftTopName = "FLM", leftBottomName = "BLM",
            rightTopName = "FRM", rightBottomName = "BRM", imuName = "imu";


    public LeMecanum(HardwareMap hw)
    {
        leftTop = hw.get(DcMotorEx.class,  leftTopName);
        rightTop = hw.get(DcMotorEx.class, rightTopName);
        leftBottom = hw.get(DcMotorEx.class, leftBottomName);
        rightBottom = hw.get(DcMotorEx.class, rightBottomName);

        rightTop.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBottom.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();

        imu = hw.get(IMU.class, imuName);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
    }

    public double getRotation()
    {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public LeMecanum(HardwareMap hw, String  leftTopName, String leftBottomName, String rightTopName,
                     String rightBottomName, String imuName) {
        leftTop = hw.get(DcMotorEx.class,  leftTopName);
        rightTop = hw.get(DcMotorEx.class, rightTopName);
        leftBottom = hw.get(DcMotorEx.class, leftBottomName);
        rightBottom = hw.get(DcMotorEx.class, rightBottomName);

        rightTop.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBottom.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();

        imu = hw.get(IMU.class, imuName);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
    }

    public void drive(double forward, double strafe, double rotate)
    {

        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        leftTop.setPower(frontLeftPower);
        leftBottom.setPower(backLeftPower);
        rightTop.setPower(frontRightPower);
        rightBottom.setPower(backRightPower);
    }

    public void setForwardConst(double value)
    {
        forwardConst = value;
    }
    //rotation is going front and back
    //strafe is rotate
    //Forward and back is going inward

    //Formula's copied from gmZero
    public void fieldCentricDrive(double forward, double strafe, double rotate) {
        double x = forward; // Remember, Y stick value is reversed
        double y = strafe; // Counteract imperfect strafing
        double rx = rotate;

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        leftTop.setPower(frontLeftPower);
        leftBottom.setPower(backLeftPower);
        rightTop.setPower(frontRightPower);
        rightBottom.setPower(backRightPower);
    }
    public int getFrontLeftTicks() {
        return leftTop.getCurrentPosition();
    }
    public int getFrontRightTicks() {
        return rightTop.getCurrentPosition();
    }
    public int getBackLeftTicks() {
        return leftBottom.getCurrentPosition();
    }
    public int getBackRightTicks() {
        return rightBottom.getCurrentPosition();
    }
    public void resetEncoders() {
        leftTop.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightTop.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftBottom.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightBottom.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftTop.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightTop.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        leftBottom.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightBottom.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void enableDriveUsingEncoders()
    {
        if (leftTop != null && rightTop != null && leftBottom != null && rightBottom != null) {
            leftTop.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightTop.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }


    public void resetHeading() {
        imu.resetYaw();
    }
}
