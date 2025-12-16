package org.firstinspires.ftc.teamcode.OldBots.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OldBots.Robots.StarterRobot;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;

public class StarterRobotTeleOP extends LinearOpMode {
    StarterRobot robot;
    GamepadEvents controller1, controller2;

    @Override
    public void runOpMode() throws InterruptedException {
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);
        robot = new StarterRobot(hardwareMap, controller1, controller2);
        waitForStart();
        while(opModeIsActive())
        {
            robot.drive();
            if (controller1.left_bumper.onPress()) {
                robot.intake();
            }

            if (controller1.right_bumper.onPress()) {
                robot.shoot();
            }
            if(controller1.b.onPress()) {
                robot.transfer();
            }
            robot.updateShooting();
            robot.updateTransfer();
            controller1.update();
            controller2.update();

            telemetry.addLine("Use Left Joystick Y for movement, Right Joystick " +
                    "X for rotation");
            telemetry.addLine("Left bumper: intake + shoot");
            telemetry.addLine("Right bumper: shoot only");
//            telemetry.addLine(robot.getData());
            telemetry.update();

        }
    }
}
