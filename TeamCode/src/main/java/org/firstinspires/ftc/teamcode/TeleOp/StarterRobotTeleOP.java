package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.StarterRobot;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

@TeleOp(group = "A", name = "LeStarter Robot TeleOP" )
public class StarterRobotTeleOP extends LinearOpMode {
    StarterRobot robot;
    GamepadEvents controller1, controller2;

    @Override
    public void runOpMode() throws InterruptedException {
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);
        robot = new StarterRobot(hardwareMap, controller1, controller2);
        Shooter shooter = new Shooter(hardwareMap,"leftShooter");
        waitForStart();
        while(opModeIsActive())
        {
            robot.drive();
            if (controller1.left_bumper.onPress()) {
                robot.intake();
            }

            if (controller1.right_bumper.onPress()) {
                robot.shoot();
            }
            if(controller1.b.onPress()) {
                robot.transfer();
            }
            if(controller1.a.onPress()) {
                robot.reverseTrasfer();
            }
            if(controller1.x.onPress()){
                robot.load();
            }
            robot.updateReverseTransfer();
            robot.updateLoad();
            robot.updateShooting();
            robot.updateTransfer();
            controller1.update();
            controller2.update();

            telemetry.addLine("(b): Tranfer");
            telemetry.addLine("(x): Load");
            telemetry.addLine("(left bumper): Intake");
            telemetry.addLine("(right bumper): Shoot");
            telemetry.addLine("(y): Reverse Transfer");
            telemetry.addData("Shooter Voltage", shooter.getVoltageNormalizedVelocity(1800));
            telemetry.addData("Voltage:", shooter.getVoltage());
            telemetry.addData("Velocity", shooter.getVelocity());
            telemetry.update();

        }
    }
}
