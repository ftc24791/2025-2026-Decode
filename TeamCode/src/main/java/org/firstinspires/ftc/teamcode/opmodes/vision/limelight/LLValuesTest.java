package org.firstinspires.ftc.teamcode.opmodes.vision.limelight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;

@TeleOp
public class LLValuesTest extends LinearOpMode {

    Limelight3A limelight3A;
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight3A.start(); // This tells Limelight to start looking!
        limelight3A.pipelineSwitch(3);

        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );
        imu.initialize(parameters);
        imu.resetYaw();


        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {

            LLResult result = limelight3A.getLatestResult();
            if (result != null && result.isValid()) {
                double tx = result.getTx(); // How far left or right the target is (degrees)
                double ty = result.getTy(); // How far up or down the target is (degrees)
                double ta = result.getTa(); // How big the target looks (0%-100% of the image)

                telemetry.addData("Target X", tx);
                telemetry.addData("Target Y", ty);
                telemetry.addData("Target Area", ta);
            } else {
                telemetry.addLine("No Targets");
            }

            double robotYaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            limelight3A.updateRobotOrientation(robotYaw);
            if (result != null && result.isValid()) {
                Pose3D botpose_mt2 = result.getBotpose_MT2();
                if (botpose_mt2 != null) {
                    double x = botpose_mt2.getPosition().x;
                    double y = botpose_mt2.getPosition().y;
                    telemetry.addData("MT2 Location:", "(" + x + ", " + y + ")");
                }
            }

            telemetry.update();
        }

    }
}
