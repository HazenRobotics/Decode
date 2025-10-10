package org.firstinspires.ftc.teamcode.utils;

public class optimalRPM
{
    private static final double GRAVITY = -9.81; //Acceleration due to gravity in m/s^2
    private static double LAUNCHER_HEIGHT = 0.105; //height of the lowest point on the launcher in meters
    private static double CHANGE_IN_HEIGHT = (0.98425 - LAUNCHER_HEIGHT); //vertical distance from the launcher to the goal in meters

    public static double getRPM(double goalDistance)
    {
        //launcher height and wheel rpm will need to be changed to match the final build
        double launchError = 1.0;
        double resultRPM = 0.0;
        double angle = 30.0;
        for (int wheelRPM = 0; wheelRPM < 6051; wheelRPM += 50)
        {
            double initialVelocity = (wheelRPM * 0.09 * Math.PI / 60); //net velocity vector magnitude when leaving launcher                double angle = Math.toRadians(testAngle);
            double horizontalVelocity = Math.cos(angle)*initialVelocity;
            double verticalVelocity = Math.sin(angle)*initialVelocity;
            double distance = getHorizontalDistance(horizontalVelocity, verticalVelocity);
            if (distance != 0.0)
            {
                double errorDistance = Math.abs(goalDistance - Math.abs(distance));
                if (errorDistance < launchError)
                {
                    resultRPM = wheelRPM;
                    launchError = errorDistance;
                }
            }
        }
        return resultRPM;
    }
    public static double getHorizontalDistance(double horizontalVelocity, double verticalVelocity)
    {
        double discriminant = (Math.pow(verticalVelocity,2) + 2*GRAVITY*CHANGE_IN_HEIGHT);
        double positiveRootAirTime = 0.0;
        double negativeRootAirTime = 0.0;
        if (discriminant >= 0)
        {
            positiveRootAirTime = (-verticalVelocity + Math.sqrt(discriminant))/GRAVITY;
            negativeRootAirTime = (-verticalVelocity - Math.sqrt(discriminant))/GRAVITY;
        }
        if (Math.abs(positiveRootAirTime) > Math.abs(negativeRootAirTime))
        {
            return(positiveRootAirTime*horizontalVelocity);
        }
        else if (Math.abs(positiveRootAirTime) < Math.abs(negativeRootAirTime))
        {
            return(negativeRootAirTime*horizontalVelocity);
        }
        return 0.0;
    }
}


