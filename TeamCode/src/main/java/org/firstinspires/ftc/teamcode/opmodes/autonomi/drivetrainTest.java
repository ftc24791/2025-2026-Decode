package org.firstinspires.ftc.teamcode.opmodes.autonomi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmodes.Movement;


@Autonomous
public class drivetrainTest extends LinearOpMode {

    private Movement movement;
    private DcMotor shooter;
    private DcMotor intake;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        DcMotor shooter = hardwareMap.dcMotor.get("shooter");

        movement = new Movement(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        //Add Autonomous instructions here (functions) PS. Make sure to adjust.
        frontLeftMotor.setPower(1);
        sleep(1000);
        frontRightMotor.setPower((1));
        sleep(1000);
        backLeftMotor.setPower((1));
        sleep(1000);
        backRightMotor.setPower((1));
        sleep(1000);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }
}
