package org.firstinspires.ftc.teamcode.opmodes.autonomi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmodes.mechanisms.MovementbyTime;


@Autonomous(name = "Drivetrain Test", group = "Z: Tests")
public class drivetrainTest extends LinearOpMode {

    private MovementbyTime movementbyTime;
    private DcMotor shooter;
    private DcMotor intake;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor"); //CH Motor Port 0
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor"); //CH Motor Port 1
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor"); //CH Motor Port 2
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor"); //CH Motor Port 3

        movementbyTime = new MovementbyTime(hardwareMap, telemetry);

        waitForStart();

        if (isStopRequested()) return;


        frontLeftMotor.setPower(1);
        telemetry.addData("Step", "Front Left running");
        telemetry.addData("Power", frontLeftMotor.getPower());
        telemetry.update();
        sleep(1000);

        frontRightMotor.setPower(1);
        telemetry.addData("Step", "Front Right running");
        telemetry.addData("Power", frontRightMotor.getPower());
        telemetry.update();
        sleep(1000);

        backLeftMotor.setPower(1);
        telemetry.addData("Step", "Back Left running");
        telemetry.addData("Power", backLeftMotor.getPower());
        telemetry.update();
        sleep(1000);

        backRightMotor.setPower(1);
        telemetry.addData("Step", "Back Right running");
        telemetry.addData("Power", backRightMotor.getPower());
        telemetry.update();
        sleep(1000);

        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);

        telemetry.addData("Done", "All motors stopped");
        telemetry.update();
    }
}
