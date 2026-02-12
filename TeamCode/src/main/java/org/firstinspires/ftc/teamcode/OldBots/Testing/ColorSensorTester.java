package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.NewBot.Utils.ColorSensor;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.NewBot.Utils.LEDLights;

@TeleOp (group = "test", name = "colorSensor Test")
public class ColorSensorTester extends LinearOpMode {
    ColorSensor colorSensor;
    LEDLights lights;
    GamepadEvents controller;
    boolean detectedColor = false;
    int count = 0;
    @Override
    public void runOpMode() throws InterruptedException {
//        lights = new  (hardwareMap, "leftLED");
        colorSensor = new ColorSensor(hardwareMap);
        controller = new GamepadEvents(gamepad1);
        colorSensor = new ColorSensor(hardwareMap);

        waitForStart();

        while (opModeIsActive())
        {
            ColorSensor.Color color = colorSensor.getColor();
            //Logic: Once Detect color and then no longer see color,
            //       count++
            switch (color){
                case BLUE:
//                    lights.setColor(LEDLights.BLUE_WEIGHT);
                    detectedColor = true;
                    break;
                case RED:
//                    lights.setColor(LEDLights.RED_WEIGHT);
                    detectedColor = true;
                    break;
                default:
                    if(detectedColor == true)
                    {
                        count++;
                        detectedColor = false;
                    }
//                    lights.setColor(LEDLights.WHITE_WEIGHT);
                    break;
            }

            telemetry.addData("Color", colorSensor.getSecondaryColor());
            telemetry.addData("Pin0", colorSensor.getPin0().getState());
            telemetry.addData("Pin1", colorSensor.getPin1().getState());
            telemetry.addData("Count: ", count);
            controller.update();
            telemetry.update();

        }
    }

}