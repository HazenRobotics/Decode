package org.firstinspires.ftc.teamcode.NewBot.TeleOP;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

@TeleOp(name = "Red Scrim TeleOP", group = "1 TungTungTungTesting")
public class LeRedTeleOP extends LinearOpMode {
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
    LeCameraServo cameraServo;
    LogitechCam camera;
    int TARGET_TAG_ID = 24;
    LeMecanum drive;
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
        calculator = new VelocityCalculator2();
        camera = new LogitechCam();
        camera.init(hardwareMap,telemetry);
        led = new LeLED(hardwareMap);
        cameraServo = new LeCameraServo(hardwareMap);
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

            if(controller.dpad_left.onPress())
            {
                cameraServo.setPositon(cameraServo.getPositon() + 0.05);
            }

            if(controller.dpad_left.onPress())
            {
                cameraServo.setPositon(cameraServo.getPositon() - 0.05);
            }


            if(targetTag != null)
            {
                telemetry.addLine("Found AprilTag");
                led.setleftLEDColor(LeLED.Colors.PINK);
            }else {
                telemetry.addLine("Nothing Found :(");
                led.setleftLEDColor(LeLED.Colors.BLUE);
            }

            if(calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)) != VelocityCalculator2.distances[0])
            {
                led.setRightLEDColor(LeLED.Colors.GREEN);
                telemetry.addLine("Can Shoot");
            }else {
                led.setRightLEDColor(LeLED.Colors.ORANGE);
                telemetry.addLine("Can't Shoot");
            }

            if(controller.dpad_up.onPress())
            {
                calculator.adjustDistance(5);
            }

            if(controller.dpad_down.onPress())
            {
                calculator.adjustDistance(-5);
            }


            drive.drive(-controller.left_stick_y, controller.left_stick_x, controller.right_stick_x);

            if(controller.x.onPress())
            {
                transfer.reverseMotor();
            }

            if(controller.right_bumper.onPress())
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


            telemetry.addLine("Left bumper to toggle transfer and feed");
            telemetry.addLine("Press X to reverse transfer");
            telemetry.addLine("Press Right Bumper to toggle shooting");
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


