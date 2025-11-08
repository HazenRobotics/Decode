package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Flap {
    Servo frontFlap, backFlap;
    private double position;
    private double frontPosition;
    private double backPosition;
    private double backStop = 0.38;
    private double backDown = 0;
    private double frontStop = 0.2642;
    private double frontGo = 0.7839;
    public Flap(HardwareMap hw, String frontName, String backName){
        frontFlap = hw.get(Servo.class, frontName);
        backFlap = hw.get(Servo.class, backName);
    }
    public void setFrontFlap(double position){
        frontFlap.setPosition(position);
    }

    public void setBackFlap(double position){
        backFlap.setPosition(position);
    }
    public double getPosition(){
        return position;
    }

    public void frontBlock(){
        frontFlap.setPosition(frontStop);
    }
    public void frontGo(){
        frontFlap.setPosition(frontGo);
    }
    public void backBlock(){
        backFlap.setPosition(backStop);
    }
    public void backDown(){
        backFlap.setPosition(backDown);
    }
    public void frontToggle(){
        frontPosition = (frontPosition == frontGo) ? frontStop: frontGo;
        frontFlap.setPosition(frontPosition);
    }
    public void backToggle(){
        backPosition = (backPosition == backStop) ? backDown: backStop;
        backFlap.setPosition(backPosition);
    }
    public double getFrontPosition(){
        return frontFlap.getPosition();
    }
    public double getBackPosition(){
        return backFlap.getPosition();
    }
    public void adjustPosition(double increment)
    {
        double position = 0;
        position += increment*0.005;
        frontFlap.setPosition(position);
        backFlap.setPosition(position);
    }
}
