package org.firstinspires.ftc.teamcode.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Blue Near Auto")


public class BlueNearAuto extends LinearOpMode {
    private int pathState;
    private int shootState;

    private final double v = 1300;
    private final double max = 1330;
    private final double min = 1290;
    private final double current = 2;
    private final double breakingStrength = 2.0;
    Feeder feeder;
    Shooter shooter;
    Intake intake;
    String isShoot;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer, shootTimer;
    //Determine all the position by testing it out;
    //starting position
    private final Pose startPose = new Pose(19.617391304347827,122.71304347826086,Math.toRadians(130));
    //Shooting position
    private final Pose shootingPose = new Pose(46.33043478260869,96.83478260869565,Math.toRadians(130));
    //Near side 3 balls

    private final Pose firstLine = new Pose(48,84.31304347826088,Math.toRadians(0));
    private final Pose firstPush = new Pose(14.9,84.31304347826088,Math.toRadians(0));
    private final Pose firstControl = new Pose(51.547826086956526,80.76521739130435);
    //Middle 3 balls
    private final Pose secondLine = new Pose(48,60.313043478260866,Math.toRadians(0));
    private final Pose secondPush = new Pose(5.3,60.313043478260866,Math.toRadians(0));
    private final Pose secondControl = new Pose(61.982608695652175,54.469565217391306);
    //Last three balls
    private final Pose thirdLine = new Pose(48,35.686956521739134,Math.toRadians(0));
    private final Pose thirdPush = new Pose(5.3,35.686956521739134,Math.toRadians(0));
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
                //Test constraining the speed
                .setBrakingStrength(10.0)
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
                .setBrakingStrength(10.0)
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
                .setBrakingStrength(breakingStrength)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        back3 = follower.pathBuilder()
                .addPath(new BezierLine(thirdPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(135))
                .build();
    }

    @Override
    public void runOpMode() {
        feeder = new Feeder(hardwareMap, "leftFeeder", "rightFeeder");
        shooter = new Shooter(hardwareMap, "shooter", true);
        intake = new Intake(hardwareMap);
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

            while (opModeIsActive() && !isStopRequested()) {
                follower.update();

                // State machine to execute paths and actions
                autonomousPathupdate();

                // Telemetry
                telemetry.addData("current:", shooter.getCurrent());
                telemetry.addData("path state", pathState);
                telemetry.addData("shoot state", shootState);
                telemetry.addLine(isShoot);
                telemetry.addData("x", follower.getPose().getX());
                telemetry.addData("y", follower.getPose().getY());
                telemetry.addData("heading", Math.toDegrees(follower.getPose().getHeading()));
                telemetry.addData("Shooter Velocity ", shooter.getVelocity());
                telemetry.update();
            }
        }


    }

    public void autonomousPathupdate() {

        switch (pathState) {

            case 0: // START: spin shooter & reverse feeder
                feeder.reverseFeed();
                intake.setPower(-0.5);
                shooter.setVelocity(v);
                follower.followPath(shoot);
                shootState = 0;
                setPathState(1);
                break;

            case 1: // SHOOT PRELOAD (3 rings)
                if (!follower.isBusy()) {
                    if (pathTimer.getElapsedTime() < 200) break;

                    if (shootThreeBallVelocity()) {
                        setPathState(10);
                    }
                }
                break;

            case 10: // DRIVE TO LINE 1
                intake.setPower(-1);
                if (!follower.isBusy()) {
                    follower.followPath(firstBall);
                    setPathState(11);
                }
                break;

            case 11: // RETURN FROM LINE 1
                if (!follower.isBusy()) {
                    intake.setPower(-0.8);
                    feeder.reverseFeed();
                    follower.followPath(back1);
                    shootState = 0;
                    setPathState(12);
                }
                break;

            case 12: // SHOOT 3 FROM LINE 1
                if (!follower.isBusy()) {
                    if (pathTimer.getElapsedTime() < 200) break;

                    if (shootThreeBallVelocity()) {
                        setPathState(20);
                    }
                }
                break;

            case 20: // DRIVE TO LINE 2
                intake.setPower(-1);
                if (!follower.isBusy()) {
                    follower.followPath(secondBall);
                    setPathState(21);
                }
                break;

            case 21: // RETURN FROM LINE 2
                if (!follower.isBusy()) {
                    intake.setPower(-0.8);
                    feeder.reverseFeed();
                    follower.followPath(back2);
                    shootState = 0;
                    setPathState(22);
                }
                break;

            case 22: // SHOOT 3 FROM LINE 2
                if (!follower.isBusy()) {
                    if (pathTimer.getElapsedTime() < 200) break;

                    if (shootThreeBallVelocity()) {
                        setPathState(30);
                    }
                }
                break;

            case 30: // DRIVE TO LINE 3
                intake.setPower(-1);
                if (!follower.isBusy()) {
                    follower.followPath(thirdBall);
                    setPathState(31);
                }
                break;

            case 31: // RETURN FROM LINE 3
                if (!follower.isBusy()) {
                    intake.setPower(-0.8);
                    feeder.reverseFeed();
                    follower.followPath(back3);
                    shootState = 0;
                    setPathState(32);
                }
                break;

            case 32: // SHOOT 3 FROM LINE 3
                if (!follower.isBusy()) {
                    if (pathTimer.getElapsedTime() < 200) break;

                    if (shootThreeBallVelocity()) {
                        shooter.setVelocity(0);
                        feeder.reset();
                        intake.setPower(-0.8);
                        setPathState(100);
                    }
                }
                break;

            case 100: // END
                break;
        }
    }


    public boolean shootThreeBallVelocity() {

        // Force time limit per stage
        if (shootTimer == null) shootTimer = new Timer();

        switch (shootState) {

            case 0:
                // reset timer when starting a new 3-ball cycle
                if ((shootTimer.getElapsedTime() > 1000 && shooter.getVelocity() > min && shooter.getVelocity() < max) || shootTimer.getElapsedTime() > 1500) {
                    feeder.feed();
                    intake.setPower(-0.8);
                    shootState = 1;
                    shootTimer.resetTimer();
                    isShoot = "firstFeed";
                }
                break;

            case 1:
                // Either velocity drops OR timeout after 1 sec
                if ((shooter.getCurrent() > current && shootTimer.getElapsedTime() > 500) || shootTimer.getElapsedTime() > 1000) {
                    feeder.reverseFeed();
                    intake.setPower(0);
                    shootState = 2;
                    shootTimer.resetTimer();
                    isShoot = "firstReverse";
                }
                break;

            case 2:
                // Feed next ring
                if ((shootTimer.getElapsedTime() > 700 && shooter.getVelocity() > min && shooter.getVelocity() < max) || shootTimer.getElapsedTime() > 1000){
                    feeder.feed();
                    intake.setPower(-0.8);
                    shootState = 3;
                    shootTimer.resetTimer();
                    isShoot = "secondFeed";
                }
                break;

            case 3:
                if ((shooter.getCurrent()> current && shootTimer.getElapsedTime() > 550) || shootTimer.getElapsedTime() > 1000) {
                    feeder.reverseFeed();
                    intake.setPower(0);
                    shootState = 4;
                    shootTimer.resetTimer();
                    isShoot = "secondFeed";
                }
                break;

            case 4:
                if ((shootTimer.getElapsedTime() > 500 && shooter.getVelocity() > min && shooter.getVelocity() < max) || shootTimer.getElapsedTime() > 1500) {
                    feeder.feed();
                    intake.setPower(-0.8);
                    shootState = 5;
                    shootTimer.resetTimer();
                    isShoot = "thirdFeed";
                }
                break;

            case 5:
                if((shooter.getCurrent() > current && shootTimer.getElapsedTime()> 800) || shootTimer.getElapsedTime() > 2000) {
                    feeder.reverseFeed();
                    shootState = 0;
                    isShoot = "thirdReverse";
                    return true;
                }
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
