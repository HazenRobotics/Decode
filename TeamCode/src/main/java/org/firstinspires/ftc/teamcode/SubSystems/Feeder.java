package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Feeder {
    CRServo leftFeeder, rightFeeder;
    public Feeder(HardwareMap hw){
        leftFeeder = hw.get(CRServo.class,"leftFeeder");
        rightFeeder = hw.get(CRServo.class,"rightFeeder");
        leftFeeder.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void feed(){
        leftFeeder.setPower(1);
        rightFeeder.setPower(1);
    }
}
