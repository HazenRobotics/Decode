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
    public static PathConstraints pathConstraints;
//   Feeder feeder = new Feeder(hardwareMap);
// Shooter shooter = new Shooter(hardwareMap, "shooter");
//   Intake intake = new Intake(hardwareMap,"intake");
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

//    public void autonomousPathupdate(){
//        switch (pathState){
//            // ... (Cases 0 and 1 remain unchanged: Move to position, Start shooter)
//
//            case 10: // WAIT FOR SPIN-UP
//                if (actionTimer.getElapsedTime() > 1000) {
//                    setPathState(11);
//                }
//                break;
//
//            // --- FEED SEQUENCE START (Preload) ---
//            case 11: // FEED RING 1 - ON
//                feeder.feed(); // Start the feeder action (Push)
//                actionTimer.resetTimer();
//                setPathState(12);
//                break;
//            case 12: // FEED RING 1 - OFF (Reset)
//                if (actionTimer.getElapsedTime() > 400) {
//                    // NOTE: Replace robot.resetFeeder() with your actual code (e.g., robot.feeder.reset() or similar)
//                    feeder.reset();
//                    actionTimer.resetTimer();
//                    setPathState(13);
//                }
//                break;
//            case 13: // FEED RING 2 - ON
//                if (actionTimer.getElapsedTime() > 400) {
//                    feeder.feed();
//                    actionTimer.resetTimer();
//                    setPathState(14);
//                }
//                break;
//            case 14: // FEED RING 2 - OFF (Reset)
//                if (actionTimer.getElapsedTime() > 400) {
//                    feeder.reset();
//                    actionTimer.resetTimer();
//                    setPathState(15);
//                }
//                break;
//            case 15: // FEED RING 3 - ON
//                if (actionTimer.getElapsedTime() > 400) {
//                    feeder.feed();
//                    actionTimer.resetTimer();
//                    setPathState(16);
//                }
//                break;
//            case 16: // FEED RING 3 - OFF (Reset) & MOVE
//                if (actionTimer.getElapsedTime() > 400) {
//                    feeder.reset(); // Explicitly reset feeder
//                    shooter.shoot(0.8);
//
//                    follower.followPath(firstBall);
//                    intake.setPower(0.8);
//                    setPathState(2);
//                }
//                break;
//            // --- FEED SEQUENCE END ---
//
//            // ... (Cases 2 and 20 remain unchanged: Drive and Intake)
//
//            case 30: // WAIT FOR SPIN-UP (1 second)
//                if (actionTimer.getElapsedTime() > 1000) {
//                    setPathState(31);
//                }
//                break;
//            case 31: // FEED ALL COLLECTED RINGS - ON
//                feeder.feed(); // Start feeder
//                actionTimer.resetTimer();
//                setPathState(32);
//                break;
//            case 32: // FEED ALL COLLECTED RINGS - OFF (Reset) & MOVE
//                // We wait 2500ms to allow for all collected rings to be fed and shot
//                if (actionTimer.getElapsedTime() > 2500) {
//                    feeder.reset(); // Explicitly reset feeder
//                    shooter.shoot(-1);
//                    follower.followPath(secondBall);
//                    intake.setPower(0.8);
//                    setPathState(4);
//                }
//                break;
//
//            // === SECOND COLLECTION CYCLE ===
//            // ... (Cases 4 and 40 remain unchanged: Drive and Intake)
//
//            case 50: // WAIT FOR SPIN-UP (1 second) AND FEED - ON
//                if (actionTimer.getElapsedTime() > 1000) {
//                    feeder.feed(); // Start feeder
//                    actionTimer.resetTimer();
//                    setPathState(51);
//                }
//                break;
//            case 51: // FEED - OFF (Reset) & MOVE
//                if (actionTimer.getElapsedTime() > 2500) {
//                    feeder.reset(); // Explicitly reset feeder
//                    shooter.shoot(-1);
//                    follower.followPath(thirdBall);
//                    intake.setPower(0.8);
//                    setPathState(6);
//                }
//                break;
//
//            // === THIRD COLLECTION CYCLE ===
//            // ... (Cases 6 and 60 remain unchanged: Drive and Intake)
//
//            case 70: // WAIT FOR SPIN-UP AND FEED - ON
//                if (actionTimer.getElapsedTime() > 1000) {
//                    feeder.feed(); // Start feeder
//                    actionTimer.resetTimer();
//                    setPathState(71);
//                }
//                break;
//            case 71: // FEED - OFF (Reset) & FINAL STOP
//                if (actionTimer.getElapsedTime() > 2500) {
//                    feeder.feed(); // Explicitly reset feeder
//                    shooter.shoot(-1);
//                    setPathState(8);
//                }
//                break;
//
//            case 8: // FINAL STATE
//                break;
//        }
//    }

    public void setPathState(int pState) {
        pathState = pState;
        if (pathTimer != null) {
            pathTimer.resetTimer();
        }
    }
}
