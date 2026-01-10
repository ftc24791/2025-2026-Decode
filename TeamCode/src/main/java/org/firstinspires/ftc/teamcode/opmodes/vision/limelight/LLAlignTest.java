package org.firstinspires.ftc.teamcode.opmodes.vision.limelight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;

@Disabled
@TeleOp
public class LLAlignTest extends LinearOpMode {

    Limelight3A limelight3A;
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");

        limelight3A.start();
        limelight3A.pipelineSwitch(3);

        waitForStart();
        if (isStopRequested()) return;

        driveToTagDistance(0.40);

        while (opModeIsActive()) {
            idle();
        }
    }

    public void driveToTagDistance(double targetDistanceMeters) {

        while (opModeIsActive()) {

            double kP = 0.035;

            double error = getDistance() - targetDistanceMeters;

            if (Math.abs(error) > 0.05) {

                setAllDrivePower(1);
                error = getDistance() - targetDistanceMeters;

                telemetry.addData("Distance", getDistance());
                telemetry.addData("Target", targetDistanceMeters);
                telemetry.addData("Error", error);
                telemetry.update();
            } else {
                setAllDrivePower(0);
                return;
            }

        }

    }

    private double getDistance() {
        LLResult llResult = limelight3A.getLatestResult();
        telemetry.addData("llResult", llResult != null ? llResult.isValid() : "No result");
        if (llResult == null || !llResult.isValid()) return -1;
        Pose3D pose3D = llResult.getBotpose_MT2();
        if (pose3D == null) return -1;
        Position pos = pose3D.getPosition();
        double z = pos.z;
        telemetry.addData("Z", z);
        return Math.abs(z);
    }

    public void setAllDrivePower(double p) {
        robot.frontLeftMotor.setPower(p);
        robot.frontRightMotor.setPower(p);
        robot.backLeftMotor.setPower(p);
        robot.backRightMotor.setPower(p);
    }
}
