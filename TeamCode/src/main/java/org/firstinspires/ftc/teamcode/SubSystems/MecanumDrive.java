package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDrive {
    DcMotorEx leftTop, leftBottom, rightTop, rightBottom;
    String leftTopName = "leftTopDrive", leftBottomName = "leftBottomDrive",
            rightTopName = "rightTopDrive", rightBottomName = "rightBottomDrive";

    public MecanumDrive(HardwareMap hw) {
        leftTop = hw.get(DcMotorEx.class, leftTopName);
        rightTop = hw.get(DcMotorEx.class, rightTopName);
        leftBottom = hw.get(DcMotorEx.class, leftBottomName);
        rightBottom = hw.get(DcMotorEx.class, rightBottomName);

        leftTop.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBottom.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public MecanumDrive(HardwareMap hw, String  leftTopName, String leftBottomName, String rightTopName,
                        String rightBottomName) {
        leftTop = hw.get(DcMotorEx.class,  leftTopName);
        rightTop = hw.get(DcMotorEx.class, leftBottomName);
        leftBottom = hw.get(DcMotorEx.class, rightTopName);
        rightBottom = hw.get(DcMotorEx.class, rightBottomName);

        leftTop.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBottom.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void drive(double forward, double strafe, double rotate) {

        //Issue with rotating
        leftTop.setPower(forward + strafe + rotate);
        rightTop.setPower(forward - strafe + rotate);
        leftBottom.setPower(forward - strafe - rotate);
        rightBottom.setPower(forward + strafe - rotate);

    }
}
