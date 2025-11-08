package org.firstinspires.ftc.teamcode.Robots;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.LED;
import org.firstinspires.ftc.teamcode.SubSystems.Mecanum;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

public class V2 {
    Mecanum drive;
    Intake intake;
    public double FAR_RPM = 6000, NEAR_RPM = 1000, DEFAULT_SET = 2000, INTAKE_SPEED = 0.9, REVERSE_INTAKE = -0.1;
    Shooter shooter;
    Feeder feeder;
    GamepadEvents controller1, controller2;
    //Timer
    private ElapsedTime timePassed = new ElapsedTime();
    private final double LAUNCHER_DELAY = 4, FEED_DELAY = 1, TRANSFER_DELAY = 2; //seconds
    private double shootTime = 0, intakeTime = 0;

    private boolean isShooting = false, isTransfered = false;
    public V2(HardwareMap hw, GamepadEvents controller1, GamepadEvents controller2)
    {
        drive = new Mecanum(hw);
        shooter = new Shooter(hw, "shooter", true);
        this.controller1 = controller1;
        this.controller2 = controller2;
        this.intake = new Intake(hw, "left", "right");
        feeder = new Feeder(hw, "leftFeeder", "rightFeeder");
    }

    public V2(HardwareMap hw)
    {
        drive = new Mecanum(hw);
        shooter = new Shooter(hw, "shooter", true);
        this.intake = new Intake(hw, "left", "right");
        feeder = new Feeder(hw, "leftFeeder", "rightFeeder");
    }

    public void drive()
    {
        drive.drive(controller1.left_stick_y, -controller1.left_stick_x, controller1.right_stick_x);
    }

    public void setDriveSpeed(double speed)
    {
        drive.setForwardConst(speed);
    }

    public void drive(double forward, double strafe, double rotate)
    {
        drive.drive(forward, strafe, rotate);
    }

    public void intake()
    {

        intake.intakeToggle(INTAKE_SPEED);

    }

    public void reverseIntake()
    {

        intake.intakeToggle(-INTAKE_SPEED);

    }



    public void toggleFeed()
    {
        feeder.toggle();
    }


    public void shoot()
    {
        isShooting = true;

        double elapsed = timePassed.seconds();

        if(controller1.left_bumper.onPress())
        {
            isTransfered = !isTransfered;
        }

        shootTime = timePassed.seconds();
        if(isTransfered)
        {
            shooter.setShooterRPM(FAR_RPM);
            //sleep
            if(elapsed > FEED_DELAY)
            {
                feeder.feed();
            }


        }else {
            shooter.setShooterRPM(-FAR_RPM/10);
            feeder.reverseFeed();
            timePassed.reset();
        }

    }

    public void shoot(double shoot)
    {
        shooter.setShooterRPM(6000*shoot);
    }

    public void setRPM(double rpm)
    {
        FAR_RPM += rpm;
    }

    public void multiplyRPM(double mult)
    {
        FAR_RPM *= mult;
    }

    public double getRPM()
    {
        return FAR_RPM;
    }


    public void updateShooting()
    {
        if (!isShooting) return;

        double elapsed = timePassed.seconds() - shootTime;

        if (elapsed > LAUNCHER_DELAY)
        {
            feeder.feed();
        }

        if (elapsed > LAUNCHER_DELAY + FEED_DELAY) {
            feeder.reset();
            shooter.reset();
            isShooting = false;
        }
    }


}
