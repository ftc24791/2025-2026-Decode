package org.firstinspires.ftc.teamcode.opmodes.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

public class Spindexer {

    private String[] slots = new String[3]; //slots

    private DcMotor spindexerMotor;
    private NormalizedColorSensor colorSensor;
    private DigitalChannel magneticLimit;

    public Spindexer(DcMotor motor, NormalizedColorSensor colorSensor, DigitalChannel magneticLimit) {
        spindexerMotor = motor;
        this.colorSensor = colorSensor;
        this.magneticLimit = magneticLimit;
        intake();
    }
    public void intake() {

    }
    public void shoot() {

    }
}
