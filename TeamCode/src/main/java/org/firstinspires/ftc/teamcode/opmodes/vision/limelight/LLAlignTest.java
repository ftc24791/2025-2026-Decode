package org.firstinspires.ftc.teamcode.opmodes.vision.limelight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;

/**
 * OpMode for testing AprilTag alignment with Limelight.
 *
 * This is mainly for verifying that the robot can use Limelight data
 * to correct its heading
 *
 * - Uses tx to figure out how far the apriltag is from the center of view
 * - Uses a simple P controller that may need tuning
 * - Stops when the robot is about close enough to being aligned
 *
 * Important:
 * This is NOT a distance alignment system. This only aligns the robot's
 * rotation with the AprilTag.
 *
 * Dont use botpose_MT2 as a distance sensor. It gives the robot's position
 * on the field, not the distance to the apriltag.
 *
 * If the robot oscillates or turning the wrong way, check the
 * P controller value and motor direction
 *
 * not tested as of August 6, 2026.
 */
@Disabled
@TeleOp
public class LLAlignTest extends LinearOpMode {

    private Limelight3A limelight3A;
    private final Hardware robot = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");

        // pipeline 3 should be configured as an apriltag pipeline in the limelight configuration site
        limelight3A.pipelineSwitch(3);
        limelight3A.start();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            alignToTag();
            telemetry.update();
            sleep(10);
        }
    }

    private void alignToTag() {

        LLResult result = limelight3A.getLatestResult();

        if (result == null || !result.isValid()) {
            telemetry.addLine("No aprilag found");
            stopDrive();
            return;
        }

        double tx = result.getTx();

        telemetry.addData("tx", tx);
        telemetry.addData("ty", result.getTy());
        telemetry.addData("ta", result.getTa());

        double kP = 0.03; //may require tuning... not sure
        double turnPower = tx * kP;

        // deadzone so it doesnt look like its having a seizure
        if (Math.abs(tx) < 1.5) {
            turnPower = 0;
        }

        // Limit max power
        turnPower = Math.max(-0.5, Math.min(0.5, turnPower));
        robot.frontLeftMotor.setPower(turnPower);
        robot.backLeftMotor.setPower(turnPower);
        robot.frontRightMotor.setPower(turnPower);
        robot.backRightMotor.setPower(turnPower);
        telemetry.addData("Turn Power", turnPower);
    }

    private void stopDrive() {
        robot.frontLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.backRightMotor.setPower(0);
    }
}