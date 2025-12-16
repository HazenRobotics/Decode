package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OldBots.Robots.V2;
import org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision.LogitechCam;
import org.firstinspires.ftc.teamcode.LeScarab.OhioUtils.GamepadEvents;
public class AutoAlign extends LinearOpMode {
    GamepadEvents controller1;
    V2 robot;
    private static final int TARGET_TAG_ID = 20; // Change this to the AprilTag ID you want to align to
    private static final double ALIGNMENT_P_GAIN = 0.015; // Proportional gain for turning power (vOmega)
    private static final double ALIGNMENT_TOLERANCE_DEG = 1.0; // Stop turning when yaw is within this range (in degrees)
    private LogitechCam visionSystem;
    @Override
    public void runOpMode() throws InterruptedException {

        controller1 = new GamepadEvents(gamepad1);
        robot = new V2(hardwareMap);
        visionSystem = new LogitechCam();
        visionSystem.init(hardwareMap, telemetry);

        while(opModeIsActive())
        {
            robot.drive(controller1.left_stick_y, controller1.left_stick_x, controller1.right_stick_x);
            visionSystem.update();

        }

    }
}
