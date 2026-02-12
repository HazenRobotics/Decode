package org.firstinspires.ftc.teamcode.NewBot.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NewBot.Robot.NewBot;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;

@TeleOp(name = "Skibidi Scarab Ohio 67 Kai Cenat IShowSpeed Outreach", group = "1")
public class OutreachTeleOP extends LinearOpMode {
    boolean isShooterReversed = false;
    boolean ledCrazy = false;
    ElapsedTime time;
    double speedLimit = 0.5;
    boolean adjustFlywheel = true;
    GamepadEvents controller1, controller2;
    @Override
    public void runOpMode() throws InterruptedException {
        controller1 = new GamepadEvents(gamepad1);
        NewBot robot = new NewBot(hardwareMap, telemetry, 20);
        time = new ElapsedTime();
        waitForStart();
        robot.store();
        while(opModeIsActive())
        {
            robot.drive(controller1.left_stick_y, controller1.left_stick_x, controller1.right_stick_x);
            if(adjustFlywheel)
            {
                if(controller1.dpad_up.onPress())
                {
                    robot.adJustFlywheel(10);
                }else if(controller1.dpad_down.onPress())
                {
                    robot.adJustFlywheel(-10);
                }

            }else {
                if(controller1.dpad_up.onPress())
                {
                    speedLimit += 0.05;
                }else if(controller1.dpad_down.onPress())
                {
                    speedLimit -= 0.05;
                }
            }


            if(controller1.left_bumper.onPress())
            {
                robot.toggleShootStore();
            }

            if(controller1.right_bumper.onPress())
            {
                ledCrazy = !ledCrazy;
                adjustFlywheel = !adjustFlywheel;
            }

            if(ledCrazy)
            {
                if(time.seconds() >= speedLimit)
                {
                    robot.ledCrazy();
                    time.reset();
                }
            }else {
                    robot.toggleLED();
            }


            telemetry.addLine(robot.getData());
            telemetry.update();
            controller1.update();
        }
    }
}
