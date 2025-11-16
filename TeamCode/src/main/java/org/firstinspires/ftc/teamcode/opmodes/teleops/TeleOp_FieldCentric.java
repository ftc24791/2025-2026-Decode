package org.firstinspires.ftc.teamcode.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class TeleOp_FieldCentric extends LinearOpMode {
    boolean sequenceStarted = false;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontLeftMotor"); //CH Motor Port 0
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor"); //CH Motor Port 1
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor"); //CH Motor Port 2
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor"); //CH Motor Port 3

        DcMotor intake = hardwareMap.dcMotor.get("intake"); //EH Motor Port 0
        DcMotorEx shooter = hardwareMap.get(DcMotorEx.class, "shooter"); //EH Motor Port 1

        Servo pushythingy = hardwareMap.servo.get("pushythingy");


        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        );
        imu.initialize(parameters);
        imu.resetYaw();


        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {


            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.left_stick_x;
            double x = gamepad1.right_stick_x;

            double rotX = x * 0.9;
            double rotY = y;

            boolean sequenceStarted = false;

            //use IMU heading
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate joystick vector by -heading
            double fieldX = rotX * Math.cos(-botHeading) - rotY * Math.sin(-botHeading);
            double fieldY = rotX * Math.sin(-botHeading) + rotY * Math.cos(-botHeading);


            double denominator = Math.max(Math.abs(fieldY) + Math.abs(fieldX) + Math.abs(rx), 1);

            //use fieldX/fieldY instead of rotX/rotY
            double frontLeftPower = (fieldY + fieldX + rx) / denominator;
            double backLeftPower = (fieldY - fieldX + rx) / denominator;
            double frontRightPower = (fieldY - fieldX - rx) / denominator;
            double backRightPower = (fieldY + fieldX - rx) / denominator;


            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            /*
            ORIGINAL SHOOTER SEQUENCE (unchanged)
            if (gamepad2.b && !sequenceStarted) {
                shooter.setPower(1);
                runtime.reset();
                sequenceStarted = true;
            }
            if (sequenceStarted && runtime.seconds() >= 1.5) {
                pushythingy.setPosition(0);
                sequenceStarted = false;
            }
            else pushythingy.setPosition(1);
            */

            if (gamepad2.right_trigger > 0.5) {
                intake.setPower(-1);
            } else {
                intake.setPower(0);
            }

            if (gamepad2.x) {
                intake.setPower(0);
                shooter.setPower(0);
            }

            if (gamepad2.dpad_down) {
                shooter.setVelocity(1650);
                shooter.setPower(1);
            }

            if (gamepad2.y) {
                pushythingy.setPosition(0);
            } else pushythingy.setPosition(1);


            if (gamepad2.options) {
                imu.resetYaw();
            }

            //Debugging Data
            telemetry.addData("Front Right", frontRightMotor.getPower());
            telemetry.addData("Front Left", frontLeftMotor.getPower());
            telemetry.addData("Back Right", backRightMotor.getPower());
            telemetry.addData("BackLeft", backLeftMotor.getPower());
            telemetry.addData("Heading", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.addData("", "");
            telemetry.addData("Shooter Velocity", shooter.getVelocity());
            telemetry.addData("Intake", intake.getPower());
            telemetry.update();
        }
    }
}
