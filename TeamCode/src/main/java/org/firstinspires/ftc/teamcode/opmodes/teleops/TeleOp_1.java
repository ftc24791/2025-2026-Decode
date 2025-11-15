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


@TeleOp
public class TeleOp_1 extends LinearOpMode {
    boolean sequenceStarted = false;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor"); //Port 1
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor"); //Port 3
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor"); //Port 0
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor"); //Port 2

        DcMotor intake = hardwareMap.dcMotor.get("intake");
        DcMotorEx shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        Servo pushythingy = hardwareMap.servo.get("pushythingy");


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



    waitForStart(); //when start pressed
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.left_stick_x;
            double x = gamepad1.right_stick_x;

            double rotX = x * 0.9;
            double rotY = y;



            boolean sequenceStarted = false;


            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;


            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);




            /*
            if (gamepad2.b && !sequenceStarted) {        //"B" on Gamepad 2 does the following: start the shooter motor, run the intake to push artifacts into the shooter, and shoot artifacts
                shooter.setPower(1);
                // Start the timer by resetting it to zero
                runtime.reset();
                sequenceStarted = true;
            }
            if (sequenceStarted && runtime.seconds() >= 1.5) {
                // The second thing happens after 1.5 seconds
                pushythingy.setPosition(0);
                sequenceStarted = false;
            }
            else pushythingy.setPosition(1);
            */

            if(gamepad2.right_trigger > 0.5) {
                intake.setPower(-1);
            }
            else {
                intake.setPower(0);
            }


            if (gamepad2.x) {        //"X" on Gamepad 2 stops both the intake and shooter
                intake.setPower(0);
                shooter.setPower(0);
            }

            if(gamepad2.dpad_down) {
                shooter.setVelocity(1650);
                shooter.setPower(1);
            }

            if(gamepad2.y) {
                pushythingy.setPosition(0);
            }
            else pushythingy.setPosition(1);



            //drivetrain
            telemetry.addData("Front Right", frontRightMotor.getPower());
            telemetry.addData("Front Left", frontLeftMotor.getPower());
            telemetry.addData("Back Right", backRightMotor.getPower());
            telemetry.addData("BackLeft", backLeftMotor.getPower());

            telemetry.addData("Shooter Velocity", shooter.getVelocity()); //shooter speed
            telemetry.addData("Intake", intake.getPower()); //intake power

            telemetry.update();

        }
    }
}
