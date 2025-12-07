package org.firstinspires.ftc.teamcode.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;

@TeleOp
public class TeleOp_2 extends LinearOpMode {
    /*
    Field-Centric
     */
    boolean sequenceStarted = false;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize hardware class
        Hardware robot = new Hardware();
        robot.init(hardwareMap);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {


            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double rotX = x * 1.1;
            double rotY = y;


            double botHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);


            double fieldX = rotX * Math.cos(-botHeading) - rotY * Math.sin(-botHeading);
            double fieldY = rotX * Math.sin(-botHeading) + rotY * Math.cos(-botHeading);


            double denominator = Math.max(Math.abs(fieldY) + Math.abs(fieldX) + Math.abs(rx), 1);


            double frontLeftPower = (fieldY + fieldX + rx) / denominator;
            double backLeftPower = (fieldY - fieldX + rx) / denominator;
            double frontRightPower = (fieldY - fieldX - rx) / denominator;
            double backRightPower = (fieldY + fieldX - rx) / denominator;


            robot.frontLeftMotor.setPower(frontLeftPower);
            robot.backLeftMotor.setPower(backLeftPower);
            robot.frontRightMotor.setPower(frontRightPower);
            robot.backRightMotor.setPower(backRightPower);


            if (gamepad2.b && !sequenceStarted) {
                robot.shooter.setVelocity(1650);
                runtime.reset();
                sequenceStarted = true;
            }

            if (sequenceStarted) {


                if (runtime.seconds() >= 1.5) {
                    robot.intake.setPower(1);
                }

                if (runtime.seconds() >= 3.0) {
                    robot.pushythingy.setPosition(Hardware.PUSHYTHINGY_UP);
                }
            }


            if (gamepad2.x) {
                robot.intake.setPower(0);
                robot.shooter.setPower(0);
                sequenceStarted = false;
                runtime.reset();
            }


            if (gamepad2.dpad_down) {
                robot.shooter.setVelocity(1800);
            }

            if (gamepad2.y) {
                robot.pushythingy.setPosition(Hardware.PUSHYTHINGY_UP);
            } else {
                robot.pushythingy.setPosition(Hardware.PUSHYTHINGY_DOWN);
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

            // Debugging Telemetry
            telemetry.addData("Front Right", robot.frontRightMotor.getPower());
            telemetry.addData("Front Left", robot.frontLeftMotor.getPower());
            telemetry.addData("Back Right", robot.backRightMotor.getPower());
            telemetry.addData("BackLeft", robot.backLeftMotor.getPower());
            telemetry.addData("Heading", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.addData("","" );
            telemetry.addData("Shooter Velocity", robot.shooter.getVelocity());
            telemetry.addData("Intake", robot.intake.getPower());
            telemetry.update();
        }
    }
}
