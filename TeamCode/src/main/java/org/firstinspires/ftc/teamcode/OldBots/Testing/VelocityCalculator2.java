package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.reflect.Array;
import java.util.HashMap;

public class VelocityCalculator2 {
    public double guess = 15;
    double[] distances = {0, 1000, 1075, 1350};
    public double calculateVelocityForTarget(double horizontalDistance)
    {
        if(horizontalDistance < 60)
        {
            return distances[0];
        }else if(horizontalDistance < 160)
        {
            return distances[1];
        }else if(horizontalDistance > 160 && horizontalDistance < 200)
        {
            return distances[2];
        }else if(horizontalDistance > 280)
        {
            return distances[3];
        }else {
            return distances[0];
        }
    }

    public void setConstant(double constant)
    {
        guess += constant;
    }

    public double getConstant()
    {
        return guess;
    }


}
