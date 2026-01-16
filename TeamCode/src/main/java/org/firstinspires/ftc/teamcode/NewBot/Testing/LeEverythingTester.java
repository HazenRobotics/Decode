package org.firstinspires.ftc.teamcode.NewBot.Testing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeIntake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeMecanum;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeOutake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeStopper;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeTransfer;
import org.firstinspires.ftc.teamcode.NewBot.Vision.LogitechCam;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name = "LeEverythingTungTungTungTungTester", group = "1 TungTungTungTesting")
public class LeEverythingTester extends LinearOpMode {
    LeTransfer transfer;
    LeOutake flywheel;
    LeIntake intake;
    LeStopper stopper;
    GamepadEvents controller;
    LeMecanum drive;
    private Follower follower;
    LogitechCam camera;
    int velocity = 1500;
    private final Pose startPose = new Pose(0,0,0);
    double WEB_CAM_OFFSET = 6.0;
    int TARGET_TAG_ID = 20;
    boolean canShoot = false;
    @Override
    public void runOpMode() throws InterruptedException
    {
        transfer = new LeTransfer(hardwareMap);
        controller = new GamepadEvents(gamepad1);
        intake = new LeIntake(hardwareMap);
        flywheel = new LeOutake(hardwareMap);
        stopper = new LeStopper(hardwareMap);
        drive = new LeMecanum(hardwareMap);
        camera = new LogitechCam();
        camera.init(hardwareMap,telemetry);
//        follower = Constants.createFollower(hardwareMap);
//        follower.setStartingPose(startPose);

        waitForStart();
        while(opModeIsActive())
        {

            camera.update();
            AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);
            if(controller.left_bumper.onPress())
            {
                transfer.togglePower();
                intake.toggle();
            }


            drive.drive(-controller.left_stick_y, controller.left_stick_x, controller.right_stick_x);

            if(controller.x.onPress())
            {
                transfer.reverseMotor();
            }

            if(controller.y.onPress())
            {
                canShoot = !canShoot;
            }

            if(canShoot)
            {
                flywheel.setVelocity(velocity);
            }

            if(controller.dpad_up.onPress())
            {
                velocity += 25;
            }

            if(controller.dpad_down.onPress())
            {
                velocity -= 25;
            }

            if(controller.b.onPress())
            {
                stopper.toggle();
            }

            telemetry.addLine("All CONTROLS ARE MANUAL MDOE, SO IT WILL BE ANNOYING TO CONTROL");
            telemetry.addLine("Left bumper to toggle transfer and feed");
            telemetry.addLine("Press X to reverse transfer");
            telemetry.addLine("Press Y to toggle shooting");
            telemetry.addLine("Press B to toggle stopper");
            telemetry.addLine("DPAD UP to increase velocity\nDPAD DOWN to decrease velocity");
            telemetry.addData("Velocity: ", velocity);
            telemetry.addData("Horizontal Distance: ", camera.getHorizontalData(targetTag));
            telemetry.addData("Transfer Power: ", transfer.getData());

            telemetry.update();
            controller.update();
        }
    }
}