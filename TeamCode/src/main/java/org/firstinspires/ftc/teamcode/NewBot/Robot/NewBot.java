package org.firstinspires.ftc.teamcode.NewBot.Robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeCameraServo;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeIntake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeLED;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeMecanum;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeOutake;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeStopper;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeTransfer;
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
    int TARGET_TAG_ID = 20;
    LeTransfer transfer;
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
        drive.drive(-x, y, r);
    }

    public void runShooter()
    {
        AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);
        flywheel.setVelocity(calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
    }

    public void intake()
    {
        intake.feed();
        stopper.block();
        transfer.setPower();
    }

    public void leftLEDIndicator()
    {
        camera.update();
        AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);
        if(targetTag != null)
        {

            led.setleftLEDColor(LeLED.Colors.PINK);
        }else {
            led.setleftLEDColor(LeLED.Colors.BLUE);
        }
    }

    public void rightLEDIndicator()
    {
        if(calculator.checkIfDefaultValue())
        {
            led.setRightLEDColor(LeLED.Colors.ORANGE);
        }else
        {
            led.setRightLEDColor(LeLED.Colors.GREEN);
        }
    }


    public void shoot()
    {
        runShooter();
        transfer.setPower();
        intake.feed();
        stopper.lift();
    }

    public void reverseTransfer()
    {
        transfer.reverseMotor();
    }

    public void store()
    {
        intake.stop();
        transfer.stop();
        stopper.block();
    }

    public void calculateVelocity()
    {
        camera.update();
        AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);
        flywheel.setVelocity(calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
    }

    public void reverseShooter()
    {
        camera.update();
        AprilTagDetection targetTag = camera.getTagBySpecificId(TARGET_TAG_ID);
        flywheel.setVelocity(-calculator.calculateVelocityForTarget(camera.getHorizontalData(targetTag)));
    }


    public void cameraServoSetPosition(GamepadEvents controller)
    {
        if(controller.dpad_left.onPress())
        {
            cameraServo.setPositon(cameraServo.getPositon() + 0.05);
        }

        if(controller.dpad_left.onPress())
        {
            cameraServo.setPositon(cameraServo.getPositon() - 0.05);
        }
    }

    public String getData()
    {
        return "Left bumper to toggle transfer and feed" +"\nPress X to reverse transfer\""
                +"\nPress Y to store ball" + "\nPress Right Bumper to toggle shooting"
                + "\n\"Press B to Reverse shooter"
                + "\nDPAD UP to increase velocity\nDPAD DOWN to decrease velocity"
                + "\nVelocity: " + flywheel.getVelocity()
                + "\nTransfer Power: " + transfer.getData();
    }


}
