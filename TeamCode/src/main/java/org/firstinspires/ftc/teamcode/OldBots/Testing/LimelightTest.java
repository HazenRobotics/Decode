package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision.LimelightCam;

@TeleOp(group = "A", name = "LimelightTest")
public class LimelightTest extends LinearOpMode {
    LimelightCam camera;
    @Override
    public void runOpMode() throws InterruptedException {
            camera = new LimelightCam(hardwareMap,"i");
            waitForStart();
            while(opModeIsActive())
            {

                camera.getPosFromTag();
               telemetry.addLine(camera.toString());
               telemetry.addData("Distance to Target",camera.getPosFromTag());
               telemetry.update();

            }
    }
}
