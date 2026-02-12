package org.firstinspires.ftc.teamcode.NewBot.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeMecanum;

//@Autonomous(name = "Just Move")
public class MovementAuto extends LinearOpMode {
    LeMecanum drive;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new LeMecanum(hardwareMap);

        waitForStart();
        drive.drive(0.5,0,0);
        sleep(2000);
        drive.drive(0,0,0);
        while(opModeIsActive())
        {

        }
    }
}
