package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robots.V2;
@Autonomous(group = "Blue", name = "Three Ball Auto")
public class BlueThreeBallAuto extends LinearOpMode {
    V2 robot;
    ElapsedTime time;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new V2(hardwareMap);
        time = new ElapsedTime();
        robot.isTransfered = true;
        waitForStart();
        while(opModeIsActive())
        {
            if(time.seconds() < 15)
            {
                robot.shoot();
            }

            //add drive feature

        }
    }
}
