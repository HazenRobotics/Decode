package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.optimalRPM;
@TeleOp(group = "A LeTeleOp", name = "RPMTest")
public class RPMTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Shooter shooter = new Shooter(hardwareMap,"leftMotor", "rightMotor");
        waitForStart();
        while(opModeIsActive())
        {
            shooter.shoot(optimalRPM.getRPM(3.5));
        }
    }
}
