package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.VelocityCalculator;
import org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision.LimelightCam;
import org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision.LogitechCam;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.LED;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;

@TeleOp(group = "Tester", name = "LimelightVelocityTest")
public class LimelightTest extends LinearOpMode {
    LimelightCam camera;
    Shooter shooter;
    Feeder feeder;
    Intake intake;
    VelocityCalculator calculator;
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
            camera = new LimelightCam(hardwareMap,"i");
            shooter = new Shooter(hardwareMap);
            calculator = new VelocityCalculator();
            feeder = new Feeder(hardwareMap);
            intake = new Intake(hardwareMap);
            led = new LED(hardwareMap);
            controller = new GamepadEvents(gamepad1);
            waitForStart();
            while(opModeIsActive())
            {

                if(controller.a.onPress())
                {
                    canShoot = !canShoot;
                }

                if(canShoot && controller.left_bumper.onPress())
                {
                    shooter.setVelocity(calculator.calculateAngularVelocityForTarget(camera.getPosFromTag()));
                    feeder.feed();
                    intake.intakeToggle(-0.8);
                }


                controller.update();
               telemetry.addLine(camera.toString());
               telemetry.addData("Distance to Target",camera.getPosFromTag());
                telemetry.addData("Calculated Linear velocity", calculator.calculateVelocityForTarget(camera.getPosFromTag()));
                telemetry.addData("Calculated angular velocity", calculator.calculateAngularVelocityForTarget(camera.getPosFromTag()));
               telemetry.update();

            }
    }
}
