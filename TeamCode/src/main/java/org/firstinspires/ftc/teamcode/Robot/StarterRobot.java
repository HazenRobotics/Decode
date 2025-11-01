package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Flap;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.SubSystems.TankDrive;
import org.firstinspires.ftc.teamcode.SubSystems.Transfer;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

public class StarterRobot {
    MecanumDrive drive;
    Shooter launcher;
    Feeder feeder;
    Intake intake;
    Flap flap;
    Transfer transfer;
    GamepadEvents controller1, controller2;

    //constants
    private final double RPM = 6000, INTAKE_SPEED = 0.8;
    private final double FEED_DELAY = 2, LAUNCHER_DELAY = 1, LOAD_DELAY = 1.5; //seconds
    //Reverse
    private final double RFEED_DELAY = 0.5, RLAUNCHER_DELAY = 0.5, RTRANSFER_DELAY = 0.2;
    private final double TRANSFER_DELAY = 1;

    //timer
    private ElapsedTime timePassed = new ElapsedTime();
    private double shootTime = 0;
    private double transferTime = 0;
    private double loadTime = 0;
    private double reverseTime = 0;

    private boolean isTransfering = false;
    private boolean isShooting = false;
    private boolean isLoading = false;
    private boolean reverse = false;

    public StarterRobot(HardwareMap hw, GamepadEvents controller1, GamepadEvents controller2)
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
        drive.drive(controller1.left_stick_y, -controller1.left_stick_x, controller1.right_stick_x);
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
        launcher.setVelocity(1800);
    }


    public void updateShooting() {
        if (!isShooting) return;

        double elapsed = timePassed.seconds() - shootTime;

        if (elapsed > LAUNCHER_DELAY && launcher.getVelocity() > 1790) {
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
        launcher.setVelocity(1800);
        flap.frontBlock();
    }

    public void updateTransfer() {
        if (!isTransfering) return;
        double elapsed = timePassed.seconds() - transferTime;

        if (elapsed > LAUNCHER_DELAY && launcher.getVelocity() > 1790){
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

    public void load(){
        isLoading = true;
        flap.backDown();
        launcher.setVelocity(400);
        loadTime = timePassed.seconds();
    }

    public void updateLoad(){
        if(!isLoading) return;
        double elapsed = timePassed.seconds() - loadTime;
        if(elapsed > LOAD_DELAY){
            flap.backBlock();
        }
        if(elapsed > LOAD_DELAY + 0.5){
            feeder.feed(-1);
            launcher.setVelocity(-200);
        }
        if(elapsed > LOAD_DELAY + 0.5 + 0.6 ){
            feeder.reset();
            launcher.reset();
            isLoading = false;
        }
    }

    public void reverseTrasfer(){
        reverse = true;
        flap.backBlock();
        reverseTime = timePassed.seconds();
        transfer.setServo(1);
        transfer.setMotor(-1);
    }

    public void updateReverseTransfer(){
        if(!reverse) return;
        double elapsed = timePassed.seconds() - reverseTime;
        if(elapsed > RLAUNCHER_DELAY){
            launcher.setVelocity(1800);
        }
        if(elapsed > RLAUNCHER_DELAY + RFEED_DELAY){
            feeder.feed(-0.3);
            launcher.reset();
            transfer.setMotor(0);
            transfer.setServo(0);
        }
        if(elapsed > RLAUNCHER_DELAY + RFEED_DELAY + RTRANSFER_DELAY){
            feeder.reset();
            reverse = false;
        }
    }

}
