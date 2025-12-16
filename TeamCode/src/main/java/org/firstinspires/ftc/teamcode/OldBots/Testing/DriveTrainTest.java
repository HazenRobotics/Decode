package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(group = "A LeTeleOp", name = "LeDrive Test")
public class DriveTrainTest extends LinearOpMode {
    DcMotorEx frontLeft, backLeft, frontRight, backRight;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotorEx.class, "FLM");
        backLeft = hardwareMap.get(DcMotorEx.class, "BLM");
        frontRight = hardwareMap.get(DcMotorEx.class,"FRM");
        backRight = hardwareMap.get(DcMotorEx.class, "BRM");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection((DcMotor.Direction.REVERSE));

        waitForStart();

        while (opModeIsActive()){

            if(gamepad1.x){
                telemetry.addLine("Powering Front Left Motor");
                frontLeft.setPower(1);
            }
            else if(gamepad1.y){
                telemetry.addLine("Powering Front Right Motor");
                frontRight.setPower(1);
            }
            else if (gamepad1.a){
                telemetry.addLine("Powering Back Left Motor");
                backLeft.setPower(1);
            }
            else if(gamepad1.b){
                telemetry.addLine("Powering Back Right Motor");
                backRight.setPower(1);
            }
            else{
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
            }
            telemetry.addLine("X: FRONT LEFT MOTOR");
            telemetry.addLine("Y: FRONT RIGHT MOTOR");
            telemetry.addLine("a: BACK LEFT MOTOR");
            telemetry.addLine("b: BACK RIGHT MOTOR");

            telemetry.update();
        }
    }
}
