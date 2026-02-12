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
@Autonomous(name = "Six Red Far Auto")
public class SixREDFarAuto extends LinearOpMode {
    private int pathState;
    private Follower follower;
    private double v = 1400;
    private Timer pathTimer, actionTimer, opmodeTimer;
    //Determine all the position by testing it out;
    private final Pose startPose = new Pose(78.67,8.13913043478261,Math.toRadians(90));
    //Shooting position
    private final Pose shootingPose = new Pose(78.67826686956522,18.365217391304338,Math.toRadians(66));
    private final Pose squareZone = new Pose(132.3,23.37391304347826,Math.toRadians(90));
    private final Pose squareZonePush = new Pose(132.3,8.5,Math.toRadians(90));
    private final Pose squareZone2 = new Pose(130,22,Math.toRadians(135));
    private final Pose squareZone2Push = new Pose(130,13.7,Math.toRadians(135));
    private final Pose squareZone2Control = new Pose(129,12, Math.toRadians(90));
    private final Pose squareZone2Control2 = new Pose(126.5,12, Math.toRadians(180));


    private final Pose thirdLine = new Pose(99.58,40,Math.toRadians(180));
    private final Pose thirdPush = new Pose(131,40,Math.toRadians(180));
    private final Pose park = new Pose(78.67,8.13913043478261, Math.toRadians(90));
    private PathChain shoot, firstBall2, push1, back1, secondBall, push2, back2, parking;
    LeTransfer transfer;
    LeOutake flywheel;
    LeIntake intake;
    LeStopper stopper;
    public void buildPaths(){
        shoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(90),Math.toRadians(66))
                .build();


        firstBall2 = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, squareZone2))
                .setLinearHeadingInterpolation(Math.toRadians(66),Math.toRadians(135))
                .addPath(new BezierLine(squareZone2,squareZone2Push))
                .setConstantHeadingInterpolation(Math.toRadians(135))
                .addPath(new BezierLine(squareZone2Push,squareZone2Control))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(90))
//                .addPath(new BezierLine(squareZone2Control,squareZone2Control2))
//                .setConstantHeadingInterpolation(Math.toRadians(180))
//                .addPath(new BezierLine(squareZone2Control2,squareZone2Control))
//                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        back1 = follower.pathBuilder()
                .addPath(new BezierLine(squareZone2Control, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(90),Math.toRadians(66))
                .build();

        secondBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, thirdLine))
                .setLinearHeadingInterpolation(Math.toRadians(66),Math.toRadians(180))
                .addPath(new BezierLine(thirdLine,thirdPush))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        back2 = follower.pathBuilder()
                .addPath(new BezierLine(thirdPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(180),Math.toRadians(66))
                .build();

        parking = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, park))
                .setLinearHeadingInterpolation(Math.toRadians(66),Math.toRadians(90))
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
                //Potenial issue may be you reset actionTimer, and then then skips
                if (actionTimer.getElapsedTimeSeconds() > 1.5)
                {
                    stopper.lift();
                    transfer.setPower();
                    setPathState(13);
                }
                break;

            case 13:
                //Potenial issue may be you rsert actionTimer, and then then skips
                if (actionTimer.getElapsedTimeSeconds() > 3.4) {
                    stopper.block();
                    setPathState(2);
                }
                break;

            case 2:
                if (!follower.isBusy())
                {
                        stopper.block();
                        //Maybe for some reason the intake and transfer turn off
                        //I added two lines of code - Armaan
//                        intake.feed();
//                        transfer.setMaxPower();
                        follower.followPath(firstBall2);
                        follower.setMaxPower(0.5);
                    setPathState(3);
                }
                break;

            case 3:
                if (!follower.isBusy()) {
                    intake.feed();
                    transfer.setMaxPower();
                    follower.followPath(back1);
                    follower.setMaxPower(1);
                    setPathState(4);
                }
                break;

            case 4:
                if (!follower.isBusy()) {
                    stopper.lift();
                    sleep(2000);
                    setPathState(14);
                }
                break;

            case 14:
                if(!follower.isBusy()) {
                    stopper.block();
                    follower.followPath(firstBall2);
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
                    follower.setMaxPower(1);
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
}