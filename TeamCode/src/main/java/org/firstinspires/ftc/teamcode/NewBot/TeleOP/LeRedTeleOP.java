package org.firstinspires.ftc.teamcode.NewBot.TeleOP;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewBot.Robot.NewBot;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeCameraServo;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeIntake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeLED;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeMecanum;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeOutake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeStopper;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeTransfer;
import org.firstinspires.ftc.teamcode.NewBot.Vision.LogitechCam;
import org.firstinspires.ftc.teamcode.NewBot.Utils.VelocityCalculator2;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name = "Red Scrim TeleOP", group = "1")
public class LeRedTeleOP extends LinearOpMode {

    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);
    double WEB_CAM_OFFSET = 6.0;
    boolean canAlign = false;
    boolean isShooterReversed = false;
    GamepadEvents controller;
    @Override
    public void runOpMode() throws InterruptedException
    {
        controller = new GamepadEvents(gamepad1);
        NewBot robot = new NewBot(hardwareMap, telemetry, 24);
//        follower = Constants.createFollower(hardwareMap);
//        follower.setStartingPose(startPose);


        waitForStart();
        robot.store();
        while(opModeIsActive())
        {
           robot.drive(controller.left_stick_y, controller.left_stick_x, controller.right_stick_x);
//            robot.runShooter();
//            robot.leftLEDIndicator();
            robot.rightLEDIndicator();
            robot.getData();

            if(controller.left_bumper.onPress())
            {
                robot.intake();
            }

            if(controller.right_bumper.onPress())
            {
                robot.shoot();
            }

            if(controller.y.onPress())
            {
                robot.store();
            }

            if(controller.x.onPress())
            {
                robot.reverseTransfer();
            }

            if(controller.b.onPress())
            {
                isShooterReversed = !isShooterReversed;
            }

            if(isShooterReversed)
            {
                robot.reverseShooter();
            }

            if(controller.y.onPress())
            {
                canAlign = !canAlign;
            }

            robot.AutoAlign(canAlign);

            telemetry.addLine(robot.getData());
            telemetry.update();
            controller.update();
        }
    }
}


