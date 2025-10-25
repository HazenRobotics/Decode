package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

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
    GamepadEvents controller1, controller2;
    Feeder feeder;
    Intake intake;
    Flap flap;
    Transfer transfer;
    private final double RPM = 4000, INTAKE_SPEED = 0.8;
    final long FEED_TIME_MILISECONDS = 600,  LAUNCHER_TIME_MILLISECONDS = 600;

    public enum RobotStates
    {
        IDLE, AUTO, SCORING, END_GAME;

        public enum IdleStates
        {

        }

        public enum AutoStates
        {

        }

        public enum SCORING
        {

        }

        public enum END_GAME
        {

        }
    }
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
    //comment out
//    public void drive()
//    {
//        drive.drive(controller1.left_stick_y, -controller1.right_stick_x);
//    }
    //Mech Drive drive + imu reset method
    public void drive()
    {
        drive.drive(controller1.left_stick_y, controller1.left_stick_x, -controller1.right_stick_x);
    }

//    public void resetHeading()
//    {
//        drive.resetHeading();
//    }
    public void intake()
    {
        flap.backBlock();
        intake.setPower(INTAKE_SPEED);
    }
    public void intakeAndShoot() throws InterruptedException {
        intake();
        Thread.sleep(FEED_TIME_MILISECONDS);
        shoot();
    }
    public void intakeAndTransfer() throws InterruptedException {
        intake();
        Thread.sleep(FEED_TIME_MILISECONDS);
        transfer();
    }
    public void shoot() throws InterruptedException {
            //try threads to allow multiple functions running at same time
            Thread thread = new Thread(() -> {
//                launcher.shoot();
                flap.frontGo();
                flap.backBlock();
                launcher.setRPM(RPM);
                try {
                    Thread.sleep(LAUNCHER_TIME_MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                feeder.feed();

                try {

                    Thread.sleep(FEED_TIME_MILISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                feeder.reset();
                launcher.reset();

            });

            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    public void transfer() throws InterruptedException {
        Thread thread = new Thread(() -> {
            transfer.setMotor(1);
            transfer.setServo(-1);
            launcher.setRPM(RPM);
            flap.frontBlock();
            try {
                Thread.sleep(LAUNCHER_TIME_MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            feeder.feed();

            try {
                Thread.sleep(FEED_TIME_MILISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            transfer.setMotor(0);
            transfer.setServo(0);
            feeder.reset();
            launcher.reset();
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


    public String getData() {
        return "FEEDER\n" + feeder.getData() + "\n" +
                "INTAKE\n" + intake.getData() + "\n" +
                "SHOOTER\n" + launcher.getData() + "\n";
    }






}
