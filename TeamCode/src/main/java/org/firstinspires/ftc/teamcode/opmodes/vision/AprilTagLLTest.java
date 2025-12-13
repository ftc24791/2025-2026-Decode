package org.firstinspires.ftc.teamcode.opmodes.vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;

@TeleOp(name = "AprilTag Limelight BotPose Test")
public class AprilTagLLTest extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {

        Hardware robot = new Hardware();
        robot.init(hardwareMap);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(3);
        limelight.start();




        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {

                Pose3D botPose = result.getBotpose_MT2();

                if (botPose != null) {

                    Position pos = botPose.getPosition();

                    double xMeters = pos.x; // left/right
                    double yMeters = pos.y; // up/down
                    double zMeters = pos.z; // forward/back

                    double distanceMeters = Math.sqrt(xMeters * xMeters + zMeters * zMeters);
                    double distanceInches = distanceMeters * 39.37;

                    telemetry.addLine("--BOT POSE (FIELD SPACE)--");
                    telemetry.addData("X (meters)", "%.2f", xMeters);
                    telemetry.addData("Y (meters)", "%.2f", yMeters);
                    telemetry.addData("Z (meters)", "%.2f", zMeters);

                    telemetry.addLine("=== DISTANCE ===");
                    telemetry.addData("Distance (m)", "%.2f", distanceMeters);
                    telemetry.addData("Distance (in)", "%.2f", distanceInches);

                    telemetry.addLine("=== CAMERA TARGET DATA ===");
                    telemetry.addData("tx (deg)", "%.2f", result.getTx());
                    telemetry.addData("ty (deg)", "%.2f", result.getTy());
                    telemetry.addData("ta (%)", "%.2f", result.getTa());

                }

            }

            telemetry.update();
            sleep(10);
        }
    }
}
