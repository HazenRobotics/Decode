package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Flap;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.SubSystems.Transfer;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

@Autonomous(name = "Farsid 3 ball launch")
public class FarsideAuto extends LinearOpMode {
    private ElapsedTime globalTime = new ElapsedTime();
    GamepadEvents controller;
    MecanumDrive drive;
    Feeder feeder;
    Flap flap;
    Intake intake;
    Shooter shooter;
    Transfer transfer;
    private final double v = 1900;
    public void runOpMode() throws InterruptedException {
        drive = new MecanumDrive(hardwareMap);
        feeder = new Feeder(hardwareMap);
        flap = new Flap(hardwareMap, "frontFlap", "backFlap");
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, "leftShooter");
        transfer = new Transfer(hardwareMap);


        waitForStart();
        flap.frontGo();
        flap.backBlock();
        shooter.setVelocity(v);
        sleep(3000);
        feeder.feed();
        sleep(2000);
        feeder.reset();
        shooter.reset();
        flap.backDown();
        shooter.setVelocity(400);
        transfer.setServo(1);
        sleep(1500);
        flap.backBlock();
        transfer.setMotor(0.3);
        sleep(500);
        transfer.setMotor(0);
        transfer.setServo(0);
        feeder.feed(-1);
        shooter.setVelocity(-200);
        sleep(600);
        shooter.setVelocity(v);
        sleep(3000);
        feeder.feed();
        sleep(2000);
        feeder.reset();
        shooter.setVelocity(300);
        flap.backDown();
        sleep(1500);
        feeder.feed(-1);
        shooter.setVelocity(-200);
        sleep(600);
        shooter.setVelocity(v);
        sleep(3000);
        feeder.feed();
        sleep(2000);
        shooter.reset();
        feeder.reset();
    }
}
