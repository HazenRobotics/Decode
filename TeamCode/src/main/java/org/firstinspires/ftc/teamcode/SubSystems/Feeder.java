package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Feeder {
    CRServo leftFeeder, rightFeeder;
    double speed = 1;
    public Feeder(HardwareMap hw){
        leftFeeder = hw.get(CRServo.class,"leftFeeder");
        rightFeeder = hw.get(CRServo.class,"rightFeeder");

        //Setting Direction has had no effect
//        leftFeeder.setDirection(Servo.Direction.FORWARD);
        rightFeeder.setDirection(CRServo.Direction.REVERSE);

    }
    public void feed(){
        leftFeeder.setPower(speed);
        rightFeeder.setPower(speed);
    }

    public void reset(){
        leftFeeder.setPower(0);
        rightFeeder.setPower(0);
    }

    public void feed(double speed){
        leftFeeder.setPower(speed);
        rightFeeder.setPower(speed);
    }


    public String getData()
    {
        return "Left feeder: "+ leftFeeder.getPower() + "\n" + "Right feeder: " + rightFeeder.getPower();
    }
}
