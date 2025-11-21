package org.firstinspires.ftc.teamcode.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.paths.PathConstraints;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "BluePedroAuto")
public class BluePedroAuto extends LinearOpMode {
    private int pathState;
    private int shootState;
   Feeder feeder;
   Shooter shooter;
   Intake intake;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    //Determine all the position by testing it out;
    //starting position
    private final Pose startPose = new Pose(19.617391304347827,122.71304347826086,Math.toRadians(135));
    //Shooting position
    private final Pose shootingPose = new Pose(46.33043478260869,96.83478260869565,Math.toRadians(135));
    //Near side 3 balls

    private final Pose firstLine = new Pose(40.48695652173913,84.31304347826088,Math.toRadians(0));
    private final Pose firstPush = new Pose(18.782608695652172,84.10434782608695,Math.toRadians(0));
    private final Pose firstControl = new Pose(51.547826086956526,80.76521739130435);
    //Middle 3 balls
    private final Pose secondLine = new Pose(40.904347826086955,60.313043478260866,Math.toRadians(0));
    private final Pose secondPush = new Pose(18.782608695652172,60.104347826086965,Math.toRadians(0));
    private final Pose secondControl = new Pose(61.982608695652175,54.469565217391306);
    //Last three balls
    private final Pose thirdLine = new Pose(41.321739130434786,35.686956521739134,Math.toRadians(0));
    private final Pose thirdPush = new Pose(18.782608695652172,35.686956521739134,Math.toRadians(0));
    private final Pose thirdControl = new Pose(63.02608695652174,28.382608695652173);
    private PathChain shoot, firstBall, push1, back1, secondBall, push2, back2, thirdBall, push3, back3;
    public void buildPaths(){
        shoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootingPose))
                .setConstantHeadingInterpolation(Math.toRadians(135))
                .build();

        firstBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, firstLine))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(0))
                .addPath(new BezierLine(firstLine,firstPush))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        back1 = follower.pathBuilder()
                .addPath(new BezierLine(firstPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(135))
                .build();

        secondBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, secondLine))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(0))
                .addPath(new BezierLine(secondLine,secondPush))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        back2 = follower.pathBuilder()
                .addPath(new BezierLine(secondPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(135))
                .build();

        thirdBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, thirdLine))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(0))
                .addPath(new BezierLine(thirdLine,thirdPush))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        back3 = follower.pathBuilder()
                .addPath(new BezierLine(thirdPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(135))
                .build();
    }

    @Override
    public void runOpMode() {
        feeder = new Feeder(hardwareMap);
        shooter = new Shooter(hardwareMap, "shooter");
        intake = new Intake(hardwareMap,"intake");
        pathTimer = new Timer();
        actionTimer = new Timer();
        opmodeTimer = new Timer();

        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);

        telemetry.addData("Status", "Initialization Complete");
        telemetry.addData("Start Pose", startPose);
        telemetry.update();

        // Standard LinearOpMode method to wait for the Start button
        waitForStart();

        // 4. Main Autonomous Execution Loop (Replaces your old 'start' and 'loop' methods)
        if (opModeIsActive()) {
            opmodeTimer.resetTimer();
            setPathState(0); // Start the path sequence

            while (opModeIsActive() && !isStopRequested()) {
                // Update Follower (must be done in every loop iteration)
                follower.update();

                // State machine to execute paths and actions
                autonomousPathupdate();

                // Telemetry
                telemetry.addData("path state", pathState);
                telemetry.addData("x", follower.getPose().getX());
                telemetry.addData("y", follower.getPose().getY());
                telemetry.addData("heading", Math.toDegrees(follower.getPose().getHeading()));
                telemetry.update();
            }
        }


    }
//    public void autonomousPathupdate(){
//        switch (pathState){
//            case 0:
//                follower.followPath(shoot);
//                setPathState(1);
//                break;
//            case 1:
//                if (!follower.isBusy()) {
//                    follower.followPath(firstBall);
//                    setPathState(2);
//                }
//                break;
//            case 2:
//                if (!follower.isBusy()) {
//                    follower.followPath(back1);
//                    setPathState(3);
//                }
//                break;
//            case 3:
//                if (!follower.isBusy()) {
//                    follower.followPath(secondBall);
//                    setPathState(4);
//                }
//                break;
//            case 4:
//                if (!follower.isBusy()) {
//                    follower.followPath(back2);
//                    setPathState(5);
//                }
//                break;
//            case 5:
//                if (!follower.isBusy()) {
//                    follower.followPath(thirdBall);
//                    setPathState(6);
//                }
//                break;
//            case 6:
//                if (!follower.isBusy()) {
//                    follower.followPath(back3);
//                    setPathState(7);
//                }
//                break;
//
//        }
//    }

    public void autonomousPathupdate() {

        // ALWAYS update intake default
        // (we turn it OFF manually during shooting states)
        if (pathState < 100) {
            // running by default unless shooting
        }

        switch (pathState) {

            // =====================================================
            //   0 — START: spin shooter & reverse feeder
            // =====================================================
            case 0:
                intake.setPower(0);
                shooter.setVelocity(1050);
                feeder.reverseFeed();
                follower.followPath(shoot);
                shootState = 0;   // reset shooting cycle
                pathState = 1;
                break;


            // =====================================================
            //  1 — SHOOT PRELOAD (3 rings)
            // =====================================================
            case 1:
                intake.setPower(-0.8);
                if (shootThreeBallVelocity()) {
                    pathState = 10;
                }
                break;


            // =====================================================
            //  10 — DRIVE TO LINE 1
            // =====================================================
            case 10:
                intake.setPower(-0.8);
                if (!follower.isBusy()) {
                    follower.followPath(firstBall);
                    pathState = 11;
                }
                break;

            // =====================================================
            //  11 — RETURN FROM LINE 1 TO SHOOTING
            // =====================================================
            case 11:
                if (!follower.isBusy()) {
                    intake.setPower(0);
                    shooter.setVelocity(1050);
                    feeder.reverseFeed();
                    follower.followPath(back1);
                    shootState = 0;
                    pathState = 12;
                }
                break;

            // =====================================================
            //  12 — SHOOT 3 FROM LINE 1
            // =====================================================
            case 12:
                intake.setPower(-0.8);
                if (shootThreeBallVelocity()) {
                    pathState = 20;
                }
                break;


            // =====================================================
            //  20 — DRIVE TO LINE 2
            // =====================================================
            case 20:
                intake.setPower(-0.8);
                if (!follower.isBusy()) {
                    follower.followPath(secondBall);
                    pathState = 21;
                }
                break;

            // =====================================================
            //  21 — RETURN FROM LINE 2
            // =====================================================
            case 21:
                if (!follower.isBusy()) {
                    intake.setPower(0);
                    shooter.setVelocity(1050);
                    feeder.reverseFeed();
                    follower.followPath(back2);
                    shootState = 0;
                    pathState = 22;
                }
                break;

            // =====================================================
            //  22 — SHOOT 3 FROM LINE 2
            // =====================================================
            case 22:
                intake.setPower(-0.8);
                if (shootThreeBallVelocity()) {
                    pathState = 30;
                }
                break;


            // =====================================================
            //  30 — DRIVE TO LINE 3
            // =====================================================
            case 30:
                intake.setPower(-0.8);
                if (!follower.isBusy()) {
                    follower.followPath(thirdBall);
                    pathState = 31;
                }
                break;

            // =====================================================
            //  31 — RETURN FROM LINE 3
            // =====================================================
            case 31:
                if (!follower.isBusy()) {
                    intake.setPower(0);
                    shooter.setVelocity(1050);
                    feeder.reverseFeed();
                    follower.followPath(back3);
                    shootState = 0;
                    pathState = 32;
                }
                break;

            // =====================================================
            //  32 — SHOOT 3 FROM LINE 3
            // =====================================================
            case 32:
                intake.setPower(-0.8);
                if (shootThreeBallVelocity()) {
                    shooter.setVelocity(0);
                    feeder.reset();
                    intake.setPower(0);
                    pathState = 100;
                }
                break;


            // =====================================================
            //  100 — END
            // =====================================================
            case 100:
                // do nothing
                break;
        }
    }

    public boolean shootThreeBallVelocity() {

        shooter.setVelocity(1050);

        switch (shootState) {

            case 0:
                feeder.reverseFeed();
                if (shooter.getVelocity() > 1000)
                { feeder.feed(); shootState = 1; }
                break;

            case 1:
                if (shooter.getVelocity() < 800)
                { feeder.reverseFeed(); shootState = 2; }
                break;

            case 2:
                if (shooter.getVelocity() > 1000)
                { feeder.feed(); shootState = 3; }
                break;

            case 3:
                if (shooter.getVelocity() < 800)
                { feeder.reverseFeed(); shootState = 4; }
                break;

            case 4:
                if (shooter.getVelocity() > 1000)
                { feeder.feed(); shootState = 5; }
                break;

            case 5:
                if (shooter.getVelocity() < 800) {
                    feeder.reverseFeed();
                    shootState = 0;
                    return true;
                }
                break;
        }

        return false;
    }




    public void setPathState(int pState) {
        pathState = pState;
        if (pathTimer != null) {
            pathTimer.resetTimer();
        }
    }
}
