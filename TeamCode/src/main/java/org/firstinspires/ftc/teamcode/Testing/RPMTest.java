package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.utils.optimalRPM;
@TeleOp(group = "A LeTeleOp", name = "RPMTest")
public class RPMTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Shooter shooter = new Shooter(hardwareMap, "leftShooter");
        GamepadEvents controller = new GamepadEvents(gamepad1);
        waitForStart();
        double rpm = 4000;
        while(opModeIsActive())
        {
//            if(controller.dpad_up.onPress())
//            {
//                rpm += 50;
//            }
//            if(controller.dpad_down.onPress())
//            {
//                rpm -= 50;
//            }
//            shooter.setRPM(rpm);
            double calculatedRPM = shooter.calculateTargetRPM(2.72, 1.1, 50);
            if(controller.a.onPress()){
                shooter.setVelocity(1800);
            }

            telemetry.addData("RPM", shooter.getVelocity());
            telemetry.addData("Power: ", shooter.getPower() * 6000);
            telemetry.update();
            controller.update();
        }
    }
}
