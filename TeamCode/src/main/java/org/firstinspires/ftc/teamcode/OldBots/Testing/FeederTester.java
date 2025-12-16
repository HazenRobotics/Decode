package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
@TeleOp(group = "A LeTeleOp", name = "LeFeeder Test")
public class FeederTester extends LinearOpMode {

        CRServo leftFeeder, rightFeeder;
        double leftSpeed = 1, rightSpeed = 1;
        GamepadEvents controller1, controller2;

        @Override
        public void runOpMode() throws InterruptedException {
            controller1 = new GamepadEvents(gamepad1);
            controller2 = new GamepadEvents(gamepad2);
            leftFeeder = hardwareMap.get(CRServo.class,"leftFeeder");
            rightFeeder = hardwareMap.get(CRServo.class,"rightFeeder");
            waitForStart();
            while(opModeIsActive())
            {
               //feeder.feed(controller1.left_trigger.getTriggerValue() - controller1.right_trigger.getTriggerValue());

               if(controller1.left_bumper.onPress())
               {
                   leftFeeder.setPower(leftSpeed);
               }

                if(controller1.right_bumper.onPress())
                {
                    rightFeeder.setPower(rightSpeed);
                }

                if(controller1.a.onPress())
                {
                    leftSpeed *= -1;
                }

                if(controller1.b.onPress())
                {
                    rightSpeed *= -1;
                }


                telemetry.addLine("Left_Bumper: left Feeder");
                telemetry.addLine("Right_Bumper: right Feeder");
                telemetry.addLine("Left_Speed Reverse: A");
                telemetry.addLine("Right_Speed Reverse: B");
                telemetry.update();
                controller1.update();
                controller2.update();

            }
        }
}
