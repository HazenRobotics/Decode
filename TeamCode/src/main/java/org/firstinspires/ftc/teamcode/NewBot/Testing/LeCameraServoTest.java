package org.firstinspires.ftc.teamcode.NewBot.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeCameraServo;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeLED;
import org.firstinspires.ftc.teamcode.NewBot.Vision.LogitechCam;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name = "LeServoCameraTester", group = "1 TungTungTungTesting")
public class LeCameraServoTest extends LinearOpMode {
    LeCameraServo servo;
    GamepadEvents controller;
    LogitechCam camera;
    LeLED led;
    int TARGET_TAG_ID = 20;

    @Override
    public void runOpMode() throws InterruptedException
    {
        servo = new LeCameraServo(hardwareMap);
        camera = new LogitechCam();
        camera.init(hardwareMap,telemetry);
        controller = new GamepadEvents(gamepad1);
        led = new LeLED(hardwareMap);
        waitForStart();
        while(opModeIsActive())
        {
            camera.update();
            AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);

            if(targetTag != null)
            {
                telemetry.addLine("Found AprilTag");
//                led.setColor(LeLED.PINK_WEIGHT);
            }else {
                telemetry.addLine("Nothing Found :(");
//                led.setColor(LeLED.BLUE_WEIGHT);
            }

            servo.setPositon(controller.left_stick_y);
            telemetry.addData("Horizontal Distance: ", camera.getHorizontalData(targetTag));
            telemetry.addLine("Hold left joystick up and down to control servo");
            telemetry.addData("Servo pos", servo.getData());
            telemetry.update();
            controller.update();
        }
    }
}
