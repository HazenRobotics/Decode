package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
//Initial Testing, i.e messing around
@TeleOp(group = "A", name = "AprilTag Test")
public class AprilTagProcessorTesting extends LinearOpMode {
    Limelight3A limelight;



    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.start(); // This tells Limelight to start looking!

        limelight.pipelineSwitch(8); //Switch to 8 for testing purposes, 0 for actual

        waitForStart();
        while(opModeIsActive())
        {
            LLResult result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                double tx = result.getTx(); // How far left or right the target is (degrees)
                double ty = result.getTy(); // How far up or down the target is (degrees)
                double ta = result.getTa(); // How big the target looks (0%-100% of the image)

            if(Math.hypot(tx, ty) < 5)
            {
                telemetry.addData("hypot calc:", Math.hypot(tx, ty));
            }

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);


            } else {
            telemetry.addData("Limelight", "No Targets");
            }
            telemetry.update();

        }

    }



}
