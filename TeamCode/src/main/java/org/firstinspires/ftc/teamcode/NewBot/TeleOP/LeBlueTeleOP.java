package org.firstinspires.ftc.teamcode.NewBot.TeleOP;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewBot.Robot.NewBot;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeLED;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.NewBot.pedroPathing.Constants;

@TeleOp(name = "Blue Comp TeleOP", group = "2")
public class LeBlueTeleOP extends LinearOpMode {
    private Follower follower;
    public final Pose startPose = new Pose(0,0,0);
    public Pose endPose = new Pose(0,0,0);
    double WEB_CAM_OFFSET = 6.0;
    boolean canAlign = false;
    boolean isShooterReversed = false;
    boolean isPark = false;
    boolean isGreen = false;
    GamepadEvents controller1, controller2;

    PathChain park;
    public PathChain buildPaths(Pose currentPose, Pose end) {
        return follower.pathBuilder()
                .addPath(new BezierLine(currentPose, end))
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(66))
                .build();
    }

    @Override
    public void runOpMode() throws InterruptedException
    {
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);
        NewBot robot = new NewBot(hardwareMap, telemetry, 20);
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        waitForStart();
        robot.store();
        while(opModeIsActive())
        {

            robot.drive(controller1.left_stick_y, controller1.left_stick_x, controller1.right_stick_x);
//            robot.runShooter();
//            robot.leftLEDIndicator();

            robot.getData();
            robot.adJustFlywheel(controller2);

            if(controller1.left_bumper.onPress())
            {
                robot.toggleShootStore();
            }

            if(controller1.right_bumper.onPress())
            {
                robot.shoot();
            }

            if(controller2.x.onPress())
            {
                robot.reverseTransfer();
            }

            if(controller2.y.onPress())
            {
                robot.reverseIntake();
            }

            if(controller1.x.onPress())
            {
                isPark = !isPark;
            }

            if(isPark)
            {
                Pose pos = follower.getPose();
               follower.followPath(buildPaths(pos, endPose));
                robot.parkLed(LeLED.Colors.ORANGE);
            }else {
                endPose = follower.getPose();
                follower.breakFollowing();

            }

            if(controller2.right_bumper.onPress())
            {
                isGreen = !isGreen;
            }

            if(isGreen)
            {
                robot.greenLed();
            }else {
                robot.rightLEDIndicator();
            }



            if(controller1.b.onPress())
            {
                isShooterReversed = !isShooterReversed;
            }

            if(isShooterReversed)
            {
                robot.reverseShooter();
            }

            if(controller1.y.onPress())
            {
                canAlign = !canAlign;
            }


            robot.AutoAlign(canAlign);
            follower.update();
            telemetry.addLine(robot.getData());
            telemetry.addData("Can Park", isPark);
            telemetry.update();
            controller1.update();
        }
    }
}

