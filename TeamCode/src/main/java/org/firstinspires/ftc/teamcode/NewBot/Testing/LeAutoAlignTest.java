package org.firstinspires.ftc.teamcode.NewBot.Testing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeIntake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeLED;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeMecanum;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeOutake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeStopper;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeTransfer;
import org.firstinspires.ftc.teamcode.NewBot.Vision.LogitechCam;
import org.firstinspires.ftc.teamcode.NewBot.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.NewBot.Utils.VelocityCalculator2;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name = "LeAutoAlignOHIOTester", group = "1 TungTungTungTesting")
public class LeAutoAlignTest extends LinearOpMode {
    LeTransfer transfer;
    LeOutake flywheel;
    LeIntake intake;
    LeStopper stopper;
    GamepadEvents controller;
    VelocityCalculator2 calculator;
    private Follower follower;
    private Pose pose = new Pose(0,0,0);
    double WEB_CAM_OFFSET = -6.0;
    boolean canAlign = false;
    boolean autoAlignLocked = false;
    double lockedX = 0;
    double lockedY = 0;
    double lockedHeading = 0;
    double rotation = 0;
    Pose lockedPose = pose;
    LeLED led;
    LogitechCam camera;
    int TARGET_TAG_ID = 20;
    LeMecanum drive;
    boolean canShoot = false;
    double headingTurn = 0;
    @Override
    public void runOpMode() throws InterruptedException
    {
        transfer = new LeTransfer(hardwareMap);
        controller = new GamepadEvents(gamepad1);
        intake = new LeIntake(hardwareMap);
        flywheel = new LeOutake(hardwareMap);
        stopper = new LeStopper(hardwareMap);
        drive = new LeMecanum(hardwareMap);
        calculator = new VelocityCalculator2();
        camera = new LogitechCam();
        camera.init(hardwareMap,telemetry);
        led = new LeLED(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(pose);
        follower.pathBuilder().build();

        waitForStart();
        follower.startTeleopDrive();
        follower.setPose(new Pose(0, 0, 0));
        while(opModeIsActive())
        {

            camera.update();
            AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);

            if(controller.left_bumper.onPress())
            {
                transfer.togglePower();
                intake.feed();
            }

            //Auto Align
            //TODO: GET Distance from AprilTag, and as I move or strafe, rotate to the april tag
            //Use Follower as the pinpoint is built into it
            //(1) Read April Tag, reset dead wheels: Method Name: resetPosAndIMU()
            //(2) As I move away from Apriltag, use some PID to rotate to the apriltag: Mr. Pecks AI Code
            //(3) If I get bumped, and no longer see the april tag, use deadwheel data to get back to original pos
            //  -> getPosX(INCH), getPosY(INCH), getHeading(INCH)

            //IDEA For Structuring Code:
            //(if Aligning & See's April Tag)
            //Then: Reset Deadwheels & Lock Auto-Align
            //(if Aligning & not see April Tag)
            //Read Dead Wheel Data and Adjust




            if (canAlign)
            {

                // April Tag Notice
                if (targetTag != null)
                {

                    led.setColor(LeLED.Colors.PINK);
                    double bearingDeg = camera.getBearing(targetTag);

                    // Rotatating toward AprilTag while driver drives or strafes
                    if (bearingDeg > 5)
                    {

                        drive.drive(0,0, -0.3);
//                        follower.turn(
//                                Math.toRadians(bearingDeg) + WEB_CAM_OFFSET,
//                                false
//                        );
                    }
                    else if (bearingDeg < 4)
                    {
                        drive.drive(0,0, 0.3);
//                        follower.turn(
//                                Math.toRadians(-bearingDeg) - WEB_CAM_OFFSET,
//                                true
//                        );
                    }
                        rotation = 0;

                    telemetry.addData("AutoAlign", "TAG LOCKED");
                    telemetry.addData("Bearing", bearingDeg);


                }else {

                    led.setColor(LeLED.Colors.YELLOW);
                }

            }else
            {
                rotation = 1;
                led.setColor(LeLED.Colors.BLUE);
            }


//            else if (targetTag == null)
//            //Not see April Ta
//            {
//
//                led.setColor(LeLED.Colors.YELLOW);
//
//                //Null pointer exception in this
//                double currentHeading = follower.getPose().getHeading();
//                double targetHeading = lockedPose.getHeading();
//                double headingError = targetHeading - currentHeading;
//
//                //idk, I will try toRadians, as normal degrees had issues with the previous auto-Align
//                if (headingError > Math.toRadians(1))
//                {
//                    follower.turn(Math.toRadians(headingError), false);
//                }
//                else if (headingError < Math.toRadians(1))
//                {
//                    follower.turn(Math.toRadians(-headingError), true);
//                }
//
//                telemetry.addData("AutoAlign", "RECOVERING VIA DEAD WHEELS");
//                telemetry.addData("Heading Error", headingError);
//            }// Auto Align is disabeled
//            else
//            {
//                autoAlignLocked = false;
//                follower.breakFollowing();
//                led.setColor(LeLED.Colors.BLUE);
//            }



            drive.drive(-controller.left_stick_y, controller.left_stick_x, controller.right_stick_x * rotation);




            if(controller.x.onPress())
            {
                transfer.reverseMotor();
            }

            if(controller.y.onPress())
            {
                canAlign = !canAlign;
            }

            if(controller.a.onPress())
            {
                canShoot = !canShoot;
            }

            if(canShoot)
            {
                flywheel.setVelocity(calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
            }

            if(canShoot && targetTag == null)
            {
                flywheel.setVelocity(calculator.setVelocityWhenItDoesNotSeeAPRIlTag());
            }

            if(controller.b.onPress())
            {
                stopper.toggle();
            }


            telemetry.addLine("Left bumper to toggle transfer and feed");
            telemetry.addLine("Press X to reverse transfer");
            telemetry.addLine("Press A to toggle shooting");
            telemetry.addLine("Press Y to Auto Align");
            telemetry.addLine("Press B to toggle stopper");
            telemetry.addLine("DPAD UP to increase velocity\nDPAD DOWN to decrease velocity");
            telemetry.addData("Estimated Velocity: ", calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
            telemetry.addData("Horizontal Distance: ", camera.getHorizontalData(targetTag));
            telemetry.addData("Velocity: ", flywheel.getVelocity());
            telemetry.addData("Transfer Power: ", transfer.getData());
            if (targetTag != null) {
                telemetry.addData("Camera rotation", camera.getBearing(targetTag));
            }

            follower.update();
            telemetry.update();
            controller.update();
        }
    }
}
