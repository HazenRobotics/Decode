//package org.firstinspires.ftc.teamcode.Auto;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.teamcode.Robots.V2;
//import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
//import org.firstinspires.ftc.teamcode.SubSystems.Intake;
//import org.firstinspires.ftc.teamcode.SubSystems.Mecanum;
//import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
//
//@Autonomous(group = "Blue", name = "Far Auto Testing")
//public class BLUEFARSIDEAUTOTESTING extends LinearOpMode
//{
//    V2 robot;
//    Feeder feeder;
//    Shooter shooter;
//    Intake intake;
//    Mecanum drive;
//    ElapsedTime time;
//    @Override
//    public void runOpMode() throws InterruptedException {
//        robot = new V2(hardwareMap);
//        shooter = new Shooter(hardwareMap, "shooter", true);
//        intake = new Intake(hardwareMap);
//        feeder = new Feeder(hardwareMap, "leftFeeder", "rightFeeder");
//        drive = new Mecanum(hardwareMap);
//        time = new ElapsedTime();
//        robot.isTransfered = true;
//        waitForStart();
//        shooter.setVelocity(1360);
//        sleep(2500);
//        intake.setPower(-0.8);
//        feeder.feed(1);
//        sleep(1000);
//        feeder.reset();
//        shooter.setVelocity(1365);
//        sleep(1500);
//        feeder.feed(1);
//        sleep(1000);
//        feeder.reset();
//        shooter.setVelocity(1365);
//        sleep(1500);
//        feeder.feed(1.5);
//        sleep(1500);
//        feeder.reset();
//        intake.setPower(0);
//        drive.drive(0.6, -0.2, 0.2);
//        sleep(1000);
//        telemetry.addData("motor velocity", shooter.getVelocity());
//        telemetry.addData("Voltage", shooter.getVoltage());
//        telemetry.update();
//    }
//}