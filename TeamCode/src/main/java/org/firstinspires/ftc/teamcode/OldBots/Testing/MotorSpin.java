package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
@TeleOp(group = "A", name = "Motor Spin")
public class MotorSpin extends LinearOpMode {
    Intake intake;
    GamepadEvents controller;
    @Override
    public void runOpMode() throws InterruptedException {
        intake = new Intake(hardwareMap, "intake");
        controller = new GamepadEvents(gamepad1);
        waitForStart();
        while (opModeIsActive())
        {
            if(controller.left_bumper.onPress())
            {
                intake.setPowerWithTime(1, 2000);

            }

            telemetry.addLine("Left Bumper to spin motor for 2 seconds");
        }
    }
}
