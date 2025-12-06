package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

@TeleOp
public class LLAlignTest extends LinearOpMode {

    Limelight3A limelight3A;
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {

        /*
        Pipeline 0: limelight_test
        Pipeline 1: Red_Goal
        Pipeline 2: Blue_Goal
        Pipeline 3: Motif_Detect

         */
        robot.init(hardwareMap);
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");

        limelight3A.start();
        limelight3A.pipelineSwitch(3);

        waitForStart();
        if (isStopRequested()) return;

        driveToTagDistance(0.40); // stop 40cm away

        while (opModeIsActive()) {
            idle();
        }
    }

    public void driveToTagDistance(double targetDistanceMeters) {

        while (opModeIsActive()) {

            double kP = 0.035;

            double error = getDistance() - targetDistanceMeters;

            if (Math.abs(error) > 1) {


                //double kP = 0.035;
                //double power = Math.max(-0.4, Math.min(0.4, kP * error));


                setAllDrivePower(1);
                error = getDistance() - targetDistanceMeters;

                //setAllDrivePower(power);

                telemetry.addData("Distance", getDistance());
                telemetry.addData("Target", targetDistanceMeters);
                //telemetry.addData("Power", 1);
                telemetry.addData("Error", error);
                telemetry.update();
            } else {
                setAllDrivePower(0);
            }

        }


    }


    private double getDistance() {
        double kP = 0.035;
        LLResult llResult = limelight3A.getLatestResult();
        telemetry.addData("llResult", llResult != null ? llResult.isValid() : "No result");
        if (llResult == null || !llResult.isValid()) return -1;
        Pose3D pose3D = llResult.getBotpose_MT2();
        if (pose3D == null) return -1;
        Position pos = pose3D.getPosition();
        double z = llResult.getTa() * kP;
        double x = llResult.getTx() * kP;
        telemetry.addData("X,Z", x + "," + z);
        return Math.abs(z);
        //return Math.sqrt(x * x + z * z);
    }



    public void setAllDrivePower(double p) {
        robot.frontLeftMotor.setPower(p);
        robot.frontRightMotor.setPower(p);
        robot.backLeftMotor.setPower(p);
        robot.backRightMotor.setPower(p);
    }
}