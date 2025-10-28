package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

@TeleOp(group = "A LeTeleOp", name = "LeDrive Test")
public class driveTrain extends LinearOpMode {
    MecanumDrive driveTrain;
    GamepadEvents controller1;

    @Override
    public void runOpMode(){
        driveTrain = new MecanumDrive(hardwareMap, "FLM", "BLM", "FRM", "BRM", "imu");
        controller1 = new GamepadEvents(gamepad1);
        waitForStart();
        while(opModeIsActive()) {
            controller1.update();
            driveTrain.drive(controller1.left_stick_y, controller1.left_stick_x, controller1.right_stick_x);
            telemetry.addData("FL ticks", driveTrain.getFrontLeftTicks());
            telemetry.addData("FR ticks", driveTrain.getFrontRightTicks());
            telemetry.addData("BL ticks", driveTrain.getBackLeftTicks());
            telemetry.addData("BL ticks", driveTrain.getBackRightTicks());

            telemetry.update();
            telemetry.update();
        }
    }
}
