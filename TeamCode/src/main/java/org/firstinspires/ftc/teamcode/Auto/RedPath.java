package org.firstinspires.ftc.teamcode.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "RedPathTest")
public class RedPath extends LinearOpMode {
    private int pathState;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    //Determine all the position by testing it out;
    //starting position
    private final Pose startPose = new Pose(123.54782608695652,122.50434782608696,Math.toRadians(45));
    //Shooting position
    private final Pose shootingPose = new Pose(97.6695652173913,96.83478260869565,Math.toRadians(45));
    //Near side 3 balls

    private final Pose firstLine = new Pose(108.10434782608695,84.31304347826088,Math.toRadians(180));
    private final Pose firstPush = new Pose(130.64347826086959,84.10434782608695,Math.toRadians(180));
    private final Pose firstControl = new Pose(51.547826086956526,80.76521739130435);
    //Middle 3 balls
    private final Pose secondLine = new Pose(109.35652173913044,60.313043478260866,Math.toRadians(180));
    private final Pose secondPush = new Pose(130.8521739130435,60.104347826086965,Math.toRadians(180));
    private final Pose secondControl = new Pose(61.982608695652175,54.469565217391306);
    //Last three balls
    private final Pose thirdLine = new Pose(109.56521739130436,35.686956521739134,Math.toRadians(180));
    private final Pose thirdPush = new Pose(130.43478260869566,35.686956521739134,Math.toRadians(180));
    private final Pose thirdControl = new Pose(63.02608695652174,28.382608695652173);
    private PathChain shoot, firstBall, push1, back1, secondBall, push2, back2, thirdBall, push3, back3;
    public void buildPaths(){
        shoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootingPose))
                .setConstantHeadingInterpolation(Math.toRadians(45))
                .build();

        firstBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, firstLine))
                .setLinearHeadingInterpolation(Math.toRadians(45),Math.toRadians(180))
                .addPath(new BezierLine(firstLine,firstPush))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        back1 = follower.pathBuilder()
                .addPath(new BezierLine(firstPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(45))
                .build();

        secondBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, secondLine))
                .setLinearHeadingInterpolation(Math.toRadians(45),Math.toRadians(180))
                .addPath(new BezierLine(secondLine,secondPush))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        back2 = follower.pathBuilder()
                .addPath(new BezierLine(secondPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(45))
                .build();

        thirdBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, thirdLine))
                .setLinearHeadingInterpolation(Math.toRadians(45),Math.toRadians(180))
                .addPath(new BezierLine(thirdLine,thirdPush))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        back3 = follower.pathBuilder()
                .addPath(new BezierLine(thirdPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(45))
                .build();
    }

    @Override
    public void runOpMode() {
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
                telemetry.addData("x", follower.getPose().getX());
                telemetry.addData("y", follower.getPose().getY());
                telemetry.addData("heading", Math.toDegrees(follower.getPose().getHeading()));
                telemetry.update();
            }
        }


    }
    public void autonomousPathupdate(){
        switch (pathState){
            case 0:
                follower.followPath(shoot);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(firstBall);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(back1);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(secondBall);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(back2);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    follower.followPath(thirdBall);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    follower.followPath(back3);
                    setPathState(7);
                }
                break;

        }
    }




    public void setPathState(int pState) {
        pathState = pState;
        if (pathTimer != null) {
            pathTimer.resetTimer();
        }
    }
}