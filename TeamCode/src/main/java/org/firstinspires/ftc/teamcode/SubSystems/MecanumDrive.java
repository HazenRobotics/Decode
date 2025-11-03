package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MecanumDrive {
    DcMotorEx leftTop, leftBottom, rightTop, rightBottom;
    private double CM_2_INCHES = 0.39370079;
    private double WHEEL_DIAMETER = 104; //mm
    private int TICKS_PER_ROT = 4096;
    private double INCHES_PER_ROTATION = (WHEEL_DIAMETER * CM_2_INCHES * Math.PI) / 10; //Distance in inches per rotation
    private double TICKS_PER_INCH = (TICKS_PER_ROT / INCHES_PER_ROTATION); //# of Ticks per Inch of distance
    IMU imu;
    String leftTopName = "FLM", leftBottomName = "BLM",
            rightTopName = "FRM", rightBottomName = "BRM", imuName = "imu";


    public MecanumDrive(HardwareMap hw) {
        leftTop = hw.get(DcMotorEx.class, leftTopName);
        rightTop = hw.get(DcMotorEx.class, rightTopName);
        leftBottom = hw.get(DcMotorEx.class, leftBottomName);
        rightBottom = hw.get(DcMotorEx.class, rightBottomName);

        rightTop.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBottom.setDirection(DcMotorSimple.Direction.REVERSE);

//        imu = hw.get(IMU.class, imuName);
//        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
//                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
//        imu.initialize(parameters);
    }

    public MecanumDrive(HardwareMap hw, String  leftTopName, String leftBottomName, String rightTopName,
                        String rightBottomName, String imuName) {
        leftTop = hw.get(DcMotorEx.class,  leftTopName);
        rightTop = hw.get(DcMotorEx.class, leftBottomName);
        leftBottom = hw.get(DcMotorEx.class, rightTopName);
        rightBottom = hw.get(DcMotorEx.class, rightBottomName);

        rightTop.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBottom.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();

        imu = hw.get(IMU.class, imuName);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
    }

    public void drive(double forward, double strafe, double rotate) {

        //Issue with rotating
        leftTop.setPower(forward + strafe - rotate);
        rightTop.setPower(forward - strafe + rotate);
        leftBottom.setPower(forward - strafe - rotate);
        rightBottom.setPower(forward + strafe + rotate);

    }
    //Formula's copied from gmZero
    public void fieldCentricDrive(double forward, double strafe, double rotate) {

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);


        double rotX = strafe * Math.cos(-botHeading) - forward * Math.sin(-botHeading);
        double rotY = strafe * Math.sin(-botHeading) + forward * Math.cos(-botHeading);


        rotX *= 1.1;


        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rotate), 1);
        double leftTopPower = (rotY + rotX + rotate) / denominator;
        double leftBottomPower = (rotY - rotX + rotate) / denominator;
        double rightTopPower = (rotY - rotX - rotate) / denominator;
        double rightBottomPower = (rotY + rotX - rotate) / denominator;

        leftTop.setPower(leftTopPower);
        leftBottom.setPower(leftBottomPower);
        rightTop.setPower(rightTopPower);
        rightBottom.setPower(rightBottomPower);
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
