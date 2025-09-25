package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.StarterRobot;
import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
@TeleOp(name = "Feeder Test")
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
               if(controller1.left_bumper.onPress())
               {
                   feeder.feed();
               }
                if(controller1.right_bumper.onPress())
                {
                    feeder.reset();
                }
                controller1.update();
                controller2.update();

               telemetry.addLine(feeder.getData());
                telemetry.addLine("Left Bumper and Right Bumper to Alternate Pos");
                telemetry.update();

            }
        }
}
