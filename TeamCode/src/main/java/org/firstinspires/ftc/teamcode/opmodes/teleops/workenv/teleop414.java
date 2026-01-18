package org.firstinspires.ftc.teamcode.opmodes.teleops.workenv;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class teleop414 extends LinearOpMode {
    boolean sequenceStarted = false;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor"); //CH Motor Port 0
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor"); //CH Motor Port 1
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor"); //CH Motor Port 2
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor"); //CH Motor Port 3

        DcMotor intake = hardwareMap.dcMotor.get("intake"); //EH Motor Port 0
        //DcMotorEx shooter = hardwareMap.get(DcMotorEx.class, "shooter"); //EH Motor Port 1

        //Servo pushythingy = hardwareMap.servo.get("pushythingy");


        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );
        imu.initialize(parameters);
        imu.resetYaw();

        double turnPower = 0.5; // tune as necessary
        boolean turning = false;
        double targetHeading = 0;

        //shooter.setZeroPowerBehavior(FLOAT);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        //shooter.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /*
        ShooterPIDF shooterPIDF = new ShooterPIDF(
                hardwareMap,
                "shooter",
                0.002, 0.0, 0.0001, 0.00005
        );

         */

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            boolean joystickActive =
                    Math.abs(gamepad1.left_stick_x) > 0.3 ||
                            Math.abs(gamepad1.left_stick_y) > 0.3 ||
                            Math.abs(gamepad1.right_stick_x) > 0.3;

           // shooterPIDF.update();

            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;
            double x = gamepad1.left_stick_x;

            double rotX = x * 1.1;
            double rotY = y;


            //use IMU heading
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate joystick vector by -heading
            double fieldX = rotX * Math.cos(-botHeading) - rotY * Math.sin(-botHeading);
            double fieldY = rotX * Math.sin(-botHeading) + rotY * Math.cos(-botHeading);

            double speedMultiplier = 1.0 - (0.8 * gamepad1.right_trigger);

            double denominator = Math.max(Math.abs(fieldY) + Math.abs(fieldX) + Math.abs(rx), 1);

            //use fieldX/fieldY instead of rotX/rotY
            double frontLeftPower = (fieldY + fieldX + rx) / denominator * speedMultiplier;
            double backLeftPower = (fieldY - fieldX + rx) / denominator * speedMultiplier;
            double frontRightPower = (fieldY - fieldX - rx) / denominator * speedMultiplier;
            double backRightPower = (fieldY + fieldX - rx) / denominator * speedMultiplier;


            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            if (gamepad1.right_trigger >= 0.5) {
                speedMultiplier = 0.5;
            }

            if (gamepad2.x) {
                intake.setPower(0);
                //shooterPIDF.setTargetVelocity(0);
                sequenceStarted = false;
                runtime.reset();
            }
/*
            if (gamepad2.dpad_down) {
                shooterPIDF.setTargetVelocity(1600);; //tune
            }

            if (gamepad2.dpad_up) {
                shooterPIDF.setTargetVelocity(1800); //tune
            }



            if (gamepad2.y) {
                pushythingy.setPosition(0);
            } else pushythingy.setPosition(1);

 */

            if (gamepad2.right_trigger > 0.5) {
                intake.setPower(1);
            } else if (gamepad2.left_trigger > 0.5) {
                intake.setPower(-1);
            } else {
                intake.setPower(0);
            }

            if (gamepad1.options) {
                imu.resetYaw();
            }

            if (gamepad1.b) {
                frontLeftMotor.setZeroPowerBehavior(BRAKE);
                frontRightMotor.setZeroPowerBehavior(BRAKE);
                backLeftMotor.setZeroPowerBehavior(BRAKE);
                backRightMotor.setZeroPowerBehavior(BRAKE);
                telemetry.addLine("Brake Mode On");
            } else {
                frontLeftMotor.setZeroPowerBehavior(FLOAT);
                frontRightMotor.setZeroPowerBehavior(FLOAT);
                backLeftMotor.setZeroPowerBehavior(FLOAT);
                backRightMotor.setZeroPowerBehavior(FLOAT);
                telemetry.addLine("Brake Mode Off");
            }


            /*
            THIS MAKES IT SO PRESSING DPAD RIGHT AND LEFT TURNS IN 90 DEGREE INTERVALS
             */

            if (joystickActive) { //this basically ensures the joysticks have priority
                turning = false;
            }

            if (!turning && !joystickActive && gamepad1.dpad_left) {
                targetHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 90;
                turning = true;
            }

            if (!turning && !joystickActive && gamepad1.dpad_right) {
                targetHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - 90;
                turning = true;
            }
            if (turning) {
                double currentHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
                double error = targetHeading - currentHeading;

                // stop turning when reached
                if (Math.abs(error) < 2) {
                    frontLeftMotor.setPower(0);
                    backLeftMotor.setPower(0);
                    frontRightMotor.setPower(0);
                    backRightMotor.setPower(0);
                    turning = false;
                } else if (error > 0) { // turn left
                    frontLeftMotor.setPower(turnPower);
                    backLeftMotor.setPower(turnPower);
                    frontRightMotor.setPower(-turnPower);
                    backRightMotor.setPower(-turnPower);
                } else { // turn right
                    frontLeftMotor.setPower(-turnPower);
                    backLeftMotor.setPower(-turnPower);
                    frontRightMotor.setPower(turnPower);
                    backRightMotor.setPower(turnPower);
                }
            }


            if (!turning && !joystickActive) {
                if (gamepad1.dpad_up) {
                    frontLeftMotor.setPower(1);
                    frontRightMotor.setPower(1);
                    backLeftMotor.setPower(1);
                    backRightMotor.setPower(1);
                }
                else if (gamepad1.dpad_down) {
                    frontLeftMotor.setPower(-1);
                    frontRightMotor.setPower(-1);
                    backLeftMotor.setPower(-1);
                    backRightMotor.setPower(-1);
                }
                else {
                    frontLeftMotor.setPower(frontLeftPower);
                    backLeftMotor.setPower(backLeftPower);
                    frontRightMotor.setPower(frontRightPower);
                    backRightMotor.setPower(backRightPower);
                }
            }



            //Debugging Data
            telemetry.addLine();
            telemetry.addData("Front Right", frontRightMotor.getPower());
            telemetry.addData("Front Left", frontLeftMotor.getPower());
            telemetry.addData("Back Right", backRightMotor.getPower());
            telemetry.addData("BackLeft", backLeftMotor.getPower());
            telemetry.addData("Heading", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.addData("", "");
            //telemetry.addData("Shooter Velocity", shooter.getVelocity());
            telemetry.addData("Intake", intake.getPower());
            telemetry.update();
        }
    }
}

