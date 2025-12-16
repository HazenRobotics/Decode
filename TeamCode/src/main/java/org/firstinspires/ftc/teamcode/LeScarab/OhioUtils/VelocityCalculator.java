package org.firstinspires.ftc.teamcode.LeScarab.OhioUtils;

public class VelocityCalculator {
    //radius = 4 inches
    private static double launchHeight = 0.10; //launchHeight height of launcher above ground (meters)
    private static double launchAngle = 50; //launchAngle launch angle in degrees
    private static double targetHeight = 0.984; //target height in meters
    private static double offsetDistance = 0.30;//Combined offset distance of camera and from edge of goal (meters)
    private static double relativeHeight = targetHeight - launchHeight;

    private static double g = -9.81;//accelertaion due to gravity

    public static double calculateInitialVelocity(double x) {
        // Convert theta from degrees to radians
        double thetaRad = Math.toRadians(launchAngle);
        double xDist = x+offsetDistance;
        // Calculate the numerator: g * x^2
        double numerator = g * xDist * xDist;

        // Calculate the denominator: 2 * cos^2(θ) * (x * tan(θ) - h)
        double cosTheta = Math.cos(thetaRad);
        double tanTheta = Math.tan(thetaRad);
        double denominator = 2 * cosTheta * cosTheta * (x * tanTheta - relativeHeight);
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
    //radius = 4.5 inches
    // 1 meter = 39.3701
    // 1 revolution = 2pi radians
    // length of flywheel: 2pi*(4.5)
    // radius = 4.5

    //Final Result: ticks per second
//    public double calclateAnglarVelocityForTarget(double horizontalDistance)
//    {
//        return calculateVelocityForTarget(horizontalDistance) *
//    }

}
