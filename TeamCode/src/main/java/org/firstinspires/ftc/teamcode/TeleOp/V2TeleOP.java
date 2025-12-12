package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.StarterRobot;
import org.firstinspires.ftc.teamcode.Robots.V2;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.utils.LEDLights;

@TeleOp(group = "A", name = "狮子并不关心三球进洞" )
public class V2TeleOP extends LinearOpMode {
    V2 robot;
    GamepadEvents controller1, controller2;
    @Override
    public void runOpMode() throws InterruptedException {
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);
        robot = new V2(hardwareMap, controller1, controller2);
        boolean far = false;
        boolean shootTog = false;
        RevBlinkinLedDriver led;
        LEDLights LED1 = new LEDLights(hardwareMap, "lights");

        waitForStart();
        while(opModeIsActive())
        {
            robot.drive();
            if (controller1.right_bumper.onPress())
            {
                robot.intake();
            }

            if(far){
                LED1.farColor();
            }else{
                LED1.nearColor();
            }

            if (controller1.a.onPress())
            {
                robot.toggleFeed();
            }


            if(controller2.x.onPress())
            {
                robot.multiplyRPM(-1);
            }

            if(controller2.y.onPress())
            {
                robot.reverseIntake();
            }
            if(controller2.a.onPress())
            {
                robot.shoot(-1);
            }

            if(controller2.dpad_up.onPress())
            {
                robot.setRPM(100);
            }
            if(controller2.dpad_down.onPress())
            {
                robot.setRPM(-100);
            }

            if(controller1.left_bumper.onPress())
            {
                shootTog = !shootTog;
                if(shootTog == true){
                    if(far)
                    {
                        robot.shoot(1500);
                    }else {
                        robot.shoot(1350);
                    }
                }else{
                    robot.shoot(0);
                }
            }

            if(controller1.x.onPress())
            {
                far = !far;
            }




//            robot.shoot();
//            robot.updateShooting();
            controller1.update();
            controller2.update();
            robot.feederEmoji(telemetry);

            telemetry.addLine("Use Left Joystick Y for movement, Right Joystick " +
                    "X for rotation");
            telemetry.addLine("使用左摇杆 Y 控制移动，右摇杆 + X 控制旋转");
            telemetry.addLine("Right bumper: intake");
            telemetry.addLine("右侧保险杠：进气口");
            telemetry.addLine("Left bumper: shoot only");
            telemetry.addLine("左侧肩键：仅射击");
            telemetry.addLine("A: Feeders");
            telemetry.addLine("A: 你中文考了A-，你妈要拿凉鞋打你了。");
            telemetry.addLine("Driver 2:\nDPAD_UP: Increase RPM\nDPAD_DOWN: Decreased RPM");
            telemetry.addLine("驱动器 2：\\n方向键向上：增加转速\\n方向键向下：降低转速");
            telemetry.addLine("X: Reverse Flywheel");
            telemetry.addLine("Y：反向飞轮");

//            telemetry.addLine("A：拨动式喂食器");
//

            telemetry.update();
        }
    }
}
