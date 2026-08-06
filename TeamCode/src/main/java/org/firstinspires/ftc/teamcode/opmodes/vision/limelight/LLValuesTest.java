package org.firstinspires.ftc.teamcode.opmodes.vision.limelight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

/**
 * OpMode for viewing Limelight values and testing MegaTag2.
 *
 * This is mainly for verifying that the Limelight setup is working
 * correctly before using vision
 *
 * Telemetry:
 * - apriltag detection data (tx, ty, ta, tag IDs)
 * - megatag2 robot field position
 * - IMU heading being sent to limelight
 *
 * Important:
 * Megatag2 is not magic. It needs the IMU and correct camera
 * mounting info to work properly. If the numbers look wrong, check
 * the setup before blaming the code for your misery.
 *
 * Dont use botpose_MT2 as a distance sensor. It gives the bots
 * position on the field, not the distance to the Apriltag.
 *
 * Not tested as of August 6, 2026.
 */
@TeleOp
public class LLValuesTest extends LinearOpMode {

    private Limelight3A limelight3A;

    @Override
    public void runOpMode() throws InterruptedException {

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");

        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP)
        );

        imu.initialize(parameters);
        imu.resetYaw();


        limelight3A.pipelineSwitch(3);

        // the higher this is the smoother
        limelight3A.setPollRateHz(100);

        limelight3A.start();

        waitForStart();

        if (isStopRequested()) return;


        while (opModeIsActive()) {

            double robotYaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

            // megatag2 needs robot heading to calculate field pose
            limelight3A.updateRobotOrientation(robotYaw);

            LLResult result = limelight3A.getLatestResult();

            telemetry.addData("Robot Yaw", robotYaw);


            if (result != null && result.isValid()) {

                double tx = result.getTx();
                double ty = result.getTy();
                double ta = result.getTa();


                telemetry.addLine("=== TARGET DATA ===");
                telemetry.addData("Target X", tx);
                telemetry.addData("Target Y", ty);
                telemetry.addData("Target Area", ta);
                telemetry.addData("Tag Count", result.getFiducialResults().size());
                for (LLResultTypes.FiducialResult tag : result.getFiducialResults()) {
                    telemetry.addData("Tag ID", tag.getFiducialId());
                }

                Pose3D botpose_mt2 = result.getBotpose_MT2();

                if (botpose_mt2 != null) {

                    telemetry.addLine("Megatag2 stuff");
                    telemetry.addData("Field X", botpose_mt2.getPosition().x);
                    telemetry.addData("Field Y", botpose_mt2.getPosition().y);
                    telemetry.addData("Robot Height", botpose_mt2.getPosition().z);
                } else {
                    telemetry.addLine("no Megatag2 Pose");
                }
            } else {
                telemetry.addLine("No targets");
            }
            telemetry.update();
        }
    }
}