package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MecanumDrive {
    DcMotorEx leftTop, leftBottom, rightTop, rightBottom;
    IMU imu;
    String leftTopName = "FLM", leftBottomName = "BLM",
            rightTopName = "FRM", rightBottomName = "BRM", imuName = "imu";

    public MecanumDrive(HardwareMap hw) {
        leftTop = hw.get(DcMotorEx.class, leftTopName);
        rightTop = hw.get(DcMotorEx.class, rightTopName);
        leftBottom = hw.get(DcMotorEx.class, leftBottomName);
        rightBottom = hw.get(DcMotorEx.class, rightBottomName);

        leftTop.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBottom.setDirection(DcMotorSimple.Direction.REVERSE);

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

        leftTop.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBottom.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = hw.get(IMU.class, imuName);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
        imu.initialize(parameters);
    }

    public void drive(double forward, double strafe, double rotate) {

        //Issue with rotating
        leftTop.setPower(forward + strafe + rotate);
        rightTop.setPower(forward - strafe + rotate);
        leftBottom.setPower(forward - strafe - rotate);
        rightBottom.setPower(forward + strafe - rotate);

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


    public void resetHeading() {
        imu.resetYaw();
    }
}
