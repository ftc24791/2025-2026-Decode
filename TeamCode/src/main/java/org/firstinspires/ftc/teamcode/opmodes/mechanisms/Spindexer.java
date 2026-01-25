package org.firstinspires.ftc.teamcode.opmodes.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Spindexer {

/**
 * How to use:
 *  -- if (something) spindexer.shoot(); --
 */
    private DcMotorEx spindexer;
    int TICKS_PER_REV;
    int NUM_SLOTS;
    int TICKS_PER_SLOT;
    int currentSlot;

    public Spindexer(DcMotorEx motor, int ticksPerRev, int numSlots) {
        this.spindexer = motor;
        this.TICKS_PER_REV = ticksPerRev;
        this.NUM_SLOTS = numSlots;
        this.TICKS_PER_SLOT = ticksPerRev / numSlots;
        this.currentSlot = 0;
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
