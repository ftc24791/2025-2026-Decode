package org.firstinspires.ftc.teamcode.opmodes.mechanisms.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

@TeleOp
public class SpindexerTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor spindexer = hardwareMap.dcMotor.get("spindexer");
        NormalizedColorSensor colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
        DigitalChannel magneticLimit1 = hardwareMap.get(DigitalChannel.class, "magneticLimit1");
        DigitalChannel magneticLimit2 = hardwareMap.get(DigitalChannel.class, "magneticLimit2");
        magneticLimit1.setMode(DigitalChannel.Mode.INPUT);
        magneticLimit2.setMode(DigitalChannel.Mode.INPUT);


        spindexer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        spindexer.setDirection(DcMotorSimple.Direction.FORWARD);

        spindexer.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
/*
            if (gamepad1.a) {
                spindexer.setPower(1);
            }
            else if (gamepad1.b) {
                spindexer.setPower(-1);
            } else {
                spindexer.setPower(0);
            }

 */

            if (!magneticLimit1.getState() || !magneticLimit2.getState() ) {
                // Magnet is detected, stop motor
                spindexer.setPower(0);
                sleep(500);
                telemetry.addLine("wow! magnet sensed! spinny thing is not moving. (it shouldnt)");
            } else if (magneticLimit1.getState() || magneticLimit2.getState()) {
                // No magnet, allowed to move
                spindexer.setPower(-1);
                sleep(500);
                telemetry.addLine("wow >:( no magnet! spinnything is moving lol ");
            }

            telemetry.update();



        }
    }
}
