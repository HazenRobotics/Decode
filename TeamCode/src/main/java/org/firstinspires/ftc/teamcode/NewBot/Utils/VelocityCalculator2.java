package org.firstinspires.ftc.teamcode.NewBot.Utils;

public class VelocityCalculator2 {

    //Goal: Be Able to Press a Button and the robot will align to the april tag direction
    //Idea: Save Last known distance value, and know which quadrant I am in
    //And Then I don't see the AprilTag, determine how far I traveled in a certain direction
    public enum Quadrant {
        DEFAULT, NEAR, FAR
    }
    public double[] distances = {1080, 1080, 1080, 1450};
    private boolean isDefaultValue = false;

    public Quadrant state = Quadrant.DEFAULT;
    public double calculateVelocityForTarget(double horizontalDistance)
    {
        if (horizontalDistance < 60)
        {
            state = Quadrant.NEAR;
            isDefaultValue = true;
            return distances[0];
        }
        else if (horizontalDistance < 160)
        {
            state = Quadrant.NEAR;
            isDefaultValue = false;
            return distances[1];
        }
        else if (horizontalDistance < 200)
        {
            state = Quadrant.NEAR;
            isDefaultValue = false;
            return distances[2];
        }
        else
        {
            state = Quadrant.FAR;
            isDefaultValue = false;
            return distances[3];
        }
    }

    private Quadrant returnState()
    {
        return state;
    }

    //Get State, Then save that as final pos, and then have the Shooter shoot

    public double setVelocityWhenItDoesNotSeeAPRIlTag()
    {
        if(returnState() == Quadrant.FAR)
        {
            return distances[3];
        }else if(returnState() == Quadrant.NEAR)
        {
            return distances[1];
        }
        return distances[0];
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
