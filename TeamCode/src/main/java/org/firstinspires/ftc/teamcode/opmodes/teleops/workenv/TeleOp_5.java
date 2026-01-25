package org.firstinspires.ftc.teamcode.opmodes.teleops.workenv;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.ShooterPIDF;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Spindexer;

@Configurable //makes it so now we can tune values in Panels
@TeleOp
public class TeleOp_5 extends LinearOpMode {

    int NUM_SLOTS = 3;
    int TICKS_PER_REV = 288; // for core hex
    int TICKS_PER_SLOT = TICKS_PER_REV / NUM_SLOTS; // shud be 96
    int currentSlot = 0; //pindexer needs to be aligned properlyt

    Hardware robot = new Hardware();

    //DcMotorEx spindexer;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        Spindexer spindexerSubsystem = new Spindexer(robot.spindexer, 288, 3);

        boolean turning = false;

        ShooterPIDF shooterPIDF = new ShooterPIDF(
                hardwareMap,
                "shooter",
                0.002, 0.0, 0.0001, 0.00005
        );

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            boolean joystickActive = Math.abs(gamepad1.left_stick_x) > 0.3 || Math.abs(gamepad1.left_stick_y) > 0.3 || Math.abs(gamepad1.right_stick_x) > 0.3;

            shooterPIDF.update();

            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;
            double x = gamepad1.left_stick_x;

            double rotX = x * 1.1;
            double rotY = y;

            double botHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double fieldX = rotX * Math.cos(-botHeading) - rotY * Math.sin(-botHeading);
            double fieldY = rotX * Math.sin(-botHeading) + rotY * Math.cos(-botHeading);

            double speedMultiplier = 1.0 - (0.6 * gamepad1.right_trigger);
            double denominator = Math.max(Math.abs(fieldY) + Math.abs(fieldX) + Math.abs(rx), 1);

            double frontLeftPower = (fieldY + fieldX + rx) / denominator * speedMultiplier;
            double backLeftPower = (fieldY - fieldX + rx) / denominator * speedMultiplier;
            double frontRightPower = (fieldY - fieldX - rx) / denominator * speedMultiplier;
            double backRightPower = (fieldY + fieldX - rx) / denominator * speedMultiplier;

            robot.frontLeftMotor.setPower(frontLeftPower);
            robot.backLeftMotor.setPower(backLeftPower);
            robot.frontRightMotor.setPower(frontRightPower);
            robot.backRightMotor.setPower(backRightPower);


            if (gamepad2.x) {
                robot.intake.setPower(0);
                shooterPIDF.setTargetVelocity(0);
            }

            if (gamepad2.dpad_down) {
                shooterPIDF.setTargetVelocity(1600);
                ; //tune
            }

            if (gamepad2.dpad_up) {
                shooterPIDF.setTargetVelocity(1800); //tune
            }


            if (gamepad2.right_trigger > 0.5) {
                robot.intake.setPower(1);
            } else if (gamepad2.left_trigger > 0.5) {
                robot.intake.setPower(-1);
            } else {
                robot.intake.setPower(0);
            }

            if (gamepad1.options) {
                robot.imu.resetYaw();
            }

            if (gamepad1.b) {
                robot.frontLeftMotor.setZeroPowerBehavior(BRAKE);
                robot.frontRightMotor.setZeroPowerBehavior(BRAKE);
                robot.backLeftMotor.setZeroPowerBehavior(BRAKE);
                robot.backRightMotor.setZeroPowerBehavior(BRAKE);
                telemetry.addLine("Brake Mode On");
            } else {
                robot.frontLeftMotor.setZeroPowerBehavior(FLOAT);
                robot.frontRightMotor.setZeroPowerBehavior(FLOAT);
                robot.backLeftMotor.setZeroPowerBehavior(FLOAT);
                robot.backRightMotor.setZeroPowerBehavior(FLOAT);
                telemetry.addLine("Brake Mode Off");
            }

            if (joystickActive) { //this basically ensures the joysticks have priority
                turning = false;
            }

            if (!turning && !joystickActive) {
                if (gamepad1.dpad_up) {
                    robot.frontLeftMotor.setPower(1);
                    robot.frontRightMotor.setPower(1);
                    robot.backLeftMotor.setPower(1);
                    robot.backRightMotor.setPower(1);
                } else if (gamepad1.dpad_down) {
                    robot.frontLeftMotor.setPower(-1);
                    robot.frontRightMotor.setPower(-1);
                    robot.backLeftMotor.setPower(-1);
                    robot.backRightMotor.setPower(-1);
                } else {
                    robot.frontLeftMotor.setPower(frontLeftPower);
                    robot.backLeftMotor.setPower(backLeftPower);
                    robot.frontRightMotor.setPower(frontRightPower);
                    robot.backRightMotor.setPower(backRightPower);
                }

            }


            telemetry.addData("Front Right", robot.frontRightMotor.getPower());
            telemetry.addData("Front Left", robot.frontLeftMotor.getPower());
            telemetry.addData("Back Right", robot.backRightMotor.getPower());
            telemetry.addData("BackLeft", robot.backLeftMotor.getPower());
            telemetry.addData("Heading", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.addLine();
            telemetry.addData("Shooter Velocity", robot.shooter.getVelocity());
            telemetry.addData("Intake", robot.intake.getPower());
            telemetry.update();
        }
    }


}
