package org.firstinspires.ftc.teamcode.NewBot.Testing;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeLED;
import org.firstinspires.ftc.teamcode.NewBot.Subsystems.LeMecanum;
import org.firstinspires.ftc.teamcode.NewBot.Utils.ColorSensor;
import org.firstinspires.ftc.teamcode.NewBot.Utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.NewBot.pedroPathing.Constants;

@TeleOp(group = "AB", name = "Park Color Sensor Test")
public class ParkColorSensorTest extends LinearOpMode {
    ColorSensor colorSensor;
    GamepadEvents controller;
    LeMecanum drive;
    LeLED led;
    Follower follower;
    boolean park = false;
    @Override
    public void runOpMode() throws InterruptedException
    {
        colorSensor = new ColorSensor(hardwareMap);
        controller = new GamepadEvents(gamepad1);
        led = new LeLED(hardwareMap);
        drive = new LeMecanum(hardwareMap);
//        follower = Constants.createFollower(hardwareMap);
        waitForStart();
//        follower.startTeleopDrive();
        while(opModeIsActive())
        {
            ColorSensor.Color color = colorSensor.getColor();
            ColorSensor.Color color2 = colorSensor.getSecondaryColor();
            if(controller.left_bumper.onPress())
            {
                park = !park;
            }

            if(color == ColorSensor.Color.RED && color2 == ColorSensor.Color.RED)
            {
                led.setColor(LeLED.Colors.ORANGE);
            }else if(color == ColorSensor.Color.RED)
            {
                led.setColor(LeLED.Colors.YELLOW);
            }else {
                led.setColor(LeLED.Colors.GREEN);
            }


//            if(park)
//            {
//                if(color == ColorSensor.Color.None && color2 == ColorSensor.Color.None)
//                {
////                    drive.drive(0.1, -0.1, 0);
//                    led.setColor(LeLED.Colors.PURPLE);
//                }else if(color == ColorSensor.Color.None)
//                {
////                    drive.drive(0, -0.1, 0);
//                    led.setColor(LeLED.Colors.PURPLE);
//                }else if(color2 == ColorSensor.Color.None)
//                {
//                    led.setColor(LeLED.Colors.PURPLE);
////                    drive.drive(0.1, 0, 0);
//                }else if(color == ColorSensor.Color.RED){
//                    led.setColor(LeLED.Colors.RED);
////                    drive.drive(0, 0, 0);
//                }
//            }else {
////                drive.drive(
////                        -controller.left_stick_y,
////                        controller.left_stick_x,
////                        controller.right_stick_x
////                );
//            }

            telemetry.addLine("Press left bumper to Park");
            telemetry.addData("Color Sensor Color:", colorSensor.getColor());
            telemetry.addData("Secondary Color:", colorSensor.getSecondaryColor());
            controller.update();
//            follower.update();
            telemetry.update();
        }
    }
}
