package org.firstinspires.ftc.teamcode.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;


@TeleOp(name="Riverhawks Baseball 26", group="A: Official Drive Control")
public class Riverhawks extends LinearOpMode {


    @Override
    public void runOpMode() {

        DcMotorEx frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        DcMotorEx frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        DcMotorEx backRightMotor = hardwareMap.get(DcMotorEx.class, "backRightMotor");
        DcMotorEx backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeftMotor");
        DcMotorEx shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        Servo pushythingy = hardwareMap.servo.get("pushythingy");

        frontLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        frontLeftMotor.setDirection(DcMotorEx.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorEx.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorEx.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorEx.Direction.FORWARD);
        shooter.setDirection(DcMotorEx.Direction.FORWARD); //change to reverse if shooter is spinning the wrong way

        frontLeftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        double leftPower;
        double rightPower;
        double maximumPower;


        waitForStart();
        if(isStopRequested()) return;

        while (opModeIsActive()) {

            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            leftPower = drive + turn;
            rightPower = drive - turn;

            maximumPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));

            if (maximumPower > 1) {
                leftPower /= maximumPower;
                rightPower /= maximumPower;
            }

            if (gamepad1.right_trigger >= 0.5) {
                shooter.setVelocity(2000); //maximum possible velocity hopefully
            }
            else if (gamepad1.right_trigger < 0.5) {
                shooter.setVelocity(0);
            }

            if (gamepad1.aWasPressed()) {
                pushythingy.setPosition(1);
            }
            else if (gamepad1.aWasReleased()) {
                pushythingy.setPosition(0);
            }

            frontLeftMotor.setPower(leftPower);
            backLeftMotor.setPower(leftPower);
            frontRightMotor.setPower(rightPower);
            backRightMotor.setPower(rightPower);

            //if this works implement in shooter and/or spindexer to test stall protection
            telemetry.addData("FL Current, AMPS", frontLeftMotor.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("FR Current, AMPS", frontRightMotor.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("BL Current, AMPS", backLeftMotor.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("BR Current, AMPS", backRightMotor.getCurrent(CurrentUnit.AMPS));

            telemetry.addData("Shooter Velocity", shooter.getVelocity());
            telemetry.update();

        }
    }
}
