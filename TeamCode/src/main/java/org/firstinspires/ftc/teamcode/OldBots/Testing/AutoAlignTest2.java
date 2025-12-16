package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Vision.LogitechCam;
import org.firstinspires.ftc.teamcode.OldBots.pedroPathing.Constants;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

//TODO: Limit Y Distance to be between 100-110
//TODO: Constain X Distance to be >4, maybe try PID to position to 10
@TeleOp(name = "AutoAlignTest", group = "Tester")
public class AutoAlignTest2 extends LinearOpMode {

    // --- Configuration ---
    private static final int TARGET_TAG_ID = 20; // Change this to the AprilTag ID you want to align to
    private static final double ALIGNMENT_P_GAIN = 0.015; // Proportional gain for turning power (vOmega)
    private static final double ALIGNMENT_TOLERANCE_DEG = 1.0; // Stop turning when yaw is within this range (in degrees)
    // ---------------------

    // Components
    private LogitechCam visionSystem;
    private Follower follower; // Pedro Pathing Follower

    @Override
    public void runOpMode() throws InterruptedException {
        // --- 1. Initialization ---
        telemetry.addData("Status", "Initializing...");
        telemetry.update();
        follower = Constants.createFollower(hardwareMap);

        // Initialize the Vision System
        visionSystem = new LogitechCam();
        visionSystem.init(hardwareMap, telemetry);

        telemetry.addData("Status", "Initialized. Waiting for Start.");
        telemetry.addData("Target Tag ID", TARGET_TAG_ID);
        telemetry.addData("Control", "Hold Left Bumper (GP1) for Auto-Align");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Check if stop was requested after initialization (e.g., driver pressed STOP after INIT)
        if (isStopRequested()) return;

        // Enable TeleOp drive mode in Pedro Pathing
        follower.startTeleopDrive();

        // --- 2. Main Loop ---
        while (opModeIsActive()) {

            follower.update();
            visionSystem.update();

            AprilTagDetection targetTag = visionSystem.getTagBySpecificId(TARGET_TAG_ID);

            //Alignment Logic Check
            boolean requestedAlignment = gamepad1.left_bumper;
            boolean isAutoAligning = requestedAlignment;

            // --- Drive Control ---
            if (isAutoAligning && targetTag != null) {
                // --- Auto-Alignment Mode ---

                // ftcPose.yaw is the error in degrees from the camera's perspective.
                double yawErrorDeg = targetTag.ftcPose.yaw;
                double yawErrorRad = AngleUnit.DEGREES.toRadians(yawErrorDeg);

                visionSystem.disPlayDetectionTelementry(targetTag);
                telemetry.addData("Alignment Status", "TARGETING TAG");
                telemetry.addData("Yaw Error (Deg)", String.format("%.2f", yawErrorDeg));

                //Have robot also move the x-position 10, and y-position for 105
                Pose currentLine = new Pose(targetTag.ftcPose.x,targetTag.ftcPose.y);
                Pose newLine = new Pose(10,105);
                follower.pathBuilder()
                        .addPath(new BezierLine(currentLine, newLine))
                        .build();

                if (Math.abs(yawErrorDeg) < ALIGNMENT_TOLERANCE_DEG) {


                    // If the robot is aligned, stop all movement
                    follower.setTeleOpDrive(0, 0, 0, true);
                    telemetry.addData("Alignment Status", "ALIGNED");
                } else {
                    // Calculating angular velocity (vOmega) using a simple P-controller
                    double vOmega = ALIGNMENT_P_GAIN * yawErrorRad;

                    // A minimum power to prevent stalling near the target
                    if (Math.abs(vOmega) < 0.1) {
                        vOmega = Math.copySign(0.1, vOmega);
                    }

                    // Applying drive power (0 translational, calculated rotational)
                    // Using robot centric control (last parameter true) for simple rotation
                    follower.setTeleOpDrive(0, 0, vOmega, true);
                    telemetry.addData("vOmega Applied", String.format("%.3f", vOmega));
                }

            } else if (isAutoAligning && targetTag == null) {
                // --- Auto-Aligning but Tag Lost ---
                follower.setTeleOpDrive(0, 0, 0, true);
                telemetry.addData("Alignment Status", "⚠️ TAG LOST: Stopping drive");
                telemetry.addData("Instruction", "Release/Re-Press L-Bumper to retry or drive manually.");

            } else {
                // --- Manual TeleOp Drive Mode (Pedro Pathing Default) ---

                // This is the default TeleOp control from the Pedro Pathing example.
                follower.setTeleOpDrive(
                        -gamepad1.left_stick_y, // Forward/Backward
                        -gamepad1.left_stick_x, // Strafe
                        -gamepad1.right_stick_x, // Turn
                        true // Robot Centric
                );

                telemetry.addData("Alignment Status", "MANUAL CONTROL");
                telemetry.addData("Instruction", "Hold Left Bumper to auto-align to Tag %d", TARGET_TAG_ID);
            }

            if (targetTag != null) {
                visionSystem.disPlayDetectionTelementry(targetTag);
            } else {
                telemetry.addLine("No target AprilTag detected.");
            }

            telemetry.update();

            // Yield control to the system for a short time (important for LinearOpMode)
            sleep(20);
        }

        visionSystem.stop();
        telemetry.addData("Status", "Stopped.");
        telemetry.update();
    }
}