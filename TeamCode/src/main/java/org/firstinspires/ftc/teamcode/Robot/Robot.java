package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Flap;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.SubSystems.Transfer;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

public class Robot {
    MecanumDrive drive;
    Shooter launcher;
    Feeder feeder;
    Intake intake;
    Flap flap;
    Transfer transfer;
    GamepadEvents controller1, controller2;

    //constants
    private final double RPM = 4000, INTAKE_SPEED = 0.8;
    private final double FEED_DELAY = 0.6, LAUNCHER_DELAY = 0.6; //seconds

    //timer
    private ElapsedTime stateTimer = new ElapsedTime();
    private boolean isTransfering = false;
    private boolean isShooting = false;

    public Robot(HardwareMap hw, GamepadEvents controller1, GamepadEvents controller2)
    {
        drive = new MecanumDrive(hw);
        //drive = new TankDrive(hw);
        launcher = new Shooter(hw, "leftShooter");
        this.controller1 = controller1;
        this.controller2 = controller2;
        flap = new Flap(hw, "frontFlap", "backFlap");
        transfer = new Transfer(hw);
        feeder = new Feeder(hw);
        intake = new Intake(hw);
    }

    public void drive() {
        drive.drive(controller1.left_stick_y, controller1.left_stick_x, -controller1.right_stick_x);
    }

    //intake
    public void intake() {
        flap.backBlock();
        intake.intakeToggle(INTAKE_SPEED);
    }

    //shooting sequence in seconds
    public void shoot() {
        isTransfering = true;
        stateTimer.reset();

        flap.frontGo();
        flap.backBlock();
        launcher.setRPM(RPM);
    }

    //transfer sequence in seconds
    public void updateShooting() {
        if (!isShooting) return;

        double t = stateTimer.seconds();

        if (t > LAUNCHER_DELAY) {
            feeder.feed();
        }

        if (t > LAUNCHER_DELAY + FEED_DELAY) {
            feeder.reset();
            launcher.reset();
            isShooting = false;
        }
    }

    //Transfer
    public void transfer() {
        isTransfering = true;
        stateTimer.reset();

        transfer.setMotor(1);
        transfer.setServo(-1);
        launcher.setRPM(RPM);
        flap.frontBlock();
    }

    public void updateTransfer() {
        if (!isTransfering) return;

        double t = stateTimer.seconds();

        if (t > LAUNCHER_DELAY) {
            feeder.feed();
        }

        if (t > LAUNCHER_DELAY + FEED_DELAY) {
            transfer.setMotor(0);
            transfer.setServo(0);
            feeder.reset();
            launcher.reset();
            isTransfering = false;
        }
    }

}

