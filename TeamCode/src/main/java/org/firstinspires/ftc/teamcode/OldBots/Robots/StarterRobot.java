package org.firstinspires.ftc.teamcode.OldBots.Robots;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Flap;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems.LeMecanum;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Transfer;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

public class StarterRobot {
    LeMecanum drive;
    Shooter launcher;
    Feeder feeder;
    Intake intake;
    Flap flap;
    Transfer transfer;
    GamepadEvents controller1, controller2;

    //constants
    private final double RPM = 6000, INTAKE_SPEED = 0.8;
    private final double FEED_DELAY = 2, LAUNCHER_DELAY = 1; //seconds
    private final double TRANSFER_DELAY = 3;

    //timer
    private ElapsedTime timePassed = new ElapsedTime();
    private double shootTime = 0;
    private double transferTime = 0;

    private boolean isTransfering = false;
    private boolean isShooting = false;

    public StarterRobot(HardwareMap hw, GamepadEvents controller1, GamepadEvents controller2)
    {
        drive = new LeMecanum(hw);
        launcher = new Shooter(hw, "shooter");
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

    //shooting
    public void shoot() {
        isShooting = true;
        shootTime = timePassed.seconds();

        flap.frontGo();
        flap.backBlock();
        launcher.setRPM(RPM);
    }


    public void updateShooting() {
        if (!isShooting) return;

        double elapsed = timePassed.seconds() - shootTime;

        if (elapsed > LAUNCHER_DELAY) {
            feeder.feed();
        }

        if (elapsed > LAUNCHER_DELAY + FEED_DELAY) {
            feeder.reset();
            launcher.reset();
            isShooting = false;
        }
    }

    //Transfer
    public void transfer() {
        isTransfering = true;
        transferTime = timePassed.seconds();

        transfer.setMotor(1);
        transfer.setServo(-1);
        launcher.setRPM(RPM);
        flap.frontBlock();
    }

    public void updateTransfer() {
        if (!isTransfering) return;

        double elapsed = timePassed.seconds() - transferTime;

        if (elapsed > LAUNCHER_DELAY){
            feeder.feed();
        }

        if (elapsed > LAUNCHER_DELAY + FEED_DELAY) {
            feeder.reset();
            launcher.reset();
        }
        if(elapsed > LAUNCHER_DELAY + FEED_DELAY + TRANSFER_DELAY){
            transfer.setMotor(0);
            transfer.setServo(0);
            isTransfering = false;
        }
    }
}
