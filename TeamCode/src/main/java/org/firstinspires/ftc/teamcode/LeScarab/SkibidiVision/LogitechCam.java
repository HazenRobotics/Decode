package org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision;

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
    public double getHorizontalData(AprilTagDetection detectedId)
    {
        if(detectedId ==null)
        {
            return 0;
        }


        if(detectedId.metadata != null)
        {
            return detectedId.ftcPose.y;
        }
        return 0;
    }

    public void disPlayDetectionTelementry(AprilTagDetection detectedId)
    {
        if(detectedId ==null)
        {
            return;
        }

        if(detectedId.metadata != null)
        {
            telemetry.addLine(String.format("\n==== (ID %d) %s", detectedId.id, detectedId.metadata.name));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f (inch)", detectedId.ftcPose.x, detectedId.ftcPose.y, detectedId.ftcPose.z));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f (degree)", detectedId.ftcPose.pitch, detectedId.ftcPose.roll, detectedId.ftcPose.yaw));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f (inch, degree degree)", detectedId.ftcPose.range, detectedId.ftcPose.bearing, detectedId.ftcPose.elevation));
        }else {
            telemetry.addLine(String.format("\n==== (ID %d) Unknown", detectedId.id));
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

    public double getBearing(AprilTagDetection detectedId)
    {
        return detectedId.ftcPose.bearing;
    }

    public void stop()
    {
        if(visionPortal != null)
        {
            visionPortal.close();
        }
    }




}
