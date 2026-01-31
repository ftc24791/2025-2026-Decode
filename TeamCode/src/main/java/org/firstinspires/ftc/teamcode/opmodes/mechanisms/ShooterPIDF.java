package org.firstinspires.ftc.teamcode.opmodes.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class ShooterPIDF {

    private DcMotorEx shooter;
    private PIDFController pidf;

    private double targetVelocity = 0;

    public ShooterPIDF(HardwareMap hardwareMap, String motorName,
                       double kP, double kI, double kD, double kF) {

        shooter = hardwareMap.get(DcMotorEx.class, motorName);
        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);


        pidf = new PIDFController(kP, kI, kD, kF);
    }

    public double getVelocity() {
        return shooter.getVelocity();
    }

    public void setTargetVelocity(double velocity) {
        this.targetVelocity = velocity;
        pidf.setSetPoint(velocity);
    }

    public void update() {
        double currentVelocity = shooter.getVelocity(); // ticks per second

        double power = pidf.calculate(currentVelocity);

        // Safety clamp
        if (power > 1) power = 1;
        if (power < -0.1) power = -0.1;

        shooter.setPower(power);
    }

    public double getCurrentVelocity() {
        return shooter.getVelocity();
    }

    public double getTargetVelocity() {
        return targetVelocity;
    }

    public boolean atSpeed(double tolerance) {
        return Math.abs(getCurrentVelocity() - targetVelocity) <= tolerance;
    }
}
