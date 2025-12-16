package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.teamcode.utils.ColorSensor;
import org.firstinspires.ftc.teamcode.utils.LEDLights;

@TeleOp (group = "test", name = "colorSensor Test")
public class ColorSensorTester extends LinearOpMode {
    ColorSensor colorSensor;
    LEDLights[] lights;
    int count = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        lights = new LEDLights[1];
        lights[0] = new LEDLights(hardwareMap, "led");

        colorSensor = new ColorSensor(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            ColorSensor.Color color = colorSensor.getColor();

            switch (color){
                case Green:
                    lights[0].setColor(LEDLights.GREEN_WEIGHT);
                    count++;

                    break;
                case Purple:
                    lights[0].setColor(LEDLights.PURPLE_WEIGHT);
                    count++;

                    break;
                default:
                    lights[0].setColor(0);
                    break;
            }

//            if(count == 3)
//            {
//                lights[0].setColor(LEDLights.BlUE_WEIGHT);
//            }
            telemetry.addData("Pin0", colorSensor.getPin0().getState());
            telemetry.addData("Pin1", colorSensor.getPin1().getState());
            telemetry.addData("Count", count);
            telemetry.update();

        }
    }

}