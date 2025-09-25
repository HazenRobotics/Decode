package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Feeder {
    Servo leftFeeder, rightFeeder;
    double finalPos = 1.0, initalPos = 0;
    public Feeder(HardwareMap hw){
        leftFeeder = hw.get(Servo.class,"leftFeeder");
        rightFeeder = hw.get(Servo.class,"rightFeeder");

        //Setting Direction has had no effect
//        leftFeeder.setDirection(Servo.Direction.FORWARD);
//        rightFeeder.setDirection(Servo.Direction.REVERSE);

    }
    public void feed(){
        leftFeeder.setPosition(finalPos);
        rightFeeder.setPosition(initalPos);
    }


    public void reset(){
        leftFeeder.setPosition(initalPos);
        rightFeeder.setPosition(finalPos);
    }

    public String getData()
    {
        return "Left feeder: "+ leftFeeder.getPosition() + "\n" + "Right feeder: " + rightFeeder.getPosition();
    }
}
