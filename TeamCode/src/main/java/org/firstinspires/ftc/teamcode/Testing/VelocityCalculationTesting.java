//package org.firstinspires.ftc.teamcode.Testing;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.teamcode.Vision.LogitechCam;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//
//import com.qualcomm.ftccommon.SoundPlayer;
//import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
//
//import org.firstinspires.ftc.teamcode.Robots.StarterRobot;
//import org.firstinspires.ftc.teamcode.Robots.V2;
//import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
//import org.firstinspires.ftc.teamcode.utils.LEDLights;
//
//public class VelocityCalculationTesting extends LinearOpMode{
//    private static final int TARGET_TAG_ID = 20;
//    private LogitechCam visionSystem;
//    @Override
//    public void runOpMode() throws InterruptedException {
//        // --- 1. Initialization ---
//        telemetry.addData("Status", "Initializing...");
//        telemetry.update();
//
//
//        // Initialize the Vision System
//        visionSystem = new LogitechCam();
//        visionSystem.init(hardwareMap, telemetry);
//
//        telemetry.addData("Status", "Initialized. Waiting for Start.");
//        telemetry.addData("Target Tag ID", TARGET_TAG_ID);
//        telemetry.update();
//
//        // Wait for the game to start (driver presses PLAY)
//        waitForStart();
//
//        // Check if stop was requested after initialization (e.g., driver pressed STOP after INIT)
//        if (isStopRequested()) return;
//
//        // Enable TeleOp drive mode in Pedro Pathing
//        follower.startTeleopDrive();
//
//        // --- 2. Main Loop ---
//        while (opModeIsActive()) {
//
//            visionSystem.update();
//
//            AprilTagDetection targetTag = visionSystem.getTagBySpecificId(TARGET_TAG_ID);
//            if(controller1.left_bumper.onPress())
//            {
//                robot.shoot(VelocityCalculator.CalculateVelocityForTarget(targetTag.ftcPose.x));
//        }
//}