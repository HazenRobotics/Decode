package org.firstinspires.ftc.teamcode.V2Auto;

import com.pedropathing.Drivetrain;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.localization.Localizer;
import com.pedropathing.math.Vector;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class BluePedroAuto extends LinearOpMode {
    private int pathState;
    private FollowerConstants followerConstants;
    private Drivetrain drive;
    private Localizer localizer;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    //Determine all the position by testing it out;
    //starting position
    private final Pose startPose = new Pose(19.617391304347827,122.71304347826086,Math.toRadians(135));
    //Shooting position
    private final Pose shootingPose = new Pose(46.33043478260869,96.83478260869565,Math.toRadians(135));
    //Near side 3 balls
    private final Pose firstLine = new Pose(41.321739130434786,35.686956521739134,Math.toRadians(0));
    private final Pose firstPush = new Pose(14.191304347826087,84.10434782608695,Math.toRadians(0));
    private final Pose firstControl = new Pose(51.547826086956526,80.76521739130435);
    //Middle 3 balls
    private final Pose secondLine = new Pose(40.904347826086955,60.313043478260866,Math.toRadians(0));
    private final Pose secondPush = new Pose(14.191304347826087,60.104347826086965,Math.toRadians(0));
    private final Pose secondControl = new Pose(61.982608695652175,54.469565217391306);
    //Last three balls
    private final Pose thirdLine = new Pose(40.48695652173913,84.31304347826088,Math.toRadians(0));
    private final Pose thirdPush = new Pose(14.817391304347826,83.89565217391304,Math.toRadians(0));
    private final Pose thirdControl = new Pose(63.02608695652174,28.382608695652173);
    private PathChain shoot, firstBall, push1, back1, secondBall, push2, back2, thirdBall, push3, back3;
    public void buildPaths(){
        shoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootingPose))
                .setConstantHeadingInterpolation(Math.toRadians(135))
                .build();

        firstBall = follower.pathBuilder()
                .addPath(new BezierCurve(shootingPose,firstControl, firstPush))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(0))
                .build();

        back1 = follower.pathBuilder()
                .addPath(new BezierLine(firstPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(135))
                .build();

        secondBall = follower.pathBuilder()
                .addPath(new BezierCurve(shootingPose,secondControl, secondPush))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(0))
                .build();

        back2 = follower.pathBuilder()
                .addPath(new BezierLine(secondPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(135))
                .build();

        thirdBall = follower.pathBuilder()
                .addPath(new BezierCurve(shootingPose,thirdControl, thirdPush))
                .setLinearHeadingInterpolation(Math.toRadians(135),Math.toRadians(0))
                .build();

        back3 = follower.pathBuilder()
                .addPath(new BezierLine(thirdPush, shootingPose))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(135))
                .build();
    }

    @Override
    public void runOpMode() {
        drive = new Drivetrain() {
            @Override
            public double[] calculateDrive(Vector correctivePower, Vector headingPower, Vector pathingPower, double robotHeading) {
                return new double[0];
            }

            @Override
            public void updateConstants() {

            }

            @Override
            public void breakFollowing() {

            }

            @Override
            public void runDrive(double[] drivePowers) {

            }

            @Override
            public void startTeleopDrive() {

            }

            @Override
            public void startTeleopDrive(boolean brakeMode) {

            }

            @Override
            public double xVelocity() {
                return 0;
            }

            @Override
            public double yVelocity() {
                return 0;
            }

            @Override
            public void setXVelocity(double xMovement) {

            }

            @Override
            public void setYVelocity(double yMovement) {

            }

            @Override
            public double getVoltage() {
                return 0;
            }

            @Override
            public String debugString() {
                return "";
            }
        };
        followerConstants = new FollowerConstants();
        localizer = new Localizer() {
            @Override
            public Pose getPose() {
                return null;
            }

            @Override
            public Pose getVelocity() {
                return null;
            }

            @Override
            public Vector getVelocityVector() {
                return null;
            }

            @Override
            public void setStartPose(Pose setStart) {

            }

            @Override
            public void setPose(Pose setPose) {

            }

            @Override
            public void update() {

            }

            @Override
            public double getTotalHeading() {
                return 0;
            }

            @Override
            public double getForwardMultiplier() {
                return 0;
            }

            @Override
            public double getLateralMultiplier() {
                return 0;
            }

            @Override
            public double getTurningMultiplier() {
                return 0;
            }

            @Override
            public void resetIMU() throws InterruptedException {

            }

            @Override
            public double getIMUHeading() {
                return 0;
            }

            @Override
            public boolean isNAN() {
                return false;
            }
        };

        follower = new Follower(followerConstants, localizer , drive);
        pathTimer = new Timer();
        actionTimer = new Timer();
        opmodeTimer = new Timer();

        buildPaths();

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
