package org.firstinspires.ftc.teamcode.OldBots.Auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.NewBot.pedroPathing.Constants;

//@Autonomous(name = "Red Near Auto")
public class RedNearAuto extends LinearOpMode {

    private int pathState;
    private int shootState;

    private final double v = 1360;
    private final double max = 1400;
    private final double min = 1350;
    private final double current = 1.35;
    private final double breakingStrength = 2.0;

    private Feeder feeder;
    private Shooter shooter;
    private Intake intake;
    private String isShoot;

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer, shootTimer;

    // Red path positions
    private final Pose startPose = new Pose(123.54782608695652,122.50434782608696,Math.toRadians(45));
    private final Pose shootingPose = new Pose(96,96.83478260869565,Math.toRadians(45));

    private final Pose firstLine = new Pose(99.13043478260869,84.31304347826088,Math.toRadians(180));
    private final Pose firstPush = new Pose(130.64347826086959,84.10434782608695,Math.toRadians(180));
    private final Pose secondLine = new Pose(99.13043478260869,60.313043478260866,Math.toRadians(180));
    private final Pose secondPush = new Pose(130.8521739130435,60.104347826086965,Math.toRadians(180));
    private final Pose thirdLine = new Pose(99.13043478260869,35.686956521739134,Math.toRadians(180));
    private final Pose thirdPush = new Pose(130.43478260869566,35.686956521739134,Math.toRadians(180));

    private PathChain shoot, firstBall, back1, secondBall, back2, thirdBall, back3;

    public void buildPaths() {
        shoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootingPose))
                .setConstantHeadingInterpolation(Math.toRadians(45))
                .build();

        firstBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, firstLine))
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(180))
                .addPath(new BezierLine(firstLine, firstPush))
                .setBrakingStrength(breakingStrength)
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        back1 = follower.pathBuilder()
                .addPath(new BezierLine(firstPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(45))
                .build();

        secondBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, secondLine))
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(180))
                .addPath(new BezierLine(secondLine, secondPush))
                .setBrakingStrength(breakingStrength)
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        back2 = follower.pathBuilder()
                .addPath(new BezierLine(secondPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(45))
                .build();

        thirdBall = follower.pathBuilder()
                .addPath(new BezierLine(shootingPose, thirdLine))
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(180))
                .addPath(new BezierLine(thirdLine, thirdPush))
                .setBrakingStrength(breakingStrength)
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        back3 = follower.pathBuilder()
                .addPath(new BezierLine(thirdPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(45))
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
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            opmodeTimer.resetTimer();
            setPathState(0);

            while (opModeIsActive() && !isStopRequested()) {
                follower.update();
                autonomousPathupdate();

                telemetry.addData("Shooter Velocity", shooter.getVelocity());
                telemetry.addData("Shooter Current", shooter.getCurrent());
                telemetry.addData("Path State", pathState);
                telemetry.addData("Shoot State", shootState);
                telemetry.addLine(isShoot);
                telemetry.addData("x", follower.getPose().getX());
                telemetry.addData("y", follower.getPose().getY());
                telemetry.update();
            }
        }
    }

    public void autonomousPathupdate() {
        switch (pathState) {

            case 0: // Spin shooter & reverse feeder
                feeder.reverseFeed();
                intake.setPower(-0.5);
                shooter.setVelocity(v);
                follower.followPath(shoot);
                shootState = 0;
                setPathState(1);
                break;

            case 1: // Shoot preload
                if (!follower.isBusy()) {
                    if (pathTimer.getElapsedTime() < 200) break;
                    if (shootThreeBallVelocity()) {
                        setPathState(10);
                    }
                }
                break;

            case 10: // Drive to line 1
                intake.setPower(-1);
                if (!follower.isBusy()) {
                    follower.followPath(firstBall);
                    setPathState(11);
                }
                break;

            case 11: // Return from line 1
                if (!follower.isBusy()) {
                    intake.setPower(-0.8);
                    feeder.reverseFeed();
                    follower.followPath(back1);
                    shootState = 0;
                    setPathState(12);
                }
                break;

            case 12: // Shoot 3 from line 1
                if (!follower.isBusy()) {
                    if (pathTimer.getElapsedTime() < 200) break;
                    if (shootThreeBallVelocity()) setPathState(20);
                }
                break;

            case 20: // Drive to line 2
                intake.setPower(-1);
                if (!follower.isBusy()) {
                    follower.followPath(secondBall);
                    setPathState(21);
                }
                break;

            case 21: // Return from line 2
                if (!follower.isBusy()) {
                    intake.setPower(-0.8);
                    feeder.reverseFeed();
                    follower.followPath(back2);
                    shootState = 0;
                    setPathState(22);
                }
                break;

            case 22: // Shoot 3 from line 2
                if (!follower.isBusy()) {
                    if (pathTimer.getElapsedTime() < 200) break;
                    if (shootThreeBallVelocity()) setPathState(30);
                }
                break;

            case 30: // Drive to line 3
                intake.setPower(-1);
                if (!follower.isBusy()) {
                    follower.followPath(thirdBall);
                    setPathState(31);
                }
                break;

            case 31: // Return from line 3
                if (!follower.isBusy()) {
                    intake.setPower(-0.8);
                    feeder.reverseFeed();
                    follower.followPath(back3);
                    shootState = 0;
                    setPathState(32);
                }
                break;

            case 32: // Shoot 3 from line 3
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
        if (shootTimer == null) shootTimer = new Timer();

        switch (shootState) {

            case 0:
                if ((shootTimer.getElapsedTime() > 1000 && shooter.getVelocity() > min && shooter.getVelocity() < max)
                        || shootTimer.getElapsedTime() > 1500) {
                    feeder.feed();
                    intake.setPower(-0.8);
                    shootState = 1;
                    shootTimer.resetTimer();
                    isShoot = "firstFeed";
                }
                break;

            case 1:
                if ((shooter.getCurrent() > current && shootTimer.getElapsedTime() > 500)
                        || shootTimer.getElapsedTime() > 1000) {
                    feeder.reverseFeed();
                    intake.setPower(0);
                    shootState = 2;
                    shootTimer.resetTimer();
                    isShoot = "firstReverse";
                }
                break;

            case 2:
                if ((shootTimer.getElapsedTime() > 700 && shooter.getVelocity() > min && shooter.getVelocity() < max)
                        || shootTimer.getElapsedTime() > 1500) {
                    feeder.feed();
                    intake.setPower(-0.8);
                    shootState = 3;
                    shootTimer.resetTimer();
                    isShoot = "secondFeed";
                }
                break;

            case 3:
                if ((shooter.getCurrent() > current && shootTimer.getElapsedTime() > 550)
                        || shootTimer.getElapsedTime() > 1500) {
                    feeder.reverseFeed();
                    intake.setPower(0);
                    shootState = 4;
                    shootTimer.resetTimer();
                    isShoot = "secondReverse";
                }
                break;

            case 4:
                if ((shootTimer.getElapsedTime() > 500 && shooter.getVelocity() > min && shooter.getVelocity() < max)
                        || shootTimer.getElapsedTime() > 1500) {
                    feeder.feed();
                    intake.setPower(-0.8);
                    shootState = 5;
                    shootTimer.resetTimer();
                    isShoot = "thirdFeed";
                }
                break;

            case 5:
                if ((shooter.getCurrent() > current && shootTimer.getElapsedTime() > 800)
                        || shootTimer.getElapsedTime() > 2000) {
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
        if (pathTimer != null) pathTimer.resetTimer();
    }
}
