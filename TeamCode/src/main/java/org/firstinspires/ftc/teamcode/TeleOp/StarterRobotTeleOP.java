package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.StarterRobot;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

@TeleOp(group = "A", name = "LeStarter Robot TeleOP" )
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
                robot.intakeAndTransfer();
            }
            controller1.update();
            controller2.update();

            telemetry.addLine("Use Left Joystick Y for movement, Right Joystick " +
                    "X for rotation");
            telemetry.addLine("Use Bumpers for launching ball");

            telemetry.update();

        }
    }
}
