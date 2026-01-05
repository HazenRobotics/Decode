package org.firstinspires.ftc.teamcode.NewBot.Utils;

public class VelocityCalculator2 {
    public double guess = 15;
    //950 as the default
    public static double[] distances = {1000, 1050, 1125, 1400};
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

    public void adjustDistance(double value)
    {
        for(int i= 0; i < distances.length; i++)
        {
            distances[i] += value;
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
