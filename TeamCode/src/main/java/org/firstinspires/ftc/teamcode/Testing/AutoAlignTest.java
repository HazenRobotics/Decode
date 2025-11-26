package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Vision.LogitechCam;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

@TeleOp(name = "AutoAlign AprilTag (Linear)", group = "Vision")
public class AutoAlignTest extends LinearOpMode {

    private Follower follower;
    private LogitechCam camera;

    private boolean autoAlign = false;
    private static final int TARGET_ID = 20;

    // ---- PID constants ----
    private double kP = 0.035;
    private double kI = 0.0;
    private double kD = 0.0008;

    private double integral = 0;
    private double lastError = 0;
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        // --------------------- INIT CAMERA ---------------------
        camera = new LogitechCam();
        camera.init(hardwareMap, telemetry);

        // --------------------- INIT PEDROPATH ------------------
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose());
        follower.update();

        telemetry.addLine("Ready to start.");
        telemetry.update();

        waitForStart();
        timer.reset();

        follower.startTeleopDrive();

        while (opModeIsActive()) {

            camera.update();
            AprilTagDetection tag = camera.getTagBySpecificId(TARGET_ID);

            // ----------------------- DRIVER INPUT -----------------------
            double x = -gamepad1.left_stick_y;
            double y = -gamepad1.left_stick_x;
            double turn = -gamepad1.right_stick_x;

            // Toggle auto-align
            if (gamepad1.left_bumper) autoAlign = true;
            if (gamepad1.right_bumper) autoAlign = false;

            if (autoAlign && tag != null) {

                // ------------- Get Tag Bearing (Yaw Error) -------------
                double error = tag.ftcPose.bearing; // degrees

                // ------------- PID compute -------------
                double dt = timer.seconds();
                timer.reset();

                integral += error * dt;
                double derivative = (error - lastError) / dt;
                lastError = error;

                double turnPower =
                        kP * error +
                                kI * integral +
                                kD * derivative;

                // Clamp PID output
                turnPower = Math.max(-0.35, Math.min(0.35, turnPower));

                // Apply only yaw correction
                follower.setTeleOpDrive(0, 0, turnPower, true);

                telemetry.addLine("AUTO ALIGN ACTIVE");
                telemetry.addData("Bearing", "%.2f", tag.ftcPose.bearing);
                telemetry.addData("TurnPower", "%.3f", turnPower);

            } else {

                // ------------- Normal Driver Control -------------
                follower.setTeleOpDrive(x, y, turn, true);

                if (autoAlign && tag == null) {
                    telemetry.addLine("No Tag Found — Cannot Align");
                }
            }

            follower.update();

            telemetry.update();
        }

        camera.stop();
    }
}

