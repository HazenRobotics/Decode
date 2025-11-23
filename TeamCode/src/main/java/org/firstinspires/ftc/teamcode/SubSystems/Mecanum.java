package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class Mecanum {
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


    public Mecanum(HardwareMap hw) {
        leftTop = hw.get(DcMotorEx.class, leftTopName);
        rightTop = hw.get(DcMotorEx.class, rightTopName);
        leftBottom = hw.get(DcMotorEx.class, leftBottomName);
        rightBottom = hw.get(DcMotorEx.class, rightBottomName);

        leftBottom.setDirection(DcMotorSimple.Direction.REVERSE);
        leftTop.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBottom.setDirection(DcMotorSimple.Direction.FORWARD);
        rightTop.setDirection(DcMotorSimple.Direction.FORWARD);

        leftTop.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightTop.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hw.get(IMU.class, imuName);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        imu.initialize(parameters);
    }

    public Mecanum(HardwareMap hw, String  leftTopName, String leftBottomName, String rightTopName,
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
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        imu.initialize(parameters);
    }

    public void drive(double forward, double strafe, double rotate)
    {

        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxPower = 1.0;
        double maxSpeed = 1.0;

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        leftTop.setPower(maxSpeed * (frontLeftPower / maxPower));
        leftBottom.setPower(maxSpeed * (frontLeftPower / maxPower));
        rightTop.setPower(maxSpeed * (frontLeftPower / maxPower));
        rightBottom.setPower(maxSpeed * (frontLeftPower / maxPower));
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
        double theta=Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);

        theta = AngleUnit.normalizeRadians(theta -
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        this.drive(newForward, newStrafe, rotate);
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
    public void enableDriveUsingEncoders() {
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
