package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Vision.AprilTags;

@TeleOp(group = "A", name = "LimelightTest")
public class LimelightTest extends LinearOpMode {
    AprilTags camera;
    @Override
    public void runOpMode() throws InterruptedException {
            camera = new AprilTags(hardwareMap,"i");
            waitForStart();
            while(opModeIsActive())
            {

                camera.readGoal();
               telemetry.addLine(camera.toString());
               telemetry.addData("Distance to Target",camera.getPosFromTag());
               telemetry.update();

            }
    }
}
