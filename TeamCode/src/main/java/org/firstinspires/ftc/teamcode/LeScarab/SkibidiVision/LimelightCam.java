package org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimelightCam {

        String limelightName = "limelight";
        String ledName = "led";
        int allianceSide = 0;
        Limelight3A limelight;
        RevBlinkinLedDriver led;
        //height all in inches
        final double limit = 0.0;
        final double motifHeight = 19.5, limelightElevation = 0;
        final double limelightAngle = 0;
        //Read AprilTag, return a pattern:
        //20: Blue Goal
        //21: Green, Purple, Purple
        //22: Purple, Green, Purple
        //23: Purple, Purple, Green
        //24: Red Goal
        public LimelightCam(HardwareMap hw, String side)
        {
                limelight = hw.get(Limelight3A.class, limelightName);
//                led = hardwareMap.get(RevBlinkinLedDriver.class, ledName);

                if(side.equalsIgnoreCase("blue"))
                {
                        allianceSide = 1;
                }else if(side.equalsIgnoreCase("red"))
                {
                        allianceSide = 2;
                }
                initialize(allianceSide);
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
                //Method for now to see the pos from the goal
//                getPosFromTag();
                //method to check within range of goal. maybe calculate power(later)?
//                checkValidShoot();
        }

        //Check if the robot is in Shooting Range
        public void checkValidShoot()
        {
                LLResult result = limelight.getLatestResult();
                if (result != null && result.isValid()) {
                        double tx = result.getTx(); // How far left or right the target is (degrees)
                        double ty = result.getTy(); // How far up or down the target is (degrees)
                        //Replace with check


                }
        }
        //attempt 2

        public double getPosFromTag()
        {
                //distance = (target height - camera height)
                //           / tan(camera angle + target angle)

                LLResult result = limelight.getLatestResult();
                double ty;
                double value = Math.PI;
                //I think I need to localize the robot to know where it is
                if (result != null && result.isValid())
                {

                        ty = result.getTy(); // How far up or down the target is (degrees)

                        //Use Projectile Motion formula
                        //Assume the 435 rpm motor is used
                        value = (motifHeight - limelightElevation) / Math.tan(Math.toRadians(limelightAngle + (double) ty));
                }

                return value;
        }

        @NonNull
        public String toString()
        {
                LLResult result = limelight.getLatestResult();
                return "Tx: " + result.getTx() + "\nTy: " + result.getTy() + "\nTa: " + result.getTa();
        }
}
