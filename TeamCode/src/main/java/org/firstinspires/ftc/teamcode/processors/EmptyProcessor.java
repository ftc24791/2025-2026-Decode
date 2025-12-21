package org.firstinspires.ftc.teamcode.processors;


import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.opencv.core.Mat;
import org.firstinspires.ftc.vision.VisionProcessor;

public class EmptyProcessor  implements VisionProcessor {

    @Override

    public void init (int  width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        return null;
    }
    @Override
    public void onDrawFrame (Canvas canvas, int onscreenwidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}
