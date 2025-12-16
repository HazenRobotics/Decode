package org.firstinspires.ftc.teamcode.OldBots.SubSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Feeder {
    CRServo leftFeeder, rightFeeder, topFeeder;
    double speed = 1;
    public boolean isReversed = true;
    public boolean isFeed = false;
    public Feeder(HardwareMap hw){
        leftFeeder = hw.get(CRServo.class,"leftFeeder");
        rightFeeder = hw.get(CRServo.class,"rightFeeder");
        topFeeder = hw.get(CRServo.class, "topFeeder");

        //Setting Direction has had no effect
        if(isReversed)
        {
            leftFeeder.setDirection(CRServo.Direction.FORWARD);
            rightFeeder.setDirection(CRServo.Direction.FORWARD);
        }else {
            leftFeeder.setDirection(CRServo.Direction.REVERSE);
            rightFeeder.setDirection(CRServo.Direction.REVERSE);
        }


    }

    public Feeder(HardwareMap hw, String leftName, String rightName){
        leftFeeder = hw.get(CRServo.class,leftName);
        rightFeeder = hw.get(CRServo.class,rightName);
        topFeeder = hw.get(CRServo.class, "topFeeder");
        //Setting Direction has had no effect
//        leftFeeder.setDirection(Servo.Direction.FORWARD);
        rightFeeder.setDirection(CRServo.Direction.REVERSE);

    }
    public void feed(){
        leftFeeder.setPower(speed);
        rightFeeder.setPower(speed);
        topFeeder.setPower(speed);
        isFeed = true;
    }
    public void reverseFeed(){
        leftFeeder.setPower(-speed);
        rightFeeder.setPower(-speed);
        topFeeder.setPower(0);
        isFeed = false;

    }

    public void toggle(Boolean isFeed)
    {
        if(isFeed)
        {
            reverseFeed();
        }else {
            feed();
        }
    }

    public void reset(){
        leftFeeder.setPower(0);
        rightFeeder.setPower(0);
        topFeeder.setPower(0);
        isFeed = false;
    }

    public void feed(double speed){
        leftFeeder.setPower(speed);
        rightFeeder.setPower(speed);
        topFeeder.setPower(speed);
        isFeed = true;
    }


    public String getData()
    {
        return "Left feeder: "+ leftFeeder.getPower() + "\n" + "Right feeder: " + rightFeeder.getPower();
    }
}
