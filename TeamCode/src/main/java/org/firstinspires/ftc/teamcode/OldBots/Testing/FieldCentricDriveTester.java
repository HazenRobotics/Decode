package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.LeScarab.SigmaSubsystems.LeMecanum;

public class FieldCentricDriveTester extends LinearOpMode {
    private LeMecanum driveTrain;
    private double applyDeadzone(double v, double d) {
        return Math.abs(v) > d ? v : 0.0;
    }
    @Override
    public void runOpMode() {
        driveTrain = new LeMecanum(hardwareMap);

        final double DEADZONE = 0.05;
        final double SPEED_SCALE = 0.9;

        waitForStart();

        while (opModeIsActive()) {
            double forward = -gamepad1.left_stick_y; // up = positive
            double strafe = gamepad1.left_stick_x;
            double rotate = gamepad1.right_stick_x;

            forward = applyDeadzone(forward, DEADZONE) * SPEED_SCALE;
            strafe = applyDeadzone(strafe, DEADZONE) * SPEED_SCALE;
            rotate = applyDeadzone(rotate, DEADZONE) * SPEED_SCALE;

            // MecanumDrive.fieldCentricDrive handles IMU-based transformation
            driveTrain.fieldCentricDrive(forward, strafe, rotate);

            telemetry.addData("Fwd", "%.2f", forward);
            telemetry.addData("Str", "%.2f", strafe);
            telemetry.addData("Rot", "%.2f", rotate);
            telemetry.update();

            sleep(20);
        }
        driveTrain.drive(0.0, 0.0, 0.0);
    }
}
