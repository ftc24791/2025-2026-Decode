package org.firstinspires.ftc.teamcode.opmodes.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Spindexer {

/**
 * How to use:
 *  -- if (something) spindexer.shoot(); --
 */
    private DcMotorEx spindexerMotor;
    int TICKS_PER_REV;
    int NUM_SLOTS;
    int TICKS_PER_SLOT;
    int currentSlot;
    int targetPosition = 0;

    public Spindexer(DcMotorEx spinmotor, int ticksPerRev, int numSlots) {
        this.spindexerMotor = spinmotor;
        this.TICKS_PER_REV = ticksPerRev;
        this.NUM_SLOTS = numSlots;
        this.TICKS_PER_SLOT = ticksPerRev / numSlots;
        this.currentSlot = 0;
        this.targetPosition = spinmotor.getCurrentPosition();
    }

    public void shoot() {
        int TARGET = spindexerMotor.getCurrentPosition() + TICKS_PER_REV;
        spindexerMotor.setTargetPosition(TARGET);
        spindexerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spindexerMotor.setPower(1);
    }

    public void intakeOneSlot() {
        int TARGET = spindexerMotor.getCurrentPosition() - TICKS_PER_SLOT;
        spindexerMotor.setTargetPosition(TARGET);
        spindexerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spindexerMotor.setPower(-1);

    }
    public void stopSpindexer() {
        spindexerMotor.setPower(0);
    }

    public void manualSpin(double power) {
        spindexerMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        spindexerMotor.setPower(power);
    }
}
