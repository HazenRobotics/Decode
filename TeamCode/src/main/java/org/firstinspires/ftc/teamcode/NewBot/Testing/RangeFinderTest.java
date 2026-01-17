package org.firstinspires.ftc.teamcode.NewBot.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NewBot.Utils.LEDLights;
import org.firstinspires.ftc.teamcode.NewBot.Utils.RangeFinder;

@TeleOp(group = "Tester", name = "Range Finder Test")
public class RangeFinderTest extends LinearOpMode {
    LEDLights lights;
    RangeFinder rangeFinder;
    int count = 0;
    ElapsedTime time, outTime;
    boolean ballDetected = false, isBallOut = false;

    @Override
    public void runOpMode() throws InterruptedException
    {
        lights = new LEDLights(hardwareMap, "led");
        rangeFinder = new RangeFinder(hardwareMap);
        time = new ElapsedTime();
        outTime = new ElapsedTime();
        waitForStart();
        while(opModeIsActive())
        {

            if(rangeFinder.isBallOut())
            {
                isBallOut = true;
                lights.setColor(LEDLights.PURPLE_WEIGHT);
            }else {
                if (isBallOut && outTime.seconds() > 0.1) {
                    count--;
                    isBallOut = false;
                } else if (isBallOut) {
                    isBallOut = false;
                }
                outTime.reset();
                lights.setColor(LEDLights.WHITE_WEIGHT);
            }
//            if(rangeFinder.isBallDetected())
//            {
//
//                ballDetected = true;
//                lights.setColor(LEDLights.PURPLE_WEIGHT);
//            }else {
//                if(ballDetected && time.seconds() > 0.1)
//                {
//                    count++;
//                    ballDetected = false;
//                }else if(ballDetected)
//                {
//                    ballDetected = false;
//                }
//                time.reset();
//                lights.setColor(LEDLights.WHITE_WEIGHT);
//            }
            telemetry.addData("Outer Distance", rangeFinder.readOutake1Distance());
//            telemetry.addData("Inner Distance", rangeFinder.readOutake2Distance());
            telemetry.addData("Count: ", count);
            telemetry.update();

        }
    }
}
