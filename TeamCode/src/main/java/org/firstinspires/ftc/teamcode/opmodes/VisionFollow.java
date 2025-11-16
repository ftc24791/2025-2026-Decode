package org.firstinspires.ftc.teamcode.opmodes;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@Disabled
@Autonomous
public class VisionFollow extends LinearOpMode {

    final double SPEED_TURN = 0.02; //Play with, chooses how fast motor moves
    private DcMotor cameraMotor = null;
    private static final boolean USE_WEBCAM = true;
    private static final int DESIRED_TAG_ID = -1;
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTagProcessor;
    private AprilTagDetection desiredTag = null;

    @Override
    public void runOpMode() throws InterruptedException {

        boolean targetFound = false;    // Set to true when an AprilTag target is detected
        double rotate = 0;        // Desired forward power/speed (-1 to +1)

        //initAprilTag();

        cameraMotor = hardwareMap.get(DcMotor.class, "cameraMotor");


    }
}

