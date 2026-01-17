package org.firstinspires.ftc.teamcode.NewBot.Utils;

public class VelocityCalculator3 {

    public double calculateAngularVelocityForTarget(double horizontalDistance)
    {
        //using unit multiplication it should be (m/s)/m * 28 ticks/rev * 1 rev / 2pi
        //Issue with code is probably because our currently bot has a lot of friction

        return 10 * Math.sqrt(3*horizontalDistance - 300) + 1000;
    }
}
