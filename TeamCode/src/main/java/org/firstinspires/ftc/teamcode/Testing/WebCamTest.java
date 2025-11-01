package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Vision.LogitechCam;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(group = "test", name = "LeWebcam")
public class WebCamTest extends OpMode {
    LogitechCam webcam = new LogitechCam();
    @Override
    public void init() {
        webcam.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        webcam.update();
        AprilTagDetection id20 = webcam.getTagBySpecificId(20);
        telemetry.addData("id20 String", id20.toString());
    }
}
