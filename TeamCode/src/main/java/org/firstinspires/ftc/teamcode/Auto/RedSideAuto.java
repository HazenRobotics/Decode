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


public class RedSideAuto extends LinearOpMode {
    GamepadEvents controller1, controller2;
    MecanumDrive drive;
    Feeder feeder;
    Flap flap;
    Intake intake;
    Shooter shooter;
    Transfer transfer;
    StarterRobot robot;
    private final double v = 1900;

    public void runOpMode() throws InterruptedException {
        robot = new StarterRobot(hardwareMap, controller1, controller2);
        drive = new MecanumDrive(hardwareMap);
        feeder = new Feeder(hardwareMap);
        flap = new Flap(hardwareMap, "frontFlap", "backFlap");
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, "leftShooter");
        transfer = new Transfer(hardwareMap);


        telemetry.addLine("Initializing camera...");
        telemetry.update();

        // Pre-start detection loop

        // Start autonomous

                waitForStart();
//                drive.drive(-0.8,0,0);
//                sleep(200);
//                drive.drive(0,0,0.3);
//                sleep(200);
//                drive.drive(0,0,0);
//
//                robot.shoot();
//                ElapsedTime timer = new ElapsedTime();
//                while (opModeIsActive() && timer.seconds() < 4.55) {
//                    robot.updateShooting();
//                }
//
//                robot.load();
//                timer.reset();
//                while (opModeIsActive() && timer.seconds() < 2.65) {
//                    robot.updateLoad();
//                }
//
//                robot.shoot();
//                timer.reset();
//                while (opModeIsActive() && timer.seconds() < 4.55) {
//                    robot.updateShooting();
//                }
//
//                robot.load();
//                timer.reset();
//                while (opModeIsActive() && timer.seconds() < 2.65) {
//                    robot.updateLoad();
//                }
//
//                robot.shoot();
//                timer.reset();
//                while (opModeIsActive() && timer.seconds() < 4.55) {
//                    robot.updateShooting();
//                }
//                shooter.setVelocity(0);
                drive.drive(-1,0 ,0);
                sleep(1000);
                drive.drive(0,0,0);
                telemetry.update();
        while(opModeIsActive()){
        }
    }
}
