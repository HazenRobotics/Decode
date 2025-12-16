package org.firstinspires.ftc.teamcode.Testing;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.Vision.LogitechCam;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.utils.VelocityCalculator;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
@TeleOp(group = "Tester", name = "VelocityCalculatorTest")
public class VelocityCalculatorTest extends LinearOpMode {
    Shooter shooter;
    VelocityCalculator calculator;
    LogitechCam camera;
    GamepadEvents controller;
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
                    shooter.setVelocity(calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
                }

                camera.update();

                telemetry.addData("Horizontal Distance to AprilTag", camera.getHorizontalData(targetTag));
                telemetry.addData("Calculated velocity", calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
                telemetry.addData("Can shoot", canShoot);
                telemetry.update();
                controller.update();
            }


    }
}
