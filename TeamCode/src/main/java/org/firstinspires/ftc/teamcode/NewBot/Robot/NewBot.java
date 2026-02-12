package org.firstinspires.ftc.teamcode.NewBot.Robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeCameraServo;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeIntake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeLED;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeMecanum;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeOutake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeStopper;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeTransfer;
import org.firstinspires.ftc.teamcode.NewBot.Utils.ColorSensor;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.NewBot.Utils.VelocityCalculator2;
import org.firstinspires.ftc.teamcode.NewBot.Vision.LogitechCam;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

//Driver Automations to be implemented
public class NewBot {
    LeCameraServo cameraServo;
    LeIntake intake;
    LogitechCam camera;
    LeLED led;
    LeMecanum drive;
    LeOutake flywheel;
    VelocityCalculator2 calculator;
    LeStopper stopper;
    double bearingDeg;
    int TARGET_TAG_ID = 20;
    LeTransfer transfer;
    double rotation = 1;
    double weight = 0.25;
    double velocity = 1200;
    public enum STATES
    {
        SHOOT, STORE, INTAKE
    }
    private STATES state = STATES.INTAKE;
    GamepadEvents controller1, controller2;
    public NewBot(HardwareMap hw, Telemetry telemetry, int id)
    {
        cameraServo = new LeCameraServo(hw);
        intake = new LeIntake(hw);
        led = new LeLED(hw);
        drive = new LeMecanum(hw);
        flywheel = new LeOutake(hw);
        stopper = new LeStopper(hw);
        TARGET_TAG_ID = id;
        transfer = new LeTransfer(hw);
        camera = new LogitechCam();
        calculator = new VelocityCalculator2();
        camera.init(hw, telemetry);
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);
    }


    public void drive(double x, double y, double r)
    {
        drive.drive(x, -y, -r * rotation);
    }

    public void runShooter()
    {
        AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);
        if(targetTag == null)
        {
//            flywheel.setVelocity(calculator.setVelocityWhenItDoesNotSeeAPRIlTag());
            flywheel.setVelocity(velocity);
        }else
        {
//            flywheel.setVelocity(calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
            flywheel.setVelocity(velocity);
        }

    }

    public void intake()
    {
        state = STATES.INTAKE;
        intake.feed();
        flywheel.setVelocity(velocity);
        stopper.block();
        transfer.setPower();
    }

//    public void leftLEDIndicator()
//    {
//        camera.update();
//        AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);
//        if(targetTag != null)
//        {
//
//            led.setleftLEDColor(LeLED.Colors.PINK);
//        }else {
//            led.setleftLEDColor(LeLED.Colors.BLUE);
//        }
//    }

    public void rightLEDIndicator()
    {
        //Pseudo Code
        led.setRightLEDColor(LeLED.Colors.ORANGE);
    }


    public void shoot()
    {
        state = STATES.SHOOT;
        runShooter();
//        AutoAlign(false);
        transfer.setPower();
        intake.feed();
        stopper.lift();
    }

    public void toggleShootStore()
    {
        if(state == STATES.INTAKE)
        {
            shoot();
        }else if(state == STATES.SHOOT)
        {
            store();
        }
        else if(state == STATES.STORE)
        {
            intake();
        }
    }

    //May have to move ElapsedTIme to TeleOP to fix LED issue
    public void ledCrazy()
    {

        if(weight <= 0.8)
        {

                weight += 0.05;
                led.setColor(weight);
        }else {
            weight = 0.25;
            led.setColor(weight);
        }

    }

    public void toggleLED()
    {
        if(state == STATES.INTAKE)
        {
            led.setColor(LeLED.Colors.BLUE);
        }else if(state == STATES.SHOOT)
        {
            led.setColor(LeLED.Colors.PINK);
        }
        else if(state == STATES.STORE)
        {
            led.setColor(LeLED.Colors.GREEN);
        }
    }

    public void parkLed(LeLED.Colors color)
    {
        led.setColor(color);
        led.setRightLEDColor(color);
        led.setleftLEDColor(color);
    }

    public void greenLed()
    {
        led.setRightLEDColor(LeLED.Colors.GREEN);
    }
    public void reverseTransfer()
    {
        transfer.reverseMotor();
    }

    public void reverseIntake()
    {
        intake.reverse();
    }

    public void store()
    {
        state = STATES.STORE;
        intake.stop();
        flywheel.setVelocity(0);
        transfer.stop();
        stopper.block();
    }

    public void calculateVelocity()
    {
        camera.update();
        AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);
        flywheel.setVelocity(calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
    }

    public void AutoAlign(boolean canAlign)
    {
        camera.update();
        AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);
        if (canAlign)
        {

            // April Tag Notice
            if (targetTag != null)
            {
                flywheel.setVelocity(calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
                led.setleftLEDColor(LeLED.Colors.PINK);
                bearingDeg = camera.getBearing(targetTag);

                // Rotatating toward AprilTag while driver drives or strafes
                if (bearingDeg > 5)
                {

                    drive.drive(0,0, 0.3);

                }
                else if (bearingDeg < 4)
                {
                    drive.drive(0,0, -0.3);

                }
                rotation = 0;


            }else if(targetTag == null)
            {
                flywheel.setVelocity(calculator.setVelocityWhenItDoesNotSeeAPRIlTag());
                led.setleftLEDColor(LeLED.Colors.YELLOW);
            }

        }else
        {
            flywheel.setVelocity(calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
            rotation = 1;
            led.setleftLEDColor(LeLED.Colors.BLUE);
        }
    }

    public void reverseShooter()
    {
        camera.update();
        AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);
        flywheel.setVelocity(-calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
    }

    public void adJustFlywheel(double value)
    {
//        calculator.adjustDistance(value);
        velocity += value;

    }
    //No workie now :(
    public void adJustFlywheel(GamepadEvents controller)
    {
        if(controller.dpad_up.onPress())
        {
            calculator.adjustDistance(50);
        }

        if(controller.dpad_down.onPress())
        {
            calculator.adjustDistance(-50);
        }
    }

    public String getData()
    {
        return "Left bumper to toggle transfer and feed" +"\nPress X to reverse transfer\""
                + "\nPress Right Bumper to toggle shooting and storing"
                + "\n\"Press B to Reverse shooter"
                + "\nDPAD UP to increase velocity\nDPAD DOWN to decrease velocity"
                +"\nPress Y to AutoAlign"
                + "\nVelocity: " + flywheel.getVelocity()
                + "\nTransfer Power: " + transfer.getData()
                +"\n Bearing Tag: "+ bearingDeg
                +"\n Controller 2 Controls: "
                +"\n Right Bumper to toggle Green and Orange: "
                +"\n Adjust FLywheel speed using up and down dpads"
        +"\n X to Reverse Transfer"
        +"\n Y to Reverse Intake";
    }


}
