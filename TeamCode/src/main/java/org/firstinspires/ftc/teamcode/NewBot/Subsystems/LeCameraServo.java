package org.firstinspires.ftc.teamcode.NewBot.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LeCameraServo {
    Servo cameraServo;
    String name = "cameraServo";
    //IDEA: Have a horizontal pos that is the default
    //Then have the camera servo adjust as it goes forward and back
    //from that default value
//    double zeroPos =

    public LeCameraServo(HardwareMap hw)
    {
        cameraServo = hw.get(Servo.class, name);
    }

    //Abstration: Have get and set Position be private
    //Then wrapper math class for figuring out the math
    public void setPositon(double pos)
    {
        cameraServo.setPosition(pos);
    }
    public double getPositon()
    {
        return cameraServo.getPosition();
    }

    public String getData()
    {
        return "Servo Position: " + getPositon();
    }

}
