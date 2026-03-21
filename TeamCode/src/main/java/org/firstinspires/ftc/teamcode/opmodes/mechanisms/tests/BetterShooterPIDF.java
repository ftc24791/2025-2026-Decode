package org.firstinspires.ftc.teamcode.opmodes.mechanisms.tests;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class BetterShooterPIDF { //because the other one sucked
    private DcMotorEx motor;

    private double kP;
    private double kI;
    private double kD;
    private double kF;
    /**
     * How to tune:
     * 1. start with all values at zero
     * 2. feedforward - increase until it does abt 90 to 95% of velocity
     * 3. add "P" until the target it reached fast without oscillation
     * 4. probably leave kI and kD at 0 for flywheel
     * 5. (use Panels to maintain sanity)
     */
    //Core equation: power = (P * error) + (I * sumError) + (D * derivative) + (F * target)

    private double lastError;
    private double integral;

    public void PIDFController(DcMotorEx motor) {
        this.motor = motor;
    }

    public void update(double targetVelocity) {
        double currentVelocity = motor.getVelocity();
        double error = targetVelocity - currentVelocity;

        integral += error;
        double derivative = error - lastError;

        double power = (kP * error) + (kI * integral) + (kD * derivative) + (kF * targetVelocity);
        /* ^^^look! its matches the core equation!!!^^^ */

        motor.setPower(power);

        lastError = error;
        /*
          more stuff to add on later AFTER this works as is:
          1. power clamp
          2. "isAtTarget" boolean to check if curVelo is close enough to shoot properly
          3. run without enocders
         */
    }
}
