package org.firstinspires.ftc.teamcode.NewBot.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeTransfer;

@TeleOp(name = "LeTrasnferTester", group = "1 TungTungTungTesting")
public class LeTransferTester extends LinearOpMode {
    LeTransfer transfer;
    GamepadEvents controller;
    double power = 0;
    @Override
    public void runOpMode() throws InterruptedException
    {
        transfer = new LeTransfer(hardwareMap);
        controller = new GamepadEvents(gamepad1);

        waitForStart();
        while(opModeIsActive())
        {
            if(controller.left_bumper.onPress())
            {
                power += 0.1;
            }

            if(controller.right_bumper.onPress())
            {
                power -= 0.1;
            }
            transfer.setPower(power);


            telemetry.addLine("Press Left bumper to increase power\nPress Right Bumper to decrease power");
            telemetry.addData("Power", transfer.getPower());
            telemetry.update();
            controller.update();
        }
    }
}
