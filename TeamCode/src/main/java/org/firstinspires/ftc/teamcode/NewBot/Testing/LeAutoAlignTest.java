package org.firstinspires.ftc.teamcode.NewBot.Testing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeIntake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeLED;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeMecanum;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeOutake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeStopper;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeTransfer;
import org.firstinspires.ftc.teamcode.NewBot.Vision.LogitechCam;
import org.firstinspires.ftc.teamcode.NewBot.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.NewBot.Utils.VelocityCalculator2;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name = "LeAutoAlignTester", group = "1 TungTungTungTesting")
public class LeAutoAlignTest extends LinearOpMode {
    LeTransfer transfer;
    LeOutake flywheel;
    LeIntake intake;
    LeStopper stopper;
    GamepadEvents controller;
    VelocityCalculator2 calculator;
    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);
    double WEB_CAM_OFFSET = 6.0;
    boolean canAlign = false;
    LeLED led;
    LogitechCam camera;
    int TARGET_TAG_ID = 20;
    LeMecanum drive;
    boolean canShoot = false;
    double headingTurn = 0;
    @Override
    public void runOpMode() throws InterruptedException
    {
        transfer = new LeTransfer(hardwareMap);
        controller = new GamepadEvents(gamepad1);
        intake = new LeIntake(hardwareMap);
        flywheel = new LeOutake(hardwareMap);
        stopper = new LeStopper(hardwareMap);
        drive = new LeMecanum(hardwareMap);
        calculator = new VelocityCalculator2();
        camera = new LogitechCam();
        camera.init(hardwareMap,telemetry);
        led = new LeLED(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        waitForStart();
        while(opModeIsActive())
        {


            camera.update();
            AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);


            if(controller.left_bumper.onPress())
            {
                transfer.togglePower();
                intake.feed();
            }


            //Auto Align
            if(targetTag != null && canAlign)
            {
                led.setColor(LeLED.Colors.PINK);
                drive.resetHeading();
                if(camera.getBearing(targetTag) > 1)
                {
                    follower.turn(Math.abs(Math.toRadians(camera.getBearing(targetTag)) + WEB_CAM_OFFSET), false);


                }else if(camera.getBearing(targetTag) < -1)
                {

                    follower.turn(Math.abs(Math.toRadians(camera.getBearing(targetTag)) - WEB_CAM_OFFSET), true);
                }
                telemetry.addData("Camera Rotation", camera.getBearing(targetTag));
            } else if(targetTag == null && canAlign)
            {
                follower.turn(drive.getRotation(), true);
            }
            else
            {
                headingTurn = drive.getRotation();
                led.setColor(LeLED.Colors.BLUE);
                follower.breakFollowing();
            }




            drive.drive(-controller.left_stick_y, controller.left_stick_x, controller.right_stick_x);

            if(controller.x.onPress())
            {
                transfer.reverseMotor();
            }

            if(controller.y.onPress())
            {
                canAlign = !canAlign;
            }

            if(controller.a.onPress())
            {
                canShoot = !canShoot;
            }

            if(canShoot)
            {
                flywheel.setVelocity(calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
            }

            if(controller.b.onPress())
            {
                stopper.toggle();
            }

            follower.update();

            telemetry.addLine("Left bumper to toggle transfer and feed");
            telemetry.addLine("Press X to reverse transfer");
            telemetry.addLine("Press A to toggle shooting");
            telemetry.addLine("Press Y to Auto Align");
            telemetry.addLine("Press B to toggle stopper");
            telemetry.addLine("DPAD UP to increase velocity\nDPAD DOWN to decrease velocity");
            telemetry.addData("Estimated Velocity: ", calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
            telemetry.addData("Horizontal Distance: ", camera.getHorizontalData(targetTag));
            telemetry.addData("Velocity: ", flywheel.getVelocity());
            telemetry.addData("Transfer Power: ", transfer.getData());

            telemetry.update();
            controller.update();
        }
    }
}
