package org.firstinspires.ftc.teamcode.NewBot.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeLED;
import org.firstinspires.ftc.teamcode.NewBot.Utils.ColorSensor;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.NewBot.Utils.LEDLights;
import org.firstinspires.ftc.teamcode.NewBot.Utils.RangeFinder;

@TeleOp(group = "A Zebron Tester", name = "Robot Range Finder Test")
public class RangeAndColorSensorTest extends LinearOpMode {
    RangeFinder rangeFinder;
    ColorSensor colorSensor;
    int count = 0;
    GamepadEvents controller;
    ElapsedTime time, outTime;
    LeLED led;
    boolean ballDetected = false, isBallOut = false, detectedColor = false;
    @Override
    public void runOpMode() throws InterruptedException
    {
        colorSensor = new ColorSensor(hardwareMap);
        controller = new GamepadEvents(gamepad1);
        rangeFinder = new RangeFinder(hardwareMap);
        time = new ElapsedTime();
        led = new LeLED(hardwareMap);
        outTime = new ElapsedTime();
        waitForStart();
        while(opModeIsActive())
        {

            if(count == 3)
            {
                led.setRightLEDColor(LeLED.Colors.GREEN);
            }else{
                led.setRightLEDColor(LeLED.Colors.ORANGE);
            }

            if(rangeFinder.isBallOut())
            {
                isBallOut = true;
            }else {
                if(isBallOut && outTime.seconds() > 0.1)
                {
                    count--;
                    isBallOut = false;
                }else if(isBallOut)
                {
                    isBallOut = false;
                }
                outTime.reset();
            }


            if(rangeFinder.isBallDetected())
            {

                ballDetected = true;

            }else {
                if(ballDetected && time.seconds() > 0.1)
                {
                    count++;
                    ballDetected = false;
                }else if(ballDetected)
                {
                    ballDetected = false;
                }
                time.reset();
            }
        }
    }
}
