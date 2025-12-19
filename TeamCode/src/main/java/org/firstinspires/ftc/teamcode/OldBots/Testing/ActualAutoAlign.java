package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.VelocityCalculator;
import org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision.LogitechCam;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.LED;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.OldBots.pedroPathing.Constants;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(group = "test", name = "LeAutoAlign")
public class ActualAutoAlign extends LinearOpMode {
    LogitechCam webcam;
    Shooter shooter;
    Feeder feeder;
    Intake intake;
    VelocityCalculator calculator;
    GamepadEvents controller;
    LED led;
    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);
    int TARGET_TAG_ID = 24;
    boolean canAlign = false;
    boolean canShoot = false;
    double WEB_CAM_OFFSET = -6.0;
    @Override
    public void runOpMode() throws InterruptedException
    {
        calculator = new VelocityCalculator();
        webcam = new LogitechCam();
        shooter = new Shooter(hardwareMap);
        feeder = new Feeder(hardwareMap);
        intake = new Intake(hardwareMap);
        led = new LED(hardwareMap);
        follower = Constants.createFollower(hardwareMap);

        controller = new GamepadEvents(gamepad1);
        webcam.init(hardwareMap,telemetry);

        follower.setStartingPose(startPose);
        waitForStart();

        while(opModeIsActive())
        {
            AprilTagDetection targetTag = webcam.getTagBySpecificId(TARGET_TAG_ID);

            if(controller.left_bumper.onPress())
            {
                shooter.setVelocity(calculator.calculateAngularVelocityForTarget(webcam.getHorizontalData(targetTag)));
                intake.setPower(-0.8);
                feeder.feed();
            }

            if(controller.y.onPress())
            {
                canAlign = !canAlign;
            }

            //left is negative
            //right is postive
            if(targetTag != null && canAlign)
            {
                led.setColor(RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN);

                if(webcam.getBearing(targetTag) > 1)
                {
                    follower.turn(Math.abs(Math.toRadians(webcam.getBearing(targetTag)) + WEB_CAM_OFFSET), false);
//                    canShoot = false;

                }else if(webcam.getBearing(targetTag) < -1)
                {
//                    canShoot = false;
                    follower.turn(Math.abs(Math.toRadians(webcam.getBearing(targetTag)) - WEB_CAM_OFFSET), true);
                }
                telemetry.addData("Camera Rotation", webcam.getBearing(targetTag));
            }else
            {
//              canShoot = true;
                led.setColor(RevBlinkinLedDriver.BlinkinPattern.WHITE);
            }

            telemetry.addLine("Press Y to toggle Align");
            telemetry.addLine("Press left bumper to intake and shoot");
            telemetry.addData("Can Align", targetTag != null && canAlign);
            follower.update();
            webcam.update();
            telemetry.update();
            controller.update();

        }
    }
}
