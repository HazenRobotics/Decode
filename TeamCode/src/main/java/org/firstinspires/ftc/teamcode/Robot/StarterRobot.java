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
    final long FEED_TIME_MILISECONDS = 2000;
    public StarterRobot(HardwareMap hw, GamepadEvents controller1, GamepadEvents controller2)
    {
        drive = new TankDrive(hw);
        launcher = new Shooter(hw, "leftShooter");
        this.controller1 = controller1;
        this.controller2 = controller2;
        feeder = new Feeder(hw);

    }

    public void drive()
    {
        drive.drive(controller1.left_stick_y, -controller1.right_stick_x);
    }

    public void shoot() throws InterruptedException {
        feeder.feed();
//        if(controller1.left_bumper.onPress())
//        {
//            feeder.feed();
//            Thread.sleep(FEED_TIME_MILISECONDS);
////            launcher.shoot();
//        }


    }
}
