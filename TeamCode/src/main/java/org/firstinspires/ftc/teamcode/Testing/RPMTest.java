package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.utils.optimalRPM;
@TeleOp(group = "A LeTeleOp", name = "RPMTest")
public class RPMTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Shooter shooter = new Shooter(hardwareMap, "shooter", true);
        GamepadEvents controller = new GamepadEvents(gamepad1);
        Intake intake = new Intake(hardwareMap);
        Feeder feeder = new Feeder(hardwareMap, "leftFeeder", "rightFeeder");
        waitForStart();
        double v = 1350;
        while(opModeIsActive())
        {
            if(controller.dpad_up.onPress())
            {
                v += 10;
            }
            if(controller.dpad_down.onPress())
            {
                v -= 10;
            }
            if(controller.a.onPress()){
                feeder.feed();
                intake.setPower(-0.8);
                shooter.setVelocity(v);
            }

            telemetry.addData("Current(AMP)", shooter.getCurrent());
            telemetry.addLine("Button a to shoot");
            telemetry.addData("V", v);
            telemetry.addData("Velocity", shooter.getVelocity());
            telemetry.addData("Shooter Voltage", shooter.getVoltageNormalizedVelocity(v));
            telemetry.addData("Voltage:", shooter.getVoltage());
            telemetry.update();
            controller.update();
        }
    }
}
