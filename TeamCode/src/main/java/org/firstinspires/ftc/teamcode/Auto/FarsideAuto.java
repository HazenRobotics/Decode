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
    public void runOpMode() throws InterruptedException {
        drive = new MecanumDrive(hardwareMap);
        feeder = new Feeder(hardwareMap);
        flap = new Flap(hardwareMap, "frontFlap", "backFlap");
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        transfer = new Transfer(hardwareMap);
        waitForStart();

    }
}
