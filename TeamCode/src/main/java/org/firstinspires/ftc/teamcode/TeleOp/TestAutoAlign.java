//package org.firstinspires.ftc.teamcode.TeleOp;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.LogitechCam;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import org.firstinspires.ftc.teamcode.Robot.StarterRobot;
//import org.firstinspires.ftc.teamcode.SubSystems.MecanumDrive;
//import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//@TeleOp(group = "A LeTeleOP", name = "Auto Align Plan")
//public class TestAutoAlign extends LinearOpMode {
//    GamepadEvents controller1, controller2;
//    StarterRobot robot;
//    LogitechCam camera;
//    public boolean blueAutoAim;
//    @Override
//    public void runOpMode() throws InterruptedException {
//        camera = new LogitechCam();
//        controller1 = new GamepadEvents(gamepad1);
//        controller2 = new GamepadEvents(gamepad2);
//        robot = new StarterRobot(hardwareMap, controller1, controller2);
//        camera.init(hardwareMap, telemetry);
//
//        while (opModeInInit() && !isStopRequested()) {
//            camera.update();
//            AprilTagDetection blue = camera.getTagBySpecificId(20);
//            AprilTagDetection tag21 = camera.getTagBySpecificId(21);
//            AprilTagDetection tag22 = camera.getTagBySpecificId(22);
//            AprilTagDetection tag23 = camera.getTagBySpecificId(23);
//            AprilTagDetection red = camera.getTagBySpecificId(24);
//
//            if (tag21 != null) {
//                telemetry.addLine("Detected Tag ID: 21");
//            } else if (tag22 != null) {
//                telemetry.addLine("Detected Tag ID: 22");
//            } else if (tag23 != null) {
//                telemetry.addLine("Detected Tag ID: 23");
//            }else {
//                telemetry.addLine("No tag detected yet...");
//            }
//
//            telemetry.update();
//        }
//
//        while(opModeIsActive())
//        {
//            camera.update();
//            AprilTagDetection blue = camera.getTagBySpecificId(20);
//
//            if(controller1.b.onPress())
//            {
//                blueAutoAim = !blueAutoAim; //toggle boolean
//            }
//
//            if(blue != null && blueAutoAim){
//                if(blue.ftcPose.yaw < 27.7){
//                    robot.drive(0, 0, 0.2);
//                } else if(blue.ftcPose.yaw > 30.7){
//                    robot.drive(0, 0, -0.2);
//                    //Not sure about strafe if the right power, test it out
//                } else if(blue.ftcPose.x < -29.2){
//                    robot.drive(0, 0.2, 0);
//                } else if(blue.ftcPose.x > -18.6){
//                    robot.drive(0, -0.2, 0);
//                } else if(blue.ftcPose.y < 284){
//                    robot.drive(0.2, 0, 0);
//                } else if(blue.ftcPose.y > 305){
//                    robot.drive(-0.2, 0, 0);
//                } else {
//                    robot.drive(0, 0, 0);
//                }
//            } else {
//                //Stop if the tag is not visible or auto-aim is off
//                robot.drive(0, 0, 0);
//            }
//            controller1.update();
//            camera.disPlayDetectionTelementry(blue);
//            telemetry.addData("AutoAim", blueAutoAim);
//            telemetry.addData("Tag", blue != null ? blue.id : "none");
//            telemetry.update();
//        }
//
////                 camera.disPlayDetectionTelementry(blue);
////                double x = blue.ftcPose.x;
////                double y = blue.ftcPose.y;
////                double blue = blue.ftcPose.blue;
////
////                //idk what the values are, test them out
////                double x_offset = 70;
////                double y_offset = 60;
////
////                if (Math.abs(x) > x_offset) {
////                    // Strafe left or right
////                    if (x > 0) {
////
////                        robot.drive(0, 0.2, 0);
////                    } else {
////                        robot.drive(0, -0.2, 0);
////                    }
////                    //drive forward
////                } else if (z > y_offset + 0.05) {
////                    robot.drive(0.2, 0, 0);
////                } else if (z < y_offset - 0.05) {
////                    robot.drive(-0.2, 0, 0);
////                } else {
////                    robot.drive(0,0,0);
////                }
////            } else {
////                // idk for turning
////                robot.drive(0,0,0.1);
////            }
//    }
//}
//
