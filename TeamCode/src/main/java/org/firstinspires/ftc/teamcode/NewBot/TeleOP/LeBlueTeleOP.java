package org.firstinspires.ftc.teamcode.NewBot.TeleOP;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewBot.Robot.NewBot;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;

@TeleOp(name = "Blue TeleOP", group = "1")
public class LeBlueTeleOP extends LinearOpMode {
    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);
    double WEB_CAM_OFFSET = 6.0;
    boolean canAlign = false;
    boolean isShooterReversed = false;
    GamepadEvents controller1, controller2;

    @Override
    public void runOpMode() throws InterruptedException
    {
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);
        NewBot robot = new NewBot(hardwareMap, telemetry, 20);
//        follower = Constants.createFollower(hardwareMap);
//        follower.setStartingPose(startPose);
        waitForStart();
        robot.store();
        while(opModeIsActive())
        {

            robot.drive(controller1.left_stick_y, controller1.left_stick_x, controller1.right_stick_x);
//            robot.runShooter();
//            robot.leftLEDIndicator();
            robot.rightLEDIndicator();
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

            if(controller1.x.onPress())
            {
                robot.reverseTransfer();
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

            telemetry.addLine(robot.getData());
            telemetry.update();
            controller1.update();
        }
    }
}

