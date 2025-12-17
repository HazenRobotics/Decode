package org.firstinspires.ftc.teamcode.LeScarab.OhioUtils;

public class VelocityCalculator {
    private static double launchHeight = 0.10; //launchHeight height of launcher above ground (meters)
    private static double launchAngle = 50; //launchAngle launch angle in degrees
    private static double targetHeight = 0.984; //target height in meters
    private static double offsetDistance = 0.30;//Combined offset distance of camera and from edge of goal (meters)
    private static double relativeHeight = targetHeight - launchHeight;

    private static double g = 9.81;//accelertaion due to gravity

    public static double calculateInitialVelocity(double x) {
        // Convert theta from degrees to radians
        double thetaRad = Math.toRadians(launchAngle);
        double xDist = x+offsetDistance;
        // Calculate the numerator: g * x^2
        double numerator = g * xDist * xDist;

        // Calculate the denominator: 2 * cos^2(θ) * (x * tan(θ) - h)
        double cosTheta = Math.cos(thetaRad);
        double tanTheta = Math.tan(thetaRad);
        double denominator = 2 * cosTheta * cosTheta * (xDist * tanTheta - relativeHeight);
        // Check if denominator is valid
        if (denominator == 0) {
           denominator = 0.0001;
        }else if(denominator < 0){
            denominator = Math.abs(denominator);
        }

        // Calculate v0 = sqrt(numerator / denominator)
        double v0 = Math.sqrt(numerator / denominator);
        return v0;
    }


    public double calculateVelocityForTarget ( double horizontalDistance){
        // Calculaterelative height (target height - launch height)
        double relativeHeight = targetHeight - launchHeight;

        // Calculate required initial velocity
        double v0 = calculateInitialVelocity(
                horizontalDistance
        );

        System.out.println("Horizontal distance: " + horizontalDistance + " m");
        System.out.println("Relative height: " + relativeHeight + " m");
        System.out.println("Required velocity: " + v0 + " m/s");
        return v0;
    }
    //radius = 43 mm
    //1 revolution = 28 ticks
    //Final Result: ticks per second
    public double calculateAngularVelocityForTarget(double horizontalDistance)
    {
        //using unit multiplication it should be (m/s)/m * 28 ticks/rev * 1 rev / 2pi
        //Issue with code is probably because our currently bot has a lot of friction
        double why = 2.75;
        return (calculateVelocityForTarget(horizontalDistance) / 0.043) * 28/ (Math.PI * 2 *  why);
    }

}
