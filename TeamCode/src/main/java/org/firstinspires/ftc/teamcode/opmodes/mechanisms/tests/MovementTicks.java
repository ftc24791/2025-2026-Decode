package org.firstinspires.ftc.teamcode.opmodes.mechanisms.tests;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MovementTicks {

    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;

    private IMU imu;

    private static final double WHEEL_DIAMETER_CM = 7.5;
    private static final double WHEEL_CIRCUMFERENCE_CM = Math.PI * WHEEL_DIAMETER_CM;
    private static final double TICKS_PER_REV = 537.6;
    private static final double TICKS_PER_CM = TICKS_PER_REV / WHEEL_CIRCUMFERENCE_CM;

    private LinearOpMode opMode;

    public MovementTicks(HardwareMap hardwareMap, LinearOpMode opMode) {
        this.opMode = opMode;

        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );
        imu.initialize(parameters);
    }

    private double getHeading() {
        double heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        if (heading < 0) heading += 360;
        return heading;
    }

    public void driveForward(double cm, double speed) {
        int target = (int) (cm * TICKS_PER_CM);

        frontLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() + target);
        frontRightMotor.setTargetPosition(frontRightMotor.getCurrentPosition() + target);
        backLeftMotor.setTargetPosition(backLeftMotor.getCurrentPosition() + target);
        backRightMotor.setTargetPosition(backRightMotor.getCurrentPosition() + target);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeftMotor.setPower(speed);
        frontRightMotor.setPower(speed);
        backLeftMotor.setPower(speed);
        backRightMotor.setPower(speed);

        while (opMode.opModeIsActive() &&
                (frontLeftMotor.isBusy() || frontRightMotor.isBusy() ||
                        backLeftMotor.isBusy() || backRightMotor.isBusy())) {
            opMode.idle();
        }

        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveBackward(double cm, double speed) {
        driveForward(-cm, speed);
    }

    public void strafeRight(double cm, double speed) {
        int target = (int) (cm * TICKS_PER_CM);

        frontLeftMotor.setTargetPosition(frontLeftMotor.getCurrentPosition() - target);
        frontRightMotor.setTargetPosition(frontRightMotor.getCurrentPosition() + target);
        backLeftMotor.setTargetPosition(backLeftMotor.getCurrentPosition() + target);
        backRightMotor.setTargetPosition(backRightMotor.getCurrentPosition() - target);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeftMotor.setPower(speed);
        frontRightMotor.setPower(speed);
        backLeftMotor.setPower(speed);
        backRightMotor.setPower(speed);

        while (opMode.opModeIsActive() &&
                (frontLeftMotor.isBusy() || frontRightMotor.isBusy() ||
                        backLeftMotor.isBusy() || backRightMotor.isBusy())) {
            opMode.idle();
        }

        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void strafeLeft (double cm, double speed) {
        strafeRight(-cm, speed);
    }

    public void turnRight(double degrees, double speed) {
        double target = (getHeading() + degrees) % 360;

        frontLeftMotor.setPower(speed);
        backLeftMotor.setPower(speed);
        frontRightMotor.setPower(-speed);
        backRightMotor.setPower(-speed);

        while (opMode.opModeIsActive()) {
            double current = getHeading();
            double error = Math.abs(target - current);
            if (error < 1.0) break;
            opMode.idle();
        }

        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void turnLeft(double degrees, double speed) {
        turnRight(-degrees, speed);
    }

}
