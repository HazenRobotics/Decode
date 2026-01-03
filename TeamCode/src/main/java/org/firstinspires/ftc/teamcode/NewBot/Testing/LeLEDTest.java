package org.firstinspires.ftc.teamcode.NewBot.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeLED;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;

@TeleOp(name = "LeLEDLEBRONTESTING", group = "1 TungTungTungTesting")
public class LeLEDTest extends LinearOpMode {
    LeLED led;
    GamepadEvents controller;
    double value = 0;
    private int index = 0;
    private final LeLED.Colors[] colors = LeLED.Colors.values();
    @Override
    public void runOpMode() throws InterruptedException {
        led = new LeLED(hardwareMap);
        controller = new GamepadEvents(gamepad1);

        waitForStart();
        while(opModeIsActive())
        {
            if (controller.left_bumper.onPress()) {
                index++;
                if (index >= colors.length) {
                    index = 0;
                }
            }

            if (controller.right_bumper.onPress()) {
                index--;
                if (index < 0) {
                    index = colors.length - 1;
                }
            }



            telemetry.addLine("Left and right bumper to iterate through colors");
            telemetry.update();
            controller.update();
        }


    }
}
