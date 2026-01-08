package org.firstinspires.ftc.teamcode.NewBot.Utils;

public class VelocityCalculator2 {

    //Goal: Be Able to Press a Button and the robot will align to the april tag direction
    //Idea: Save Last known distance value, and know which quadrant I am in
    //And Then I don't see the AprilTag, determine how far I traveled in a certain direction
    public enum Quadrant {
        NEAR, FAR
    }
    public static double[] distances = {900, 1000, 1125, 1400};
    private boolean isDefaultValue = false;

    public Quadrant state = Quadrant.NEAR;
    public double calculateVelocityForTarget(double horizontalDistance)
    {
        if (horizontalDistance < 60) {
            state = Quadrant.NEAR;
            isDefaultValue = true;
            return distances[0];
        }
        else if (horizontalDistance < 160) {
            state = Quadrant.NEAR;
            isDefaultValue = false;
            return distances[1];
        }
        else if (horizontalDistance < 200) {
            state = Quadrant.NEAR;
            isDefaultValue = false;
            return distances[2];
        }
        else {
            state = Quadrant.FAR;
            isDefaultValue = false;
            return distances[3];
        }
    }

    public Quadrant returnState()
    {
        return state;
    }

    public boolean checkIfDefaultValue()
    {
        return isDefaultValue;
    }
    public void adjustDistance(double value)
    {
        for(int i= 0; i < distances.length; i++)
        {
            distances[i] += value;
        }
    }



}
