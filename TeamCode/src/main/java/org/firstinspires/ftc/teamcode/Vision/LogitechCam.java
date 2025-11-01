package org.firstinspires.ftc.teamcode.Vision;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

public class LogitechCam {
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    private List<AprilTagDetection> detectedTags = new ArrayList<>();
    private Telemetry telemetry;
    public void init(HardwareMap hw, Telemetry telemetry)
    {
        this.telemetry = telemetry;
        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hw.get(WebcamName.class, "Webcam"));
        builder.setCameraResolution(new Size(640, 480));
        builder.addProcessor(aprilTagProcessor);

        visionPortal = builder.build();


    }

    public void update()
    {
        detectedTags = aprilTagProcessor.getDetections();

    }

    public List<AprilTagDetection> getDetectedTags()
    {
        return detectedTags;
    }
    public void disPlayDetectionTelemnetr(AprilTagDetection detectedId)
    {
        if(detectedId ==null)
        {
            return;
        }

        if(detectedId.metadata != null)
        {
            telemetry.addLine(String.format("\n==== (ID %d) %s", detectedId.id, detectedId.metadata.name));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f (inch)", detectedId.ftcPose.x, detectedId.ftcPose.y));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f (degree)", detectedId.ftcPose.pitch, detectedId.ftcPose.roll));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f (inch, degree degree)", detectedId.ftcPose.range));
        }else {
            telemetry.addLine(String.format("\n==== (ID %d) Unknown", detectedId.id));
            telemetry.addLine();
        }

    }
    public AprilTagDetection getTagBySpecificId(int id)
    {
        for(AprilTagDetection detection: detectedTags)
        {
            if(detection.id == id)
            {
                return detection;
            }

        }
        return null;
    }

    public void stop()
    {
        if(visionPortal != null)
        {
            visionPortal.close();
        }
    }
}
