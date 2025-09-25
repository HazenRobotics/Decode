package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.SubSystems.TankDrive;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

public class StarterRobot {
    TankDrive drive;
    Shooter launcher;
    GamepadEvents controller1, controller2;
    Feeder feeder;
    final long FEED_TIME_MILISECONDS = 400,  LAUNCHER_TIME_MILLISECONDS = 500;
    public StarterRobot(HardwareMap hw, GamepadEvents controller1, GamepadEvents controller2)
    {
        drive = new TankDrive(hw);
        //drive = new MecanumDrive(hw);
        launcher = new Shooter(hw, "leftShooter");
        this.controller1 = controller1;
        this.controller2 = controller2;
        feeder = new Feeder(hw);

    }

    public void drive()
    {
        drive.drive(controller1.left_stick_y, -controller1.right_stick_x);
    }
    //Mech Drive drive + imu reset method
//    public void drive()
//    {
//        drive.drive(controller1.left_stick_y, controller1.left_stick_x, -controller1.right_stick_x);
//    }
//
//    public void resetHeading()
//    {
//        drive.resetHeading();
//    }

    public void shoot() throws InterruptedException {

        if(controller1.left_bumper.onPress())
        {
            //try threads to allow multiple functions running at same time
            Thread thread = new Thread(() -> {
                launcher.shoot();

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
        }





}
