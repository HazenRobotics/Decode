package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot.StarterRobot;
import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Flap;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.SubSystems.Transfer;
import org.firstinspires.ftc.teamcode.Vision.LogitechCam;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous(name = "Red Farside 3 ball launch")
public class RedFarsideCamAuto extends LinearOpMode {
    private ElapsedTime globalTime = new ElapsedTime();
    GamepadEvents controller1, controller2;
    MecanumDrive drive;
    Feeder feeder;
    Flap flap;
    Intake intake;
    Shooter shooter;
    Transfer transfer;
    LogitechCam camera;
    StarterRobot robot;
    private final double v = 1900;
    private LogitechCam interstTag = null;

    public void runOpMode() throws InterruptedException {
        camera = new LogitechCam();
        robot = new StarterRobot(hardwareMap, controller1, controller2);
        camera.init(hardwareMap, telemetry);
        drive = new MecanumDrive(hardwareMap);
        feeder = new Feeder(hardwareMap);
        flap = new Flap(hardwareMap, "frontFlap", "backFlap");
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, "leftShooter");
        transfer = new Transfer(hardwareMap);


        telemetry.addLine("Initializing camera...");
        telemetry.update();

        // Pre-start detection loop
        while (opModeInInit() && !isStopRequested()) {
            camera.update();
            AprilTagDetection blue = camera.getTagBySpecificId(20);
            AprilTagDetection tag21 = camera.getTagBySpecificId(21);
            AprilTagDetection tag22 = camera.getTagBySpecificId(22);
            AprilTagDetection tag23 = camera.getTagBySpecificId(23);
            AprilTagDetection red = camera.getTagBySpecificId(24);

            if (tag21 != null) {
                telemetry.addLine("Detected Tag ID: 21");
            } else if (tag22 != null) {
                telemetry.addLine("Detected Tag ID: 22");
            } else if (tag23 != null) {
                telemetry.addLine("Detected Tag ID: 23");
            }else {
                telemetry.addLine("No tag detected yet...");
            }

            telemetry.update();
        }

        // Start autonomous
        while(opModeIsActive()){
            camera.update();

            // Decide action based on tag
            AprilTagDetection blue = camera.getTagBySpecificId(20);
            AprilTagDetection tag21 = camera.getTagBySpecificId(21);
            AprilTagDetection tag22 = camera.getTagBySpecificId(22);
            AprilTagDetection tag23 = camera.getTagBySpecificId(23);
            AprilTagDetection red = camera.getTagBySpecificId(24);

            if (tag21 != null) {
                telemetry.addLine("Executing path for Tag 21...");
                telemetry.update();
            } else if (tag22 != null) {
                telemetry.addLine("Executing path for Tag 22...");
                waitForStart();

                while(red.ftcPose.y > 275){
                    camera.update();
                    red = camera.getTagBySpecificId(24);
                    drive.drive(-0.8,0,0);
                }
                while(red.ftcPose.yaw < -22){
                    camera.update();
                    red = camera.getTagBySpecificId(24);
                    drive.drive(0,0,0.3);
                }
                drive.drive(0,0,0);

                robot.shoot();
                ElapsedTime timer = new ElapsedTime();
                while (opModeIsActive() && timer.seconds() < 4.55) {
                    robot.updateShooting();
                }

                robot.load();
                timer.reset();
                while (opModeIsActive() && timer.seconds() < 2.65) {
                    robot.updateLoad();
                }

                robot.shoot();
                timer.reset();
                while (opModeIsActive() && timer.seconds() < 4.55) {
                    robot.updateShooting();
                }

                robot.load();
                timer.reset();
                while (opModeIsActive() && timer.seconds() < 2.65) {
                    robot.updateLoad();
                }

                robot.shoot();
                timer.reset();
                while (opModeIsActive() && timer.seconds() < 4.55) {
                    robot.updateShooting();
                }

                shooter.setVelocity(0);
                telemetry.update();
            } else if (tag23 != null) {
                telemetry.addLine("Executing path for Tag 23...");
                telemetry.update();
            } else {
                telemetry.addLine("No tag detected — default action.");
                telemetry.update();
            }
        }
    }
}
