package org.firstinspires.ftc.teamcode.Auto;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robots.V2;
@TeleOp(name = "Red ")
public class RedThreeBallAuto extends LinearOpMode {
    V2 robot;
    ElapsedTime time;
    Timer shootTime;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new V2(hardwareMap);
        time = new ElapsedTime();
        shootTime = new Timer();
        robot.isTransfered = true;
        waitForStart();
        if(shootTime.getElapsedTimeSeconds() > 4) {
            robot.shoot(1780);
            shootTime.resetTimer();
        }
        if(shootTime.getElapsedTimeSeconds() > 4) {
            robot.shoot(1780);
            shootTime.resetTimer();
        }
        if(shootTime.getElapsedTimeSeconds() > 4) {
            robot.shoot(1780);
            shootTime.resetTimer();
        }

        robot.drive(-0.6,0,0);
        sleep(2500);




        while(opModeIsActive())
        {


            //add drive feature

        }
    }
}
