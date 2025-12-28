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
    @Override
    public void runOpMode() throws InterruptedException {
        lights = new LEDLights(hardwareMap, "led");
        colorSensor = new ColorSensor(hardwareMap);
        controller = new GamepadEvents(gamepad1);
        colorSensor = new ColorSensor(hardwareMap);

        waitForStart();

        while (opModeIsActive())
        {
            ColorSensor.Color color = colorSensor.getColor();

            switch (color){
                case Green:
                    lights.setColor(LEDLights.GREEN_WEIGHT);

                    break;
                case Purple:
                    lights.setColor(LEDLights.PURPLE_WEIGHT);

                    break;
                default:
                    lights.setColor(LEDLights.WHITE_WEIGHT);
                    break;
            }



            telemetry.addData("Color", colorSensor.getColor());
            telemetry.addData("Pin0", colorSensor.getPin0().getState());
            telemetry.addData("Pin1", colorSensor.getPin1().getState());
            controller.update();
            telemetry.update();

        }
    }

}