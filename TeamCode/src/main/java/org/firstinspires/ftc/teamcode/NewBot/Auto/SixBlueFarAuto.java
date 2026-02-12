package org.firstinspires.ftc.teamcode.NewBot.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeCameraServo;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeIntake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeLED;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeMecanum;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeOutake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeStopper;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeTransfer;
import org.firstinspires.ftc.teamcode.NewBot.Vision.LogitechCam;
import org.firstinspires.ftc.teamcode.NewBot.pedroPathing.Constants;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous(name = "Six Blue Far Auto")
public class SixBlueFarAuto extends LinearOpMode {
    private final double v = 1400;
    int TARGET_TAG_ID = 20;
    LeMecanum drive;
    LogitechCam camera;
    LeLED led;
    double bearing = -1000000;
    double WEB_CAM_OFFSET = 6.0;
    private int pathState;
    private Follower follower;
    double bearingDeg;
    LeTransfer transfer;
    LeOutake flywheel;
    LeIntake intake;
    ElapsedTime time;
    LeStopper stopper;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private final Pose startPose = new Pose(63.23478260869565,8.13913043478261,Math.toRadians(90));
    //Shooting position
    private final Pose shootingPose = new Pose(63.23478260869565,18.365217391304338,Math.toRadians(106));
    private final Pose squareZone = new Pose(8.5,23.37391304347826,Math.toRadians(90));
    private final Pose squareZonePush = new Pose(8.5,8.1,Math.toRadians(90));
    private final Pose squareZone2 = new Pose(8.5,21,Math.toRadians(45));
    private final Pose squareZone2Push = new Pose(8.2,9.5,Math.toRadians(45));
    private final Pose squareZone2Control = new Pose(8.2,8.0, Math.toRadians(0));
    private final Pose squareZone2Control2 = new Pose(10,8.0, Math.toRadians(0));


    private final Pose thirdLine = new Pose(42.321739130434786,33,Math.toRadians(0));
    private final Pose thirdPush = new Pose(7.6,33,Math.toRadians(0));
    private final Pose park = new Pose(30,8.13913043478261, Math.toRadians(90));
    private PathChain shoot, firstBall, firstBall2, back1, secondBall, push2, back2, parking;
    public void buildPaths(){
        shoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(90),Math.toRadians(106))
                .build();

        firstBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, squareZone))
                .setLinearHeadingInterpolation(Math.toRadians(106),Math.toRadians(90))
                .addPath(new BezierLine(squareZone,squareZonePush))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        firstBall2 = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, squareZone2))
                .setLinearHeadingInterpolation(Math.toRadians(106),Math.toRadians(45))
                .addPath(new BezierLine(squareZone2,squareZone2Push))
                .setConstantHeadingInterpolation(Math.toRadians(45))
                .addPath(new BezierLine(squareZone2Push,squareZone2Control))
                .setLinearHeadingInterpolation(Math.toRadians(45),Math.toRadians(0))
                .addPath(new BezierLine(squareZone2Control,squareZone2Control2))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(new BezierLine(squareZone2Control2,squareZone2Control))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        back1 = follower.pathBuilder()
                .addPath(new BezierLine(squareZone2Control, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(90),Math.toRadians(106))
                .build();

        secondBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, thirdLine))
                .setLinearHeadingInterpolation(Math.toRadians(106),Math.toRadians(0))
                .addPath(new BezierLine(thirdLine,thirdPush))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        back2 = follower.pathBuilder()
                .addPath(new BezierLine(thirdPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(106))
                .build();

        parking = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, park))
                .setLinearHeadingInterpolation(Math.toRadians(106),Math.toRadians(90))
                .build();

    }

    @Override
    public void runOpMode() {
        pathTimer = new Timer();
        actionTimer = new Timer();
        opmodeTimer = new Timer();
        camera = new LogitechCam();
        led = new LeLED(hardwareMap);
        drive = new LeMecanum(hardwareMap);
        camera.init(hardwareMap, telemetry);
        flywheel = new LeOutake(hardwareMap);
        stopper = new LeStopper(hardwareMap);
        transfer = new LeTransfer(hardwareMap);
        intake = new LeIntake(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);

        telemetry.addData("Status", "Initialization Complete");
        telemetry.addData("Start Pose", startPose);
        telemetry.update();

        follower.startTeleopDrive();
        // Standard LinearOpMode method to wait for the Start button
        waitForStart();

        // 4. Main Autonomous Execution Loop (Replaces your old 'start' and 'loop' methods)
        if (opModeIsActive()) {
            opmodeTimer.resetTimer();
            setPathState(0); // Start the path sequence

            while (opModeIsActive() && !isStopRequested()) {
                // Update Follower (must be done in every loop iteration)
                follower.update();
                camera.update();
                // State machine to execute paths and actions
                autonomousPathupdate();

                // Telemetry
                telemetry.addData("x", follower.getPose().getX());
                telemetry.addData("y", follower.getPose().getY());
                telemetry.addData("velocity", flywheel.getVelocity());
                telemetry.addData("Bearing", bearing);
                telemetry.update();
            }
        }


    }
    public void autonomousPathupdate() {
        switch (pathState) {

            case 0:
                stopper.block();
                flywheel.setVelocity(v);
                follower.followPath(shoot);
                transfer.stop();
                intake.feed();
                stopper.block();
                actionTimer.resetTimer();
                setPathState(1);
                break;


            case 1:
                if(!follower.isBusy()) {
                    sleep(2000);
                    stopper.lift();
                    intake.feed();
                    transfer.setPower();
                    sleep(3000);
                    setPathState(13);
                }
                break;

            case 13:
                if(!follower.isBusy()) {
                    stopper.block();
                    setPathState(2);
                }
                break;

            case 2:
                if (!follower.isBusy()) {
                        stopper.block();
                        follower.followPath(firstBall);
                        follower.setMaxPower(0.6);
                    setPathState(3);
                }
                break;

            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(back1);
                    follower.setMaxPower(1);
                    setPathState(4);
                }
                break;

            case 4:
                if (!follower.isBusy()) {
                    stopper.lift();
                    sleep(2000);
                    setPathState(8);
                }
                break;

            case 14:
                if(!follower.isBusy()) {
                    stopper.block();
                    follower.followPath(firstBall);
                    follower.setMaxPower(0.5);
                    setPathState(19);
                }
                break;

            case 19:
                if(!follower.isBusy()){
                    follower.followPath(back1);
                    follower.setMaxPower(1);
                    setPathState(17);
                }

            case 17:
                if(!follower.isBusy()){
                    stopper.lift();
                    sleep(2000);
                    setPathState(8);
                }

            case 8:
                if (!follower.isBusy()) {
                    flywheel.setVelocity(0);
                    intake.stop();
                    transfer.stop();
                    follower.followPath(parking);
                    setPathState(9);
                }
                break;
        }
    }


    public void setPathState(int pState)
    {
        pathState = pState;
        if (pathTimer != null)
        {
            pathTimer.resetTimer();
        }
    }


    public void autoAlign(){
        camera.update();
        AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);
        if(targetTag == null){
            led.setleftLEDColor(LeLED.Colors.BLUE);
            return;
        }


        led.setleftLEDColor(LeLED.Colors.PINK);
        bearingDeg = camera.getBearing(targetTag);
        bearing = bearingDeg;

        // Rotatating toward AprilTag while driver drives or strafes
        if (bearingDeg > 1) {
            camera.update();
            drive.drive(0,0,-0.3);
        }
        if (bearingDeg < -1) {
            camera.update();
            drive.drive(0,0,0.3);
        }

        if(bearingDeg < 1 && bearingDeg > -1){
            camera.update();
            drive.drive(0,0,0);
        }
    }

    public boolean isAligned()
    {
        //figure out verification
        return true;
    }
}
