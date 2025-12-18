package org.firstinspires.ftc.teamcode.LeScarab.TungTungTungTester;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;
import org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems.LeOutake;
import org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems.LeStopper;

@TeleOp(name = "LeOutakeTester", group = "1 TungTungTungTesting")
public class LeOutakeTester extends LinearOpMode {
    LeOutake flywheel;
    GamepadEvents controller;
    LeStopper stopper;
    int velocity = 1200;
    @Override
    public void runOpMode() throws InterruptedException {
        flywheel = new LeOutake(hardwareMap);
        controller = new GamepadEvents(gamepad1);
        stopper = new LeStopper(hardwareMap);

        waitForStart();
        while(opModeIsActive())
        {
            if(controller.left_bumper.onPress())
            {
                velocity += 25;
            }

            if(controller.dpad_up.onPress())
            {
                stopper.toggle();
            }

            if(controller.right_bumper.onPress())
            {
                velocity -= 25;
            }
            flywheel.setVelocity(velocity);

            telemetry.addLine("Left bumper to increase velocity");
            telemetry.addLine("right bumper to decrease velocity");
            telemetry.addLine("Left DPAD_UP to toggle Servo");
            telemetry.addData("Shooter Expected Velocity", velocity);
            telemetry.addData("Shooter Acutal Velocity", flywheel.getData());
            telemetry.update();
            controller.update();
        }
    }
}
