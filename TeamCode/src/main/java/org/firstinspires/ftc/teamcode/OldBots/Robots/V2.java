package org.firstinspires.ftc.teamcode.OldBots.Robots;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Feeder;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems.LeMecanum;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;

public class V2 {
    LeMecanum drive;
    Intake intake;
    public double FAR_RPM = 6000, NEAR_RPM = 1000, DEFAULT_SET = 2000, INTAKE_SPEED = -0.9, REVERSE_INTAKE = -0.1;
    Shooter shooter;
    Feeder feeder;
    GamepadEvents controller1, controller2;
    //Timer
    private ElapsedTime timePassed = new ElapsedTime();
    private double applyDeadzone(double v, double d) {
        return Math.abs(v) > d ? v : 0.0;
    }
    private final double LAUNCHER_DELAY = 4, FEED_DELAY = 1, TRANSFER_DELAY = 2; //seconds
    private double shootTime = 0, intakeTime = 0;
    //idk where you got these values from but they work
    final double DEADZONE = 0.05;
    final double SPEED_SCALE = 0.9;

     public boolean isShooting = false, isTransfered = false, isFeeder = false, farShot = true;

    public V2(HardwareMap hw, GamepadEvents controller1, GamepadEvents controller2)
    {
        drive = new LeMecanum(hw);
        shooter = new Shooter(hw, "shooter", true);
        this.controller1 = controller1;
        this.controller2 = controller2;
        this.intake = new Intake(hw);
        feeder = new Feeder(hw, "leftFeeder", "rightFeeder");
    }

    public V2(HardwareMap hw)
    {
        drive = new LeMecanum(hw);
        shooter = new Shooter(hw, "shooter", true);
        this.intake = new Intake(hw);
        feeder = new Feeder(hw, "leftFeeder", "rightFeeder");
    }

    public void drive()
    {
        double forward = -controller1.left_stick_y; // up = positive
        double strafe = controller1.left_stick_x;
        double rotate =controller1.right_stick_x;

        forward = applyDeadzone(forward, DEADZONE) * SPEED_SCALE;
        strafe = applyDeadzone(strafe, DEADZONE) * SPEED_SCALE;
        rotate = applyDeadzone(rotate, DEADZONE) * SPEED_SCALE;

        drive.fieldCentricDrive(-controller1.left_stick_y, controller1.left_stick_x, controller1.right_stick_x);
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
//      if (isShooting)
//      {
//          feeder.toggle();
//          isFeeder = !isFeeder;
//      }
//       else
//       {
//          feeder.reverseFeed();
//      }
        isFeeder = !isFeeder;
        feeder.toggle(isFeeder);
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
            shooter.setVelocity(1780);
            //sleep
            if(elapsed > FEED_DELAY)
            {
                feeder.feed();
            }


        }else {
            shooter.setVelocity(0);
            feeder.reverseFeed();
            timePassed.reset();
        }

    }

    public void shoot(double shoot)
    {
        feeder.feed();
        shooter.setVelocity(shoot);
    }

    public void reverseFeed(){
        feeder.reverseFeed();
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

    public void feederEmoji(Telemetry telemetry)
    {
        if(isFeeder)
        {
            telemetry.addLine("🤪🤪🤪🤪🤪🤪🤪🤪🤪");
//            telemetry.speak("67 67 67 67 67 67");
        }else {
            telemetry.addLine("🇹🇼🇹🇼🇹🇼🇹🇼🇹🇼🇹🇼🇹🇼🇹🇼🇹🇼");
//            telemetry.speak("我需要一辆福特F-150来获得哈兹");
        }
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
