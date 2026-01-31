package org.firstinspires.ftc.teamcode.opmodes.mechanisms.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class SpindexerTestTicks extends LinearOpMode {

    int NUM_SLOTS = 3;
    int TICKS_PER_REV = 420; // check
    int TICKS_PER_SLOT = TICKS_PER_REV / NUM_SLOTS;
    int currentSlot = 0; //pindexer needs to be aligned properlyt

    DcMotorEx spindexer = hardwareMap.get(DcMotorEx.class, "spindexer");

    @Override
    public void runOpMode() {
        spindexer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spindexer.setDirection(DcMotor.Direction.FORWARD);
        spindexer.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spindexer.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        waitForStart();
        if (gamepad1.a) {
            shoot();
        } else if (gamepad1.b) {
            intakeOneSlot();
        } else {
            spindexer.setPower(0);
        }
    }

    public void shoot() { //hopefully rapid shoot?
        int TARGET = spindexer.getCurrentPosition() + TICKS_PER_REV;
        spindexer.setTargetPosition(TARGET);
        spindexer.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spindexer.setPower(1);
        while (spindexer.isBusy()) {
        }
        spindexer.setPower(0);
        spindexer.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void intakeOneSlot() {
        int TARGET = spindexer.getCurrentPosition() - TICKS_PER_SLOT;
        spindexer.setTargetPosition(TARGET);
        spindexer.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spindexer.setPower(-1);
        while (spindexer.isBusy()) {
        }
        spindexer.setPower(0);
        spindexer.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
