package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private String name = "intake";
    DcMotorEx intake;
    public Intake(HardwareMap hw)
    {
        intake = hw.get(DcMotorEx.class, name);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public Intake(HardwareMap hw, String name)
    {
        intake = hw.get(DcMotorEx.class, name);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPower(double power)
    {
        intake.setPower(power);
    }

    public double getPower()
    {
        return intake.getPower();
    }
    public String getData() {
        return "Intake power: " + intake.getPower();
    }

}
