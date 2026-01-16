package org.firstinspires.ftc.teamcode.NewBot.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NewBot.pedroPathing.Constants;
@Autonomous(name = "BluePathTest")
public class BluePath extends LinearOpMode {
    private int pathState;
    private Follower follower;
    //YMultipler affects what it is going vertical in refernce to the field
    //YMultiplier has to be positve
    //160/84
//private double xMultiplier = 1, yMultiplier = 1;
    private Timer pathTimer, actionTimer, opmodeTimer;
    //Determine all the position by testing it out;
    //starting position
    private double xMultiplier = 1, yMultiplier = 1.1;
    private final Pose startPose = new Pose(19.617391304347827* xMultiplier,122.71304347826086 *yMultiplier,Math.toRadians(135));
    //Shooting position
    private final Pose shootingPose = new Pose(46.33043478260869 * xMultiplier,96.83478260869565 *yMultiplier,Math.toRadians(135));
    //Near side 3 balls
    //IDEA Potenially move x back
    private final Pose firstLine = new Pose(40.48695652173913 * xMultiplier,84.31304347826088 *yMultiplier,Math.toRadians(0));
    private final Pose firstPush = new Pose(8.026086956521738 * xMultiplier,84.10434782608695 *yMultiplier,Math.toRadians(0));
    private final Pose firstControl = new Pose(51.547826086956526 * xMultiplier,80.76521739130435 *yMultiplier);
    //Middle 3 balls
    private final Pose secondLine = new Pose(40.904347826086955 * xMultiplier,60.313043478260866 *yMultiplier,Math.toRadians(0));
    private final Pose secondPush = new Pose(5.026086956521738 * xMultiplier,60.104347826086965 *yMultiplier,Math.toRadians(0));
    private final Pose secondControl = new Pose(61.982608695652175 * xMultiplier,54.469565217391306 *yMultiplier);
    //Last three balls
    private final Pose thirdLine = new Pose(41.321739130434786 * xMultiplier,35.686956521739134 *yMultiplier,Math.toRadians(0));
    private final Pose thirdPush = new Pose(5.026086956521738 * xMultiplier,35.686956521739134 *yMultiplier,Math.toRadians(0));
    private final Pose thirdControl = new Pose(63.02608695652174 * xMultiplier,28.382608695652173 *yMultiplier);
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
        pathTimer = new Timer();
        actionTimer = new Timer();
        opmodeTimer = new Timer();

        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);

        telemetry.addData("Status", "Initialization Complete");
        telemetry.addData("Start Pose", shootingPose);
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
                if (!follower.isBusy())
                {
                    follower.followPath(firstBall);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy())
                {
                    follower.followPath(back1);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy())
                {
                    follower.followPath(secondBall);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy())
                {
                    follower.followPath(back2);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy())
                {
                    follower.followPath(thirdBall);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy())
                {
                    follower.followPath(back3);
                    setPathState(7);
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
}
