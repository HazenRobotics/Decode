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
    private final double RPM = 6000, INTAKE_SPEED = 0.8;
    Feeder feeder;
    Shooter shooter;
    GamepadEvents controller1, controller2;

    //Timer
    private ElapsedTime timePassed = new ElapsedTime();
    private double shootTime = 0;
    private final double LAUNCHER_DELAY = 2; //seconds
    private double transferTime = 2;

    private boolean isTransfering = false;
    private boolean isShooting = false;
    public V2(HardwareMap hw, GamepadEvents controller1, GamepadEvents controller2)
    {
        drive = new Mecanum(hw);
        shooter = new Shooter(hw, "shooter");
        this.controller1 = controller1;
        this.controller2 = controller2;
        this.intake = new Intake(hw, "left", "right");
    }

    public void drive()
    {
        drive.drive(controller1.left_stick_y, controller1.left_stick_x, -controller1.right_stick_x);
    }

    public void intake()
    {
        intake.intakeToggle(INTAKE_SPEED);
    }

    public void shoot()
    {
        shooter.setRPM(RPM);
    }

    public void intakeAndShoot()
    {
        intake();
        double elapsed = timePassed.seconds();

        if(elapsed >= transferTime)
        {
            shoot();
            intake.intakeToggle(INTAKE_SPEED);

        }

        if(elapsed >= transferTime + LAUNCHER_DELAY)
        {
            shooter.setRPM(0);
        }

    }


}
