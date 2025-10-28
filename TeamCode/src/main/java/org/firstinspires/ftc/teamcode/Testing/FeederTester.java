package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.StarterRobot;
import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
@TeleOp(group = "A LeTeleOp", name = "LeFeeder Test")
public class FeederTester extends LinearOpMode {

        Feeder feeder;
        GamepadEvents controller1, controller2;

        @Override
        public void runOpMode() throws InterruptedException {
            controller1 = new GamepadEvents(gamepad1);
            controller2 = new GamepadEvents(gamepad2);
            feeder = new Feeder(hardwareMap);
            waitForStart();
            while(opModeIsActive())
            {
               //feeder.feed(controller1.left_trigger.getTriggerValue() - controller1.right_trigger.getTriggerValue());
               if(controller1.a.onPress()){
                   feeder.feed();
               }
                controller1.update();
                controller2.update();

               telemetry.addLine(feeder.getData());
                telemetry.addLine("Left and Right Triggers to control feeder, a button to feed");
                telemetry.update();

            }
        }
}
