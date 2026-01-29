package org.firstinspires.ftc.teamcode.opmodes.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Intake {
    private DcMotorEx intake;

    public Intake(DcMotorEx intake) {
        this.intake = intake;
        this.intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }


}
