package org.firstinspires.ftc.teamcode.opmodes.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;

@TeleOp
public class AprilTagLLTest extends LinearOpMode {

    Limelight3A limelight3A;

    @Override
    public void runOpMode() throws InterruptedException {

        Hardware robot = new Hardware();
        robot.init(hardwareMap);

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(3); // Motif_Detect
        limelight3A.start();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            LLResult llResult = limelight3A.getLatestResult();

            if (llResult != null && llResult.isValid()) {

                Pose3D pose3D = llResult.getBotpose_MT2();

                if (pose3D != null) {

                    Position pos = pose3D.getPosition();

                    double x = pos.x;  // left-right (meters)
                    double y = pos.y;  // up-down (meters)
                    double z = pos.z;  // forward-back (meters)

                    // Horizontal ground distance ignoring height
                    double distanceMeters = Math.sqrt(x * x + z * z);
                    double distanceInches = distanceMeters * 39.37;

                    telemetry.addData("X (side)", llResult.getTx());
                    telemetry.addData("Y (height)", llResult.getTy());
                    telemetry.addData("Z (forward)", llResult.getTa());
                    telemetry.addData("BotPose", pose3D);

                    telemetry.addData("Distance (meters)", distanceMeters);

                } else {
                    telemetry.addLine("BotPose not available");
                }

            } else {
                telemetry.addLine("No Tag Detected");
            }

            telemetry.update();
            sleep(10);
        }
    }
}
