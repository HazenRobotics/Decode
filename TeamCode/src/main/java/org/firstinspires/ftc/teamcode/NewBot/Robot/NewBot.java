package org.firstinspires.ftc.teamcode.NewBot.Robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

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
import org.firstinspires.ftc.teamcode.NewBot.Vision.LogitechCam;

//Driver Automations to be implemented
public class NewBot {
    LeCameraServo cameraServo;
    LeIntake intake;
    LogitechCam camera;
    LeLED led;
    LeMecanum drive;
    LeOutake flywheel;
    LeStopper stopper;
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
        camera.init(hw, telemetry);
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);
    }

    public void drive()
    {
        drive.drive(-controller1.left_stick_y, controller1.left_stick_x, controller1.right_stick_x);
    }


}
