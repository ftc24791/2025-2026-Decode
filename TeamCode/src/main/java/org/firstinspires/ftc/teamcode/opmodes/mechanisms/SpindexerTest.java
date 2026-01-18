package org.firstinspires.ftc.teamcode.opmodes.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class SpindexerTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor spindexer = hardwareMap.dcMotor.get("spindexer");

        spindexer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        spindexer.setDirection(DcMotorSimple.Direction.FORWARD);

        spindexer.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            if (gamepad1.a) {
                spindexer.setPower(1);
            }
            else if (gamepad1.b) {
                spindexer.setPower(-1);
            } else {
                spindexer.setPower(0);
            }

        }
    }
}
