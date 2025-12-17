package org.firstinspires.ftc.teamcode.OldBots.Testing;


import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.LED;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision.LogitechCam;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.VelocityCalculator;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
@TeleOp(group = "Tester", name = "VelocityCalculatorTest")
public class VelocityCalculatorTest extends LinearOpMode {
    Shooter shooter;
    Feeder feeder;
    Intake intake;
    VelocityCalculator calculator;
    LogitechCam camera;
    GamepadEvents controller;
    LED led;
    boolean canShoot = false;
    int TARGET_TAG_ID = 24;
    //Read AprilTag, return a pattern:
    //20: Blue Goal
    //21: Green, Purple, Purple
    //22: Purple, Green, Purple
    //23: Purple, Purple, Green
    //24: Red Goal
    @Override
    public void runOpMode() throws InterruptedException {
            shooter = new Shooter(hardwareMap);
            calculator = new VelocityCalculator();
            camera = new LogitechCam();
            feeder = new Feeder(hardwareMap);
            intake = new Intake(hardwareMap);
            led = new LED(hardwareMap);
            camera.init(hardwareMap,telemetry);
            controller = new GamepadEvents(gamepad1);
            waitForStart();
            while(opModeIsActive())
            {
                AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);

                if(controller.a.onPress())
                {
                    canShoot = !canShoot;
                }

                if(canShoot && controller.left_bumper.onPress())
                {
                    shooter.setVelocity(calculator.calculateAngularVelocityForTarget(camera.getHorizontalData(targetTag)));
                    feeder.feed();
                    intake.intakeToggle(-0.8);
                }

                if(targetTag != null)
                {
                    led.setColor(RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN);
                }

                camera.update();

                telemetry.addData("Horizontal Distance to AprilTag", camera.getHorizontalData(targetTag));
                telemetry.addData("Calculated Linear velocity", calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
                telemetry.addData("Calculated angular velocity", calculator.calculateAngularVelocityForTarget(camera.getHorizontalData(targetTag)));
                telemetry.addData("Can shoot", canShoot);
                telemetry.addLine("Press A to allow shooting\nPress left_bumper to shoot");
                telemetry.update();
                controller.update();
            }


    }
}
