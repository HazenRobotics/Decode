package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//Initial Testing, i.e messing around
@TeleOp(group = "A LeTeleOp", name = "LeLED Test")
public class InitialAprilTagLEDTesting extends LinearOpMode {
    Limelight3A limelight;
    RevBlinkinLedDriver led;
    //No Idea what the unit of measurement,
    //Data seems to be inconsisent
    final double limit = 10.0;


    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        led = hardwareMap.get(RevBlinkinLedDriver.class, "led");
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

                if(Math.hypot(tx, ty) >= limit)
                {
                    led.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN);
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
