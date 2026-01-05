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
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.NewBot.pedroPathing.Constants;

@Autonomous(name = "Blue Near Auto")

public class BlueNearSideAuto extends LinearOpMode {
    private int pathState;
    private int shootState;

    private final double v = 1050;
    private final double max = 1075;
    private final double min = 1025;
    private final double current = 2;
    private final double breakingStrength = 2.0;
    LeTransfer transfer;
    LeOutake flywheel;
    LeIntake intake;
    LeStopper stopper;
    String isShoot;
    private Follower follower;
    //The Multipliers are there becasue I may have made a mistake in pedro pathing,
    //retune the robot and prob set X and Y multipliers to 1, and see if the path is accurate
    private final double xMultiplier = 1.7, yMultiplier = (double) 44/28;
    private Timer pathTimer, actionTimer, opmodeTimer;
    //Determine all the position by testing it out;
    //starting position
    private final Pose startPose = new Pose(19.617391304347827* xMultiplier,122.71304347826086 *yMultiplier,Math.toRadians(135));
    //Shooting position
    private final Pose shootingPose = new Pose(46.33043478260869 * xMultiplier,96.83478260869565 *yMultiplier,Math.toRadians(135));
    //Near side 3 balls
    private final Pose firstLine = new Pose(40.48695652173913 * xMultiplier,84.31304347826088 *yMultiplier,Math.toRadians(0));
    private final Pose firstPush = new Pose(15.026086956521738 * xMultiplier,84.10434782608695 *yMultiplier,Math.toRadians(0));
    private final Pose firstControl = new Pose(51.547826086956526 * xMultiplier,80.76521739130435 *yMultiplier);
    //Middle 3 balls
    private final Pose secondLine = new Pose(40.904347826086955 * xMultiplier,60.313043478260866 *yMultiplier,Math.toRadians(0));
    private final Pose secondPush = new Pose(15.026086956521738 * xMultiplier,60.104347826086965 *yMultiplier,Math.toRadians(0));
    private final Pose secondControl = new Pose(61.982608695652175 * xMultiplier,54.469565217391306 *yMultiplier);
    //Last three balls
    private final Pose thirdLine = new Pose(41.321739130434786 * xMultiplier,35.686956521739134 *yMultiplier,Math.toRadians(0));
    private final Pose thirdPush = new Pose(15.026086956521738 * xMultiplier,35.686956521739134 *yMultiplier,Math.toRadians(0));
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
                //Test constraining the speed
//                .setBrakingStrength(10.0)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        back1 = follower.pathBuilder()
                .addPath(new BezierLine(firstPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(135))
                .build();

        secondBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, secondLine))
                .setLinearHeadingInterpolation(Math.toRadians(13),Math.toRadians(0))
                .addPath(new BezierLine(secondLine,secondPush))
//                .setBrakingStrength(10.0)
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
//                .setBrakingStrength(breakingStrength)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        back3 = follower.pathBuilder()
                .addPath(new BezierLine(thirdPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(135))
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
            // Use setPathState to reset pathTimer properly
            setPathState(0); // Start the path sequence

            while (opModeIsActive() && !isStopRequested())
            {
                follower.update();

                // State machine to execute paths and actions
                autonomousPathupdate();

                // Telemetry
                telemetry.addData("path state", pathState);
                telemetry.addData("shoot state", shootState);
                telemetry.addLine(isShoot);
                telemetry.addData("x", follower.getPose().getX());
                telemetry.addData("y", follower.getPose().getY());
                telemetry.addData("heading", Math.toDegrees(follower.getPose().getHeading()));
                telemetry.addData("Shooter Velocity ", flywheel.getData());
                telemetry.update();
            }
        }


    }

    public void autonomousPathupdate() {

        switch (pathState) {

            case 0: // START: spin shooter & reverse feeder
                stopper.block();
                transfer.stop();
                flywheel.setVelocity(v);
                follower.followPath(shoot);
                shootState = 0;
                setPathState(1);
                break;

            case 1: // SHOOT PRELOAD (3 rings)
                if (!follower.isBusy()) {
                    if (pathTimer.getElapsedTime() < 200) break;

                    stopper.lift();
                    transfer.setPower();
                    flywheel.setVelocity(v);
//                    if (shootThreeBallVelocity()) {
//                        setPathState(10);
//                    }
                    sleep(2000);
                    setPathState(10);

                }
                break;

            case 10: // DRIVE TO LINE 1
                intake.feed();
                transfer.setPower();
                stopper.block();
                if (!follower.isBusy()) {
                    follower.followPath(firstBall);
                    setPathState(11);
                }
                break;

            case 11: // RETURN FROM LINE 1
                if (!follower.isBusy()) {
                    stopper.block();
                    transfer.stop();
                    intake.stop();

                    follower.followPath(back1);
                    shootState = 0;
                    setPathState(12);
                }
                break;

            case 12: // SHOOT 3 FROM LINE 1
                if (!follower.isBusy()) {
                    if (pathTimer.getElapsedTime() < 200) break;
                    stopper.lift();
                    transfer.setPower();
                    flywheel.setVelocity(v);

                    sleep(2000);
                    setPathState(20);

                }
                break;

            case 20: // DRIVE TO LINE 2
                intake.feed();
                stopper.block();
                transfer.stop();
                if (!follower.isBusy()) {
                    follower.followPath(secondBall);
                    setPathState(21);
                }
                break;

            case 21: // RETURN FROM LINE 2
                if (!follower.isBusy()) {
                    intake.stop();
                   stopper.block();
                    transfer.stop();
                    follower.followPath(back2);
                    shootState = 0;
                    setPathState(22);
                }
                break;

            case 22: // SHOOT 3 FROM LINE 2
                if (!follower.isBusy()) {
                    if (pathTimer.getElapsedTime() < 200) break;
                    stopper.lift();
                    transfer.setPower();
                    flywheel.setVelocity(v);

                    sleep(2000);
                    setPathState(30);
                }
                break;

            case 30: // DRIVE TO LINE 3
                stopper.block();
                intake.feed();
                transfer.setPower();
                if (!follower.isBusy()) {
                    follower.followPath(thirdBall);
                    setPathState(31);
                }
                break;

            case 31: // RETURN FROM LINE 3
                if (!follower.isBusy()) {
                    intake.stop();
                    transfer.stop();
                    stopper.block();
                    follower.followPath(back3);
                    shootState = 0;
                    setPathState(32);
                }
                break;

            case 32: // SHOOT 3 FROM LINE 3
                if (!follower.isBusy()) {
                    if (pathTimer.getElapsedTime() < 200) break;
                    stopper.lift();
                    transfer.setPower();
                    flywheel.setVelocity(v);

                    sleep(2000);
                    setPathState(100);
                }
                break;

            case 100: // END
                break;
        }
    }


//    public boolean shootThreeBallVelocity()
//    {
//
//        switch (shootState) {
//
//            case 0:
//                // reset timer when starting a new 3-ball cycle
//                stopper.lift();
//                break;
//        }
//        return false;
//    }


    public void setPathState(int pState) {
        pathState = pState;
        if (pathTimer != null) {
            pathTimer.resetTimer();
        }
    }
}
