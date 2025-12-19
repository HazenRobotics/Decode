package org.firstinspires.ftc.teamcode.LeScarab.TungTungTungTester;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;
import org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems.LeIntake;
import org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems.LeOutake;
import org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems.LeTransfer;
@TeleOp(name = "LeEverythingTester", group = "1 TungTungTungTesting")
public class LeTransferTester extends LinearOpMode {
    LeTransfer transfer;
    LeOutake flywheel;
    LeIntake intake;
    GamepadEvents controller;
    int velocity = 1500;
    boolean canShoot = false;
    @Override
    public void runOpMode() throws InterruptedException
    {
        transfer = new LeTransfer(hardwareMap);
        controller = new GamepadEvents(gamepad1);
        intake = new LeIntake(hardwareMap);
        flywheel = new LeOutake(hardwareMap);


        waitForStart();
        while(opModeIsActive())
        {
            if(controller.left_bumper.onPress())
            {
                transfer.togglePower();
                intake.feed();
            }



            if(controller.right_bumper.onPress())
            {
                transfer.reverseMotor();
            }

            if(controller.y.onPress())
            {
                canShoot = !canShoot;
            }

            if(canShoot)
            {
                flywheel.setVelocity(velocity);
            }

            if(controller.dpad_up.onPress())
            {
                velocity += 25;
            }

            if(controller.dpad_down.onPress())
            {
                velocity -= 25;
            }

            telemetry.addLine("Left bumper to toggle transfer and feed");
            telemetry.addLine("Right bumper to reverse transfer");
            telemetry.addLine("Press Y to toggle shooting");
            telemetry.addLine("DPAD UP to increase velocity\nDPAD DOWN to decrease velocity");
            telemetry.update();
            controller.update();
        }
    }
}
