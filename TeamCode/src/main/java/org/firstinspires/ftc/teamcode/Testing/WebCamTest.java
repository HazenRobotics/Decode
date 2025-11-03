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
        //Read AprilTag, return a pattern:
        //20: Blue Goal
        //21: Green, Purple, Purple
        //22: Purple, Green, Purple
        //23: Purple, Purple, Green
        //24: Red Goal
        webcam.update();
        AprilTagDetection id = webcam.getTagBySpecificId(24);
        if(id != null) {
            webcam.disPlayDetectionTelementry(id);
        }
    }
}