package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

@TeleOp
public class AprilTagLLTest extends LinearOpMode {

    Limelight3A limelight3A;

    @Override
    public void runOpMode() throws InterruptedException {


        Hardware robot = new Hardware();
        robot.init(hardwareMap);

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(3); //Motif_Detect
        limelight3A.start();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            robot.imu.getRobotYawPitchRollAngles();

            LLResult llResult = limelight3A.getLatestResult();

            if (llResult != null && llResult.isValid()) {
                Pose3D pose3D = llResult.getBotpose_MT2();
                if (pose3D != null) {
                    telemetry.addData("Bot Pose", pose3D);
                } else {
                    telemetry.addLine("BotPose MT2 not available");
                }
            } else {
                telemetry.addLine("No Tag Detected");
            }

            telemetry.update();
            idle();
            sleep(10);
        }
    }
}

