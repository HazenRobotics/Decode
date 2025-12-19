package org.firstinspires.ftc.teamcode.OldBots.Testing;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LeScarab.SkibidiVision.LogitechCam;
import org.firstinspires.ftc.teamcode.OldBots.SubSystems.LED;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import com.qualcomm.ftccommon.SoundPlayer;

@TeleOp(group = "test", name = "LeWebcam")
public class WebCamTest extends LinearOpMode {
    LogitechCam webcam;
//    LED led;
    int TARGET_TAG_ID = 24;
    int dialupSoundId;

    @Override
    public void runOpMode() throws InterruptedException
    {
        webcam = new LogitechCam();
//        led = new LED(hardwareMap);
        webcam.init(hardwareMap,telemetry);
        boolean soundPlaying = false;

        // Load sound
        dialupSoundId = hardwareMap.appContext
                .getResources()
                .getIdentifier("internet", "raw",
                        hardwareMap.appContext.getPackageName());

        // ===== INIT LOOP =====
        while (!isStarted() && !isStopRequested()) {

            if (!soundPlaying && dialupSoundId != 0) {
                SoundPlayer.getInstance().startPlaying(
                        hardwareMap.appContext,
                        dialupSoundId
                );
                soundPlaying = true;
            }

            telemetry.addLine("INIT - playing dialup");
            telemetry.update();
        }

        // ===== STOP SOUND WHEN START IS PRESSED =====
        SoundPlayer.getInstance().stopPlayingAll();

        while(opModeIsActive())
        {
            AprilTagDetection targetTag = webcam.getTagBySpecificId(TARGET_TAG_ID);

            if(targetTag != null)
            {
                telemetry.addLine("Found April Tag");
            }else {
                telemetry.addLine(":(");
            }
            telemetry.update();
            webcam.update();
        }

    }
}
