package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Transfer {
    DcMotorEx transfer;
    CRServo servo;
    public Transfer(HardwareMap hw){
        transfer = hw.get(DcMotorEx.class, "transfer");
        servo = hw.get(CRServo.class, "transferServo");
    }
    public void setMotor(double power){
        transfer.setPower(power);
    }
    public void setServo(double power){
        servo.setPower(power);
    }
}
