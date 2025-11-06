package org.firstinspires.ftc.teamcode.V2Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robots.V2;
import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
@Autonomous(group = "auto", name = "LE Blu AUTO")
public class BLUEEXPIRMENTAL extends LinearOpMode {
    V2 robot;
    Feeder feeder;

    ElapsedTime time = new ElapsedTime();
    double timepassed;
    //Position 2d: Do AFTER COMP 1

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        time.reset();
        robot = new V2(hardwareMap);
        feeder = new Feeder(hardwareMap);
        robot.setDriveSpeed(0.5);

        robot.drive(-0.5, 0, 0);
        sleep(3300);
        robot.drive(0, 0, 0);
        robot.shoot(0.9);
        sleep(2000);
        feeder.feed();
        robot.shoot(1);
        robot.intake();

        sleep(6000);

        robot.drive(0, -0.5, 0);
        sleep(600);


        while (opModeIsActive()) {
            return;
        }
    }
}
