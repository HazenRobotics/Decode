package org.firstinspires.ftc.teamcode.OldBots.TeleOp;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.VelocityCalculator;
import org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision.LogitechCam;
import org.firstinspires.ftc.teamcode.OldBots.Robots.V2;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.LED;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.ColorSensor;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.teamcode.OldBots.pedroPathing.Constants;

@TeleOp(group = "B", name = "V2RedTeleop")
public class V2RedTeleOP extends LinearOpMode {
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
    private static final double P_GAIN = 0.015;
    private static final double ROT_DEADZONE = 1.0;   // degrees
    private static final double MAX_ALIGN_SPEED = 0.6;

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
        follower.startTeleopDrive();

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
            if (controller1.right_bumper.onPress())
            {
                robot.intake();
            }

            if(controller1.left_bumper.onPress())
            {
                shooter.setVelocity(calculator.calculateAngularVelocityForTarget(vision.getHorizontalData(targetTag)));
            }
            if(shooter.getCurrent() > 2)
            {
                robot.reverseFeed();
            }
            if (controller1.a.onPress())
            {
//
                robot.toggleFeed();
            }



            if(controller1.x.onPress())
            {
                if (targetTag != null && targetTag.metadata != null) {

                    double bearingError = targetTag.ftcPose.bearing;
                    double alignTurn = -bearingError * P_GAIN;

                    alignTurn = Math.max(
                            -MAX_ALIGN_SPEED,
                            Math.min(MAX_ALIGN_SPEED, alignTurn)
                    );

                    // Lock when aligned
                    if (Math.abs(bearingError) < ROT_DEADZONE) {
                        robot.fieldCentricDrive(0,0,0);
                    } else {
                        robot.fieldCentricDrive(0,0, alignTurn);
                    }

                    telemetry.addData("Align", "ACTIVE");
                    telemetry.addData("Bearing (deg)", "%.2f", bearingError);


                } else {
                    telemetry.addData("Align", "Target not visible");
                }
            }



//            if(far){
//                lights.setColor(0);
//            }else{
//                lights.setColor(1);
//            }


            // ===================== DRIVER 2 =====================
            if (controller2.x.onPress())
            {
                robot.multiplyRPM(-1);
            }
            if (controller2.y.onPress())
            {
                robot.reverseIntake();
            }
            if (controller2.a.onPress())
            {
                robot.shoot(-1);
            }
            if (controller2.dpad_up.onPress())
            {
                robot.setRPM(100);
            }
            if (controller2.dpad_down.onPress())
            {
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
