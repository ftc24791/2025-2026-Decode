package org.firstinspires.ftc.teamcode.opmodes.mechanisms;

/*
Taken from:
https://github.com/FTC-23511/SolversLib/blob/master/core/src/main/java/com/seattlesolvers/solverslib/controller/PIDFController.java
 */
public class PIDFController {

    private double kP, kI, kD, kF;
    private double setPoint = 0;

    private double integral = 0;
    private double lastError = 0;
    private long lastTime = 0;

    public PIDFController(double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.lastTime = System.nanoTime();
    }

    public void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
        integral = 0;
        lastError = 0;
    }

    public double calculate(double currentValue) {

        long now = System.nanoTime();
        double dt = (now - lastTime) / 1e9;
        if (dt <= 1e-4) dt = 1e-4;
        lastTime = now;

        double error = setPoint - currentValue;

        integral += error * dt;
        integral = Math.max(-500, Math.min(500, integral));

        double derivative = (error - lastError) / dt;
        lastError = error;

        double p = kP * error;
        double i = kI * integral;
        double d = kD * derivative;
        double f = kF * setPoint;

        return p + i + d + f;
    }
}
