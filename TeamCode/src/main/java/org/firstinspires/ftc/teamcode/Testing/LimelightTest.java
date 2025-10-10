package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Vision.AprilTags;

@TeleOp(group = "A", name = "LimelightTest")
public class LimelightTest extends LinearOpMode {
    AprilTags camera;
    @Override
    public void runOpMode() throws InterruptedException {
            camera = new AprilTags("Blue");
            while(opModeIsActive())
            {
                camera.readGoal();

            }
    }
}
