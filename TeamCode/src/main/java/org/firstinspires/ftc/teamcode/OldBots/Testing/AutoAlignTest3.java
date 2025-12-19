//package org.firstinspires.ftc.teamcode.OldBots.Testing;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;
//import org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems.LeMecanum;
//import org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision.LogitechCam;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//
//public class AutoAlignTest3 extends LinearOpMode {
//    private static final int TARGET_TAG_ID = 20; // Change this to the AprilTag ID you want to align to
//    private LogitechCam visionSystem;
//    private GamepadEvents controller;
//    private LeMecanum drive;
//    private static final double ALIGNMENT_TOLERANCE_DEG = 1.0;
//    @Override
//    public void runOpMode() throws InterruptedException {
//        visionSystem = new LogitechCam();
//        visionSystem.init(hardwareMap, telemetry);
//        controller = new GamepadEvents(gamepad1);
//        drive = new LeMecanum(hardwareMap);
//        waitForStart();
//        if (isStopRequested()) return;
//
//        while (opModeIsActive()) {
//            AprilTagDetection targetTag = visionSystem.getTagBySpecificId(TARGET_TAG_ID);
//
//            if(controller.left_bumper.onPress() && targetTag != null)
//            {
//                double yawErrorDeg = targetTag.ftcPose.yaw;
//                double yawErrorRad = AngleUnit.DEGREES.toRadians(yawErrorDeg);
//
//                if(Math.abs(yawErrorDeg) > ALIGNMENT_TOLERANCE_DEG)
//                {
//
//                }
//            }
//
//
//
//        }
//    }
//}
