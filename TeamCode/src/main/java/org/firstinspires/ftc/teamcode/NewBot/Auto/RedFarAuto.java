package org.firstinspires.ftc.teamcode.NewBot.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeIntake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeOutake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeStopper;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeTransfer;
import org.firstinspires.ftc.teamcode.NewBot.pedroPathing.Constants;
@Autonomous(name = "Red Far Auto")
public class RedFarAuto extends LinearOpMode {
    private int pathState;
    private Follower follower;
    private double v = 1550;
    private Timer pathTimer, actionTimer, opmodeTimer;
    //Determine all the position by testing it out;
    //starting position
    private final Pose startPose = new Pose(63.23478260869565,8.13913043478261,Math.toRadians(90));
    //Shooting position
    private final Pose shootingPose = new Pose(64.608695652173914,23.79130434782609,-Math.toRadians(112));
    //Near side 3 balls
    private final Pose squareZone = new Pose(121.2521739,23.37391304347826,Math.toRadians(90));
    private final Pose squareZonePush = new Pose(121.6695652,5.008695652173916,Math.toRadians(90));
    //Last three balls
    private final Pose thirdLine = new Pose(158.6086956,35.686956521739134,Math.toRadians(0));
    private final Pose thirdPush = new Pose(184.9043478,35.686956521739134,Math.toRadians(0));
    private final Pose park = new Pose(137.5304348,39.8608695652174, Math.toRadians(90));
    private PathChain shoot, firstBall, push1, back1, secondBall, push2, back2, parking;
    LeTransfer transfer;
    LeOutake flywheel;
    LeIntake intake;
    LeStopper stopper;
    public void buildPaths(){
        shoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(90),Math.toRadians(112))
                .build();

        firstBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, squareZone))
                .setLinearHeadingInterpolation(Math.toRadians(112),Math.toRadians(90))
                .addPath(new BezierLine(squareZone,squareZonePush))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        back1 = follower.pathBuilder()
                .addPath(new BezierLine(squareZonePush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(90),Math.toRadians(112))
                .build();

        secondBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, thirdLine))
                .setLinearHeadingInterpolation(Math.toRadians(112),Math.toRadians(0))
                .addPath(new BezierLine(thirdLine,thirdPush))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        back2 = follower.pathBuilder()
                .addPath(new BezierLine(thirdPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(112))
                .build();

        parking = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, park))
                .setLinearHeadingInterpolation(Math.toRadians(112),Math.toRadians(90))
                .build();

    }

    @Override
    public void runOpMode() {
        flywheel = new LeOutake(hardwareMap);
        stopper = new LeStopper(hardwareMap);
        transfer = new LeTransfer(hardwareMap);
        intake = new LeIntake(hardwareMap);
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
                sleep(2000);
                stopper.lift();
                flywheel.setVelocity(v);
                sleep(1000);
                stopper.lift();
                intake.feed();
                flywheel.setVelocity(v);
                transfer.setPower();
                sleep(4000);
                stopper.block();
                setPathState(100);
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
            // Case 3 and 4 depends on the alliance if they need the third line intake
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
                    follower.followPath(firstBall);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy())
                {
                    follower.followPath(back1);
                    setPathState(7);
                }
                break;

            case 7:
                if (!follower.isBusy())
                {
                    follower.followPath(parking);
                    setPathState(100);
                }
                break;
            case 100:
                intake.stop();
                flywheel.setVelocity(0);
                stopper.block();
                transfer.stop();
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

