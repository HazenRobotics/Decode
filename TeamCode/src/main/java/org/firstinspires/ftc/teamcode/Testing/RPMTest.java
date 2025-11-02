package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.utils.optimalRPM;
@TeleOp(group = "A LeTeleOp", name = "RPMTest")
public class RPMTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Shooter shooter = new Shooter(hardwareMap, "shooter", true);
        Feeder feeder = new Feeder(hardwareMap);
        GamepadEvents controller = new GamepadEvents(gamepad1);
        waitForStart();
        double rpm = 3000;
        while(opModeIsActive())
        {
            if(controller.dpad_up.onPress())
            {
                rpm += 100;
            }
            if(controller.dpad_down.onPress())
            {
                rpm -= 100;
            }
            if(controller.left_bumper.onPress())
            {
                feeder.feed();
            }

//            if(controller)
            shooter.setShooterRPM(rpm);

//            shooter.shoot(optimalRPM.getRPM(3.5));
            telemetry.addData("RPM: ", shooter.getCurrentRPM());
            telemetry.addLine("DPAD UP to Increase RPM, Decrease using DPAD DOWN");
            telemetry.addLine("Left_Bumper to turn on FEEDER");
            telemetry.update();
            controller.update();
        }
    }
}
