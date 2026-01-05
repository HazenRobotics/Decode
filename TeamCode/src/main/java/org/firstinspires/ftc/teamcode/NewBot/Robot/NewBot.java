package org.firstinspires.ftc.teamcode.NewBot.Robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

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
    public NewBot(HardwareMap hw, Telemetry telemetry)
    {
        cameraServo = new LeCameraServo(hw);
        intake = new LeIntake(hw);
        led = new LeLED(hw);
        drive = new LeMecanum(hw);
        flywheel = new LeOutake(hw);
        stopper = new LeStopper(hw);
        transfer = new LeTransfer(hw);
        camera = new LogitechCam();
        calculator = new VelocityCalculator2();
        camera.init(hw, telemetry);
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);
    }

    public void drive()
    {
        drive.fieldCentricDrive(-controller1.left_stick_y, controller1.left_stick_x, controller1.right_stick_x);
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

    public void shoot()
    {
        runShooter();
        transfer.setPower();
        stopper.lift();
    }

    public void store()
    {
        intake.stop();
        transfer.stop();
        stopper.block();
    }




}
