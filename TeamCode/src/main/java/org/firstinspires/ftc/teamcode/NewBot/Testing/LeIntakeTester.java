package org.firstinspires.ftc.teamcode.NewBot.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeIntake;

@TeleOp(name = "LeIntakeTester", group = "1 TungTungTungTesting")
public class LeIntakeTester extends LinearOpMode {
    LeIntake intake;
    GamepadEvents controller;
    double power = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        intake = new LeIntake(hardwareMap);
        controller = new GamepadEvents(gamepad1);
        waitForStart();
        while(opModeIsActive())
        {
            if(controller.left_bumper.onPress())
            {
                intake.feed();
            }

            if(controller.right_bumper.onPress())
            {
                intake.stop();
            }


            telemetry.addLine("Press Left bumper to intake\nPress Right Bumper to stop");
            telemetry.addData("Power", intake.getPower());
            telemetry.update();
            controller.update();

        }


    }
}
