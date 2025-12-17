package org.firstinspires.ftc.teamcode.OldBots.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.VelocityCalculator;
import org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision.LogitechCam;
import org.firstinspires.ftc.teamcode.OldBots.Robots.V2;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.ColorSensor;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.teamcode.OldBots.pedroPathing.Constants;

@TeleOp(group = "A", name = "V2RedTeleop")
public class V2RedTeleOP extends LinearOpMode {
    ColorSensor colorSensor;
    private V2 robot;
    private GamepadEvents controller1, controller2;
    private Shooter shooter;

    // Pedro + Vision
    private Follower follower;
    private LogitechCam vision;

    // Auto Align config
    private static final int TARGET_TAG_ID = 24;
    private static final double ALIGN_P = 0.015;      // degree-based
    private static final double ALIGN_TOLERANCE = 1.0;
    private VelocityCalculator calculator;

    @Override
    public void runOpMode() throws InterruptedException {

        shooter = new Shooter(hardwareMap, "shooter", true);
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);
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

            // ===================== AUTO-ALIGN =====================
            vision.update();



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
            telemetry.addLine("Controller1 - X (far): toggle distance");
            telemetry.addData("Far mode", far);
            telemetry.addLine("Controller2 - X: multiplyRPM");
            telemetry.addLine("Controller2 - Y: reverseIntake");
            telemetry.addLine("Controller2 - A: shoot -1");
            telemetry.addLine("Controller2 - DPad Up: +RPM");
            telemetry.addLine("Controller2 - DPad Down: -RPM");

            if (targetTag != null)
            {
                vision.disPlayDetectionTelementry(targetTag);
            }

            telemetry.update();

            // ===================== PEDRO UPDATE =====================
            follower.update();
            idle();
        }
    }
}