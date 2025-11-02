package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.StarterRobot;
import org.firstinspires.ftc.teamcode.Robots.V2;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
@TeleOp(group = "A", name = "LeV2 TeleOP" )
public class V2TeleOP extends LinearOpMode {
    V2 robot;
    GamepadEvents controller1, controller2;
    @Override
    public void runOpMode() throws InterruptedException {
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);
        robot = new V2(hardwareMap, controller1, controller2);
        waitForStart();
        while(opModeIsActive())
        {
            robot.drive();
            if (controller1.left_bumper.onPress())
            {
                robot.intake();
            }
//            if (controller2.left_bumper.onPress())
//            {
//                robot.intake();
//            }
//            if(controller2.b.onPress())
//            {
//                robot.toggleFeed();
//            }


            robot.shoot();
            robot.updateShooting();
            controller1.update();
            controller2.update();

            telemetry.addLine("Use Left Joystick Y for movement, Right Joystick " +
                    "X for rotation");
            telemetry.addLine("Left bumper: intake");
            telemetry.addLine("Right bumper: shoot only");
//            telemetry.addLine("Driver 2\nLeft_Bumper: intake");
            telemetry.update();
        }
    }
}
