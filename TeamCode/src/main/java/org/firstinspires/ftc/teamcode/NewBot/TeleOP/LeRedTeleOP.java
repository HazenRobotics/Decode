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

@TeleOp(name = "Red Comp TeleOP", group = "2")
public class LeRedTeleOP extends LinearOpMode {

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
        controller2 = new GamepadEvents(gamepad1);
        NewBot robot = new NewBot(hardwareMap, telemetry, 24);
//        follower = Constants.createFollower(hardwareMap);
//        follower.setStartingPose(startPose);
        robot.adJustFlywheel(controller2);

        waitForStart();
        robot.store();
        while(opModeIsActive())
        {
           robot.drive(controller1.left_stick_y, controller1.left_stick_x, -controller1.right_stick_x);
//            robot.runShooter();
//            robot.leftLEDIndicator();
            robot.rightLEDIndicator();
            robot.getData();
            robot.adJustFlywheel(controller2);

            if(controller1.left_bumper.onPress())
            {
                robot.intake();
            }

            if(controller1.right_bumper.onPress())
            {
                robot.shoot();
            }

            if(controller2.y.onPress())
            {
                robot.reverseTransfer();
            }
            if(controller2.y.onPress())
            {
                robot.reverseIntake();
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


