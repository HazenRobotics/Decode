package org.firstinspires.ftc.teamcode.NewBot.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeOutake;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;

@TeleOp(name = "Flywheel PIDF test", group = "1 TungTungTungTesting")
public class leFlywheelPIDFtesting extends LinearOpMode {
    public double highVelocity = 1460;
    public double lowVelocity = 1000;
    public double currentTargetVelocity = highVelocity;
    private double P = 0;
    private double F = 0;
    private double[] stepSizes = {10.0, 1.0, 0.1, 0.001, 0.0001};
    private int stepindex;

    @Override
    public void runOpMode() throws InterruptedException {
        LeOutake shooter = new LeOutake(hardwareMap);
        GamepadEvents controller = new GamepadEvents(gamepad1);
        waitForStart();
        while(opModeIsActive())
        {
            if(controller.y.onPress()){
                if(currentTargetVelocity == highVelocity){
                    currentTargetVelocity = lowVelocity;
                }else{
                    currentTargetVelocity = highVelocity;
                }
            }

            if(controller.b.onPress()){
                stepindex = (stepindex + 1) % stepSizes.length;
            }

            if(controller.dpad_left.onPress()){
                F -= stepSizes[stepindex];
            }

            if(controller.dpad_right.onPress()){
                F += stepSizes[stepindex];
            }

            if(controller.dpad_up.onPress()){
                P += stepSizes[stepindex];
            }

            if(controller.dpad_down.onPress()){
                P -= stepSizes[stepindex];
            }

            shooter.updatePID(P, F);
            shooter.setVelocity(currentTargetVelocity);

            double error = currentTargetVelocity - shooter.getVelocity();

            telemetry.addData("Current(AMP)", shooter.getCurrent());
            telemetry.addData("Velocity", shooter.getVelocity());
            telemetry.addData("Target Velocity", currentTargetVelocity);
            telemetry.addData("Error", error);
            telemetry.addData("P", P);
            telemetry.addData("F", F);
            telemetry.addData("StepSize", stepSizes[stepindex]);
            telemetry.update();
            controller.update();
        }
    }
}
