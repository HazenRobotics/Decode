package org.firstinspires.ftc.teamcode.OldBots.TeleOp;

import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.NewBot.Utils.VelocityCalculator;
import org.firstinspires.ftc.teamcode.NewBot.Vision.LogitechCam;
import org.firstinspires.ftc.teamcode.OldBots.Robots.V2;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.LED;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.NewBot.Utils.ColorSensor;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.teamcode.OldBots.pedroPathing.Constants;

@TeleOp(group = "B", name = "V2RedTeleop")
public class RedTeleOP extends LinearOpMode {
    ColorSensor colorSensor;
    private V2 robot;
    private GamepadEvents controller1, controller2;
    private Shooter shooter;

    // Pedro + Vision
    private Follower follower;
    private LogitechCam vision;
    private LED led;

    // Auto Align config
    private static final int TARGET_TAG_ID = 24;
    private VelocityCalculator calculator;
    private final Pose startPose = new Pose(0, 0, 0);
    double WEB_CAM_OFFSET = -6.0;
    boolean canAlign = false;


    @Override
    public void runOpMode() throws InterruptedException {

        shooter = new Shooter(hardwareMap, "shooter", true);
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);
        led = new LED(hardwareMap);
        robot = new V2(hardwareMap, controller1, controller2);
        calculator = new VelocityCalculator();
//        lights = new LEDLights[1];
//        lights[0] = new LEDLights(hardwareMap, "led");

        // PedroPathing init
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        // Vision init
        vision = new LogitechCam();
        vision.init(hardwareMap, telemetry);

        boolean far = false;


        telemetry.addLine("V2 Blue TeleOp + Auto Aim Initialized");
        telemetry.addLine("Hold Y to Auto-Align");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            AprilTagDetection targetTag = vision.getTagBySpecificId(TARGET_TAG_ID);

            robot.drive();
            // ===================== AUTO-ALIGN =====================
            vision.update();

            if (targetTag != null) {
                led.setColor(RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN);
            } else {
                led.setColor(RevBlinkinLedDriver.BlinkinPattern.WHITE);
            }

            // ===================== DRIVER 1 =====================
            if (controller1.right_bumper.onPress()) {
                robot.intake();
            }

            if (controller1.left_bumper.onPress()) {
                shooter.setVelocity(calculator.calculateAngularVelocityForTarget(vision.getHorizontalData(targetTag)));
            }
            if (shooter.getCurrent() > 2) {
                robot.reverseFeed();
            }
            if (controller1.a.onPress()) {
                robot.toggleFeed();
            }

            if (controller1.y.onPress()) {
                canAlign = !canAlign;
            }


            if (targetTag != null && canAlign)
            {
                led.setColor(RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN);

                if (vision.getBearing(targetTag) > 1)
                {
                    follower.turn(Math.abs(Math.toRadians(vision.getBearing(targetTag)) + WEB_CAM_OFFSET), false);

                } else if (vision.getBearing(targetTag) < -1)
                {
                    follower.turn(Math.abs(Math.toRadians(vision.getBearing(targetTag)) - WEB_CAM_OFFSET), true);

                    telemetry.addData("Camera Rotation", vision.getBearing(targetTag));
                } else if (canAlign && targetTag == null)
                {
                    follower.turnTo(0);
                } else
                {
                    led.setColor(RevBlinkinLedDriver.BlinkinPattern.WHITE);
                    follower.breakFollowing();
                }
            }

//            if(far){
//                lights.setColor(0);
//            }else{
//                lights.setColor(1);
//            }


                // ===================== DRIVER 2 =====================
                if (controller2.x.onPress()) {
                    robot.multiplyRPM(-1);
                }
                if (controller2.y.onPress()) {
                    robot.reverseIntake();
                }
                if (controller2.a.onPress()) {
                    robot.shoot(-1);
                }
                if (controller2.dpad_up.onPress()) {
                    robot.setRPM(100);
                }
                if (controller2.dpad_down.onPress()) {
                    robot.setRPM(-100);
                }

                //Color Sensor
//            ColorSensor.Color color = colorSensor.getColor();
//
//            switch (color){
//                case Green:
//                    lights[0].setColor(LEDLights.GREEN_WEIGHT);
//
//                    break;
//                case Purple:
//                    lights[0].setColor(LEDLights.PURPLE_WEIGHT);
//
//                    break;
//
//            }
                // ===================== LED =====================
//            if (far) {
//                led.setColor(LEDLights.FAR_WEIGHT);
//            }

                // ===================== UPDATES =====================
                controller1.update();
                controller2.update();
                robot.feederEmoji(telemetry);

                // ===================== TELEMETRY =====================
                telemetry.addData("Shooter Velocity", shooter.getVelocity());
                telemetry.addLine("Controller1 - Right Bumper: intake");
                telemetry.addLine("Controller1 - Left Bumper: shoot toggle");
                telemetry.addLine("Controller1 - A: feed");
                telemetry.addData("Can Align", canAlign);
                telemetry.addData("Target tag", targetTag != null);
                //Fix Driver-Automations to improve driving experience
                telemetry.addLine("Controller1 - Y: Allow Auto-Aligning");
                telemetry.addLine("Controller1 - X: Auto Align");
                telemetry.addData("Far mode", far);
                telemetry.addLine("Controller2 - X: multiplyRPM");
                telemetry.addLine("Controller2 - Y: reverseIntake");
                telemetry.addLine("Controller2 - A: shoot -1");
                telemetry.addLine("Controller2 - DPad Up: +RPM");
                telemetry.addLine("Controller2 - DPad Down: -RPM");


                telemetry.update();

                // ===================== PEDRO UPDATE =====================
                follower.update();
                idle();
            }
        }
    }
