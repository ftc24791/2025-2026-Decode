package org.firstinspires.ftc.teamcode.opmodes.autonomi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.opmodes.Movement;

@Autonomous
public class RedGoal extends LinearOpMode {

    private Movement movement;
    private DcMotor shooter;
    private DcMotor intake;

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor"); //CH Motor Port 0
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor"); //CH Motor Port 1
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor"); //CH Motor Port 2
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor"); //CH Motor Port 3

        DcMotor intake = hardwareMap.dcMotor.get("intake"); //EH Motor Port 0
        DcMotorEx shooter = hardwareMap.get(DcMotorEx.class, "shooter"); //EH Motor Port 1

        Servo pushythingy = hardwareMap.servo.get("pushythingy"); //UPDATE: CH Servo Port 0


        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        shooter.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        if (isStopRequested()) return;

        //Add Autonomous instructions here (functions) PS. Make sure to adjust.
        movement.moveForward(1, 400);
        shooter.setPower(1);
        sleep(2000);
        intake.setPower(1);
        sleep(500);
        pushythingy.setPosition(0);

    }
}