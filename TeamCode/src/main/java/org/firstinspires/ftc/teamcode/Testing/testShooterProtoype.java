package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

@TeleOp(group = "A LeTeleOp", name = "LeShooter Prototype")
public class testShooterProtoype extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Shooter shooter = new Shooter(hardwareMap,"shooter");
        GamepadEvents controller = new GamepadEvents(gamepad1);
        boolean flag = true;
//        Shooter shooter = new Shooter();
        waitForStart();
        telemetry.addLine("Two Motor Mode");
        telemetry.addLine("Left Stick Y to power motors");
        while(opModeIsActive())
        {
            shooter.shoot(controller.left_stick_y);
//            telemetry.addData("RB power",shooter.getPower());



//            if(controller.right_bumper.onPress())
//            {
//                flag = !flag;
//                telemetry.addLine("Mode changed");
//            }
//
//            if(controller.left_bumper.onPress())
//            {
//                shooter.shoot();
//                telemetry.addData("LB power",shooter.getPower());
//            }
//
//            if(flag)
//            {
//                shooter.shoot(Math.abs(controller.left_stick_y));
//                telemetry.addData("RB power",shooter.getPower());
//            }

            controller.update();
            telemetry.update();


        }
    }
}
