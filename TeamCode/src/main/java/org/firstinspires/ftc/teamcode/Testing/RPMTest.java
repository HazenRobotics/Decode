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
        double v = 2200;
        while(opModeIsActive())
        {
            if(controller.dpad_up.onPress())
            {
                v += 100;
            }
            if(controller.dpad_down.onPress())
            {
                v -= 100;
            }
            if(controller.a.onPress()){
                shooter.setVelocity(v);
            }

            telemetry.addLine("Button a to shoot");
            telemetry.addData("Velocity", v);
            telemetry.addData("Shooter Voltage", shooter.getVoltageNormalizedVelocity(1800));
            telemetry.addData("Voltage:", shooter.getVoltage());
            telemetry.update();
            controller.update();
        }
    }
}
