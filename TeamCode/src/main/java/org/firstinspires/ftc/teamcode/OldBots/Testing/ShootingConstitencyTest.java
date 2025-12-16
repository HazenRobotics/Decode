package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;
@TeleOp(group = "Tester", name = "Shooter Reliability Test")
public class ShootingConstitencyTest extends LinearOpMode {
    Shooter shooter;
    GamepadEvents controller;
    Feeder feeder;
    boolean isFeed = false;
    int max = 1020, min = 990;
    int v = 1000;
    @Override
    public void runOpMode() throws InterruptedException {
        shooter = new Shooter(hardwareMap, "shooter", true);
        feeder = new Feeder(hardwareMap, "leftFeeder", "rightFeeder");
        controller = new GamepadEvents(gamepad1);

        waitForStart();
        while(opModeIsActive())
        {
            shooter.setVelocity(v);
            if(shooter.getVelocity() < max && shooter.getVelocity() > min)
            {
                isFeed = true;
                feeder.feed();
            }else {
                isFeed = false;
                feeder.reverseFeed();
            }

            telemetry.addData("Shooter Velocity", shooter.getVelocity());
            telemetry.addData("Is Feed Active", isFeed);
            telemetry.update();
            controller.update();
        }

    }
}
