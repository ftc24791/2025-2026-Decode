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
 * Opmode for testing apriltag with limelight's megatag2 feature
 *
 * This is mainly for verifying that the Limelight setup stuff is working
 * before trying to use vision.
 *
 * Telemtry:
 * - AprilTag detection data (tx, ty, ta, tag IDs)
 * - Megatag2 robot field position
 * - IMU heading from control hub being sent to Limelight
 *
 * Important:
 * Megatag2 is not magic. It needs the IMU and correct camera
 * mounting info to work properly. If the numbers look wrong, check
 * the setup before blaming the code for your misery.
 *
 * Dont use botpose_MT2 as a distance sensor. It gives the robot's position
 * on the field, not the distance to the apriltag.
 *
 * This file exists so future teams don't have to spend a week fighting
 * Limelight because someone tried copy pasting code from the docs without knowing
 * what was happening (this may or may not have been me).
 *
 * not tested as of August 6, 2026.
 */

@TeleOp
public class AprilTagLLTest extends LinearOpMode {

    private Limelight3A limelight;
    private IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {

        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP)
        );

        imu.initialize(parameters);
        imu.resetYaw();

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(3);

        limelight.start();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            imu.resetYaw();

            double robotYaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

            limelight.updateRobotOrientation(robotYaw);

            LLResult result = limelight.getLatestResult();

            telemetry.addData("Robot Yaw", robotYaw);

            if (result != null && result.isValid()) {

                telemetry.addLine("Target Data");
                telemetry.addData("tx", result.getTx());
                telemetry.addData("ty", result.getTy());
                telemetry.addData("ta", result.getTa());
                telemetry.addData("Tag Count", result.getFiducialResults().size());

                for (LLResultTypes.FiducialResult tag : result.getFiducialResults()) {
                    telemetry.addData("Tag ID", tag.getFiducialId());
                }

                Pose3D botPose = result.getBotpose_MT2();

                if (botPose != null) {

                    telemetry.addLine("Megatag2 stuff");

                    telemetry.addData("Field X (m)", "%.2f", botPose.getPosition().x);

                    telemetry.addData("Field Y (m)", botPose.getPosition().y);

                    telemetry.addData("Robot Height (m)", botPose.getPosition().z);

                } else {
                    telemetry.addLine("No MegaTag2 pose found");
                }

            } else {

                telemetry.addLine("No Apriltag found");

            }

            telemetry.update();
            sleep(10); //make sure stuff happens
        }
    }
}