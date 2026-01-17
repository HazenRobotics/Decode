package org.firstinspires.ftc.teamcode.NewBot.Utils;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RangeFinder {

    //Park Color Sensors are ports 2,3
    ColorRangefinder crf1, crf2, out1;
    public RangeFinder(HardwareMap hw)
    {
        crf1 = new ColorRangefinder(hw.get(RevColorSensorV3.class, "Range2"));;
        // 10mm or closer requirement
        crf1.setPin0Digital(ColorRangefinder.DigitalMode.HSV, 160 / 360.0 * 255, 190 / 360.0 * 255); // purple
        crf1.setPin0DigitalMaxDistance(ColorRangefinder.DigitalMode.HSV, 10); // 10mm or closer requirement
        crf1.setPin1Digital(ColorRangefinder.DigitalMode.HSV, 110 / 360.0 * 255, 140 / 360.0 * 255); // green
        crf1.setPin1DigitalMaxDistance(ColorRangefinder.DigitalMode.HSV, 10); // 10mm or closer requirement

        crf2 = new ColorRangefinder(hw.get(RevColorSensorV3.class, "Range3"));;
        // 10mm or closer requirement
        crf2.setPin0Digital(ColorRangefinder.DigitalMode.HSV, 160 / 360.0 * 255, 190 / 360.0 * 255); // purple
        crf2.setPin0DigitalMaxDistance(ColorRangefinder.DigitalMode.HSV, 10); // 10mm or closer requirement
        crf2.setPin1Digital(ColorRangefinder.DigitalMode.HSV, 110 / 360.0 * 255, 140 / 360.0 * 255); // green
        crf2.setPin1DigitalMaxDistance(ColorRangefinder.DigitalMode.HSV, 10); // 10mm or closer requirement

        out1 = new ColorRangefinder(hw.get(RevColorSensorV3.class, "Range1"));;
        // 10mm or closer requirement
        out1.setPin0Digital(ColorRangefinder.DigitalMode.HSV, 160 / 360.0 * 255, 190 / 360.0 * 255); // purple
        out1.setPin0DigitalMaxDistance(ColorRangefinder.DigitalMode.HSV, 10); // 10mm or closer requirement
        out1.setPin1Digital(ColorRangefinder.DigitalMode.HSV, 110 / 360.0 * 255, 140 / 360.0 * 255); // green
        out1.setPin1DigitalMaxDistance(ColorRangefinder.DigitalMode.HSV, 10); // 10mm or closer requirement

    }

    public double readOuterDistance()
    {
        return crf1.readDistance();
    }

    public double readOutake1Distance()
    {
        return out1.readDistance();
    }

    public double readInnerDistance()
    {
        return crf2.readDistance();
    }

    public boolean isBallDetected()
    {
        return crf1.readDistance() < 100 && crf2.readDistance() < 100;
    }

    public boolean isBallOut()
    {
        return out1.readDistance() < 100;
    }



}
