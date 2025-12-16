package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.LED;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

//@TeleOp(group = "Tester", name = "ThreeBall LED")
public class CheckThreeBallLED extends LinearOpMode {
    Shooter shooter;
    Intake intake;
    Feeder feeder;
    LED led;
    GamepadEvents controller1;
    boolean intakeSpike = false;
    boolean shooterSpike = false;
    int count = 0;
    double highestSpike = 0;
    ElapsedTime time;


    @Override
    public void runOpMode() throws InterruptedException {
        shooter = new Shooter(hardwareMap);
        intake = new Intake(hardwareMap);
        feeder = new Feeder(hardwareMap);
        led = new LED(hardwareMap);
        controller1 = new GamepadEvents(gamepad1);
        time = new ElapsedTime();
        waitForStart();
        while(opModeIsActive()) {
            if (controller1.right_bumper.onPress()) {
                intake.setPower(-0.8);

            }


            if (controller1.left_bumper.onPress())
            {
                shooter.setVelocity(1400);
            }

            if (controller1.a.onPress())
            {
                feeder.feed();
            }

            //Intake surpases 1.5 Amps, then drops down 1.5 Amps
            if (intake.getCurrent() > 1.5) {
                intakeSpike = true;

            }



            if (intakeSpike && intake.getCurrent() < 1.5 && time.seconds() > 1) {
                intakeSpike = false;
                count++;
                time.reset();
            }

            if (shooter.getCurrent() > 1.5 && feeder.isFeed)
            {
                shooterSpike = true;
            }

            if(shooterSpike && shooter.getCurrent() < 1.5 && time.seconds() > 1)
            {
                shooterSpike = false;
                time.reset();
                count --;
            }

//            if(count == 3)
//            {
//                led.setColor(RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN);
//            }
//            //Check for issues
//            if(count > 3)
//            {
//                led.setColor(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
//            }
//
//            if(count < 3)
//            {
//                led.setColor(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
//            }

            if(intake.getCurrent() > highestSpike)
            {
                highestSpike = intake.getCurrent();
            }
            telemetry.addData("Ball Count", count);
            telemetry.addData("Intake current", intake.getCurrent());
            telemetry.addData("Flywheel current", shooter.getCurrent());
            telemetry.addData("Highest Spike", highestSpike);
            telemetry.update();
            controller1.update();

        }
    }
}
