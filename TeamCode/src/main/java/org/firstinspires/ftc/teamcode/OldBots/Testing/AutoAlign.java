//package org.firstinspires.ftc.teamcode.OldBots.Testing;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.OldBots.Robots.V2;
//import org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision.LogitechCam;
//import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//
//@TeleOp(name = "AutoAlignTest", group = "Tester")
//public class AutoAlign extends LinearOpMode {
//
//    private LogitechCam webcam = new LogitechCam();
//    private GamepadEvents controller1, controller2;
//    private V2 robot;
//    private int TARGET_ID = 20;
//    private static final double SPEED_SCALE = 0.9;
//    private static final double P_GAIN = 0.015;
//    private static final double ROT_DEADZONE = 1.0;   // degrees
//    private static final double MAX_ALIGN_SPEED = 0.6;
//
//    @Override
//    public void runOpMode() {
//        webcam = new LogitechCam();
//        controller1 = new GamepadEvents(gamepad1);
//        controller2 = new GamepadEvents(gamepad2);
//
//        robot = new V2(hardwareMap, controller1, controller2);
//        webcam.init(hardwareMap, telemetry);
//
//        telemetry.addLine("AutoAlign ready");
//        telemetry.update();
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//
//            controller1.update();
//            controller2.update();
//            webcam.update();
//
//            // -------------------- DRIVER INPUT --------------------
//            double forward = -gamepad1.left_stick_y * SPEED_SCALE;
//            double strafe  =  gamepad1.left_stick_x * SPEED_SCALE;
//            double rotate  =  gamepad1.right_stick_x * SPEED_SCALE;
//            AprilTagDetection target = webcam.getTagBySpecificId(TARGET_ID);
//
//            // -------------------- AUTO-ALIGN --------------------
//            if (gamepad1.a) {
//                telemetry.addData("Is target found", target);
//                telemetry.addData("Is target metadata found", target.metadata != null);
//                if (target != null && target.metadata != null)
//                {
//
//                    double bearingError = target.ftcPose.bearing;
//                    double alignTurn = -bearingError * P_GAIN;
//
//                    alignTurn = Math.max(
//                            -MAX_ALIGN_SPEED,
//                            Math.min(MAX_ALIGN_SPEED, alignTurn)
//                    );
//
//                    // Lock when aligned
//                    if (Math.abs(bearingError) < ROT_DEADZONE) {
//                        forward = 0.0;
//                        strafe  = 0.0;
//                        rotate  = 0.0;
//                    } else {
//                        rotate = alignTurn;
//                    }
//
//                    telemetry.addData("Align", "ACTIVE");
//                    telemetry.addData("Bearing (deg)", "%.2f", bearingError);
//                    telemetry.addData("Turn Cmd", "%.2f", rotate);
//
//                } else {
//                    telemetry.addData("Align", "Target not visible");
//                }
//            }
//
//            // -------------------- DRIVE --------------------
//            robot.fieldCentricDrive(forward, strafe, rotate);
//            webcam.disPlayDetectionTelementry(target);
//            telemetry.update();
//        }
//
//        webcam.stop();
//    }
//}
