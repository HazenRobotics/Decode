package org.firstinspires.ftc.teamcode.NewBot.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.ftc.localization.constants.TwoWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants().mass(12)
            .forwardZeroPowerAcceleration(-34.3463614)
            .lateralZeroPowerAcceleration(-62)
            .useSecondaryTranslationalPIDF(true)
            .useSecondaryHeadingPIDF(true)
            .useSecondaryDrivePIDF(true)

            //Change values of below
            .translationalPIDFCoefficients(new PIDFCoefficients(0.9, 0, 0.00001, 0.25))
            //Secondary PIDF for translational having issues
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.1, 0, 0.0001, 0.01))

            .headingPIDFCoefficients(new PIDFCoefficients(0.7,0,0.0001,0.2))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(2.5, 0, 0.0005,0.02))
            //above was good enough to be consistent
            .centripetalScaling(0.0005)
            //NOTE: Breaking Strength is 0.1
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(1, 0, 0.1, 0.6,0))
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.05, 0, 0.000005, 0.6, 0.02));
    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1.0, 1);

    public static Follower createFollower(HardwareMap hardwareMap)
    {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .build();
    }

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(3)
            .strafePodX(5.5)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);



    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("FRM")
            .rightRearMotorName("BRM")
            .leftRearMotorName("BLM")
            .leftFrontMotorName("FLM")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(63.533128)
            .yVelocity(51.4026);
}