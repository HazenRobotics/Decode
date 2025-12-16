package org.firstinspires.ftc.teamcode.OldBots.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OldBots.Robots.V2;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems.LeMecanum;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;

@Autonomous(group = "Blue", name = "Three Ball Auto")
public class BlueThreeBallAuto extends LinearOpMode {
    V2 robot;
    Feeder feeder;
    Shooter shooter;
    Intake intake;
    LeMecanum drive;
    ElapsedTime time;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new V2(hardwareMap);
        shooter = new Shooter(hardwareMap,"shooter", true);
        intake = new Intake(hardwareMap);
        feeder = new Feeder(hardwareMap, "leftFeeder","rightFeeder");
        drive = new LeMecanum(hardwareMap);
        time = new ElapsedTime();
        robot.isTransfered = true;
        waitForStart();
        shooter.setVelocity(1000);
        drive.drive(-0.8,0,0);
        sleep(1200);
        drive.drive(0,0,0);
        sleep(2200);
        intake.setPower(-0.8);
        feeder.feed();
        sleep(1500);
        feeder.reset();
//        intake.setPower(0);
        sleep(1500);
        intake.setPower(-0.8);
        feeder.feed();
        sleep(1000);
        feeder.reset();
//        intake.setPower(0);
        sleep(1500);
        intake.setPower(-0.8);
        feeder.feed();
        sleep(1500);
        feeder.reset();
        intake.setPower(0);
        shooter.setVelocity(0);
        telemetry.addData("motor velocity", shooter.getVelocity());
        telemetry.addData("Voltage", shooter.getVoltage());
        telemetry.update();
        while(opModeIsActive())
        {

            //add drive feature

        }
    }
}
