package org.firstinspires.ftc.teamcode.Vision;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

public class Obelisk {

        String limelightName = "limelight";
        String ledName = "led";
        int allianceSide = 0;
        Limelight3A limelight;
        RevBlinkinLedDriver led;
        //Need to calculate this
        final double limit = 10.0;

        //Read AprilTag, return a pattern:
        //20: Blue Goal
        //21: Green, Purple, Purple
        //22: Purple, Green, Purple
        //23: Purple, Purple, Green
        //24: Red Goal
        public Obelisk(String side)
        {
                limelight = hardwareMap.get(Limelight3A.class, limelightName);
                led = hardwareMap.get(RevBlinkinLedDriver.class, ledName);

                if(side.equalsIgnoreCase("blue"))
                {
                        allianceSide = 1;
                }else if(side.equalsIgnoreCase("red"))
                {
                        allianceSide = 2;
                }
        }

        public void initialize(int i)
        {
                //0: Reading Motifs
                //1: Blue Side
                //2: Red Side
                int[] pipelines = {0,1,2};
                limelight.setPollRateHz(100);
                limelight.start();
                limelight.pipelineSwitch(pipelines[i]);
        }

        //Find Motif pattern
        //Useful for Auto
        public void readMotif()
        {
                //Switch to pipeline that reads Motifs
                initialize(0);
                //method to figure out the Arraylist pattern
        }

        //Useful for entire game
        public void readGoal()
        {
                initialize(allianceSide);
                //method to check within range of goal. maybe calculate power(later)?
                checkValidShoot();
        }

        //Check if the robot is in Shooting Range
        public void checkValidShoot()
        {
                LLResult result = limelight.getLatestResult();
                if (result != null && result.isValid()) {
                        double tx = result.getTx(); // How far left or right the target is (degrees)
                        double ty = result.getTy(); // How far up or down the target is (degrees)
                        //Replace with check
                        if(Math.hypot(tx, ty) >= limit)
                        {
                                led.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN);
                        }
                }
        }

        public void shoot()
        {
                //Read Limelight x, y, and angle

                LLResult result = limelight.getLatestResult();
                if (result != null && result.isValid()) {
                        double tx = result.getTx(); // How far left or right the target is (degrees)
                        double ty = result.getTy(); // How far up or down the target is (degrees)

                        //Use Projectile Motion formula
                        //Assume the 435 rpm motor is used

                }


        }
}
