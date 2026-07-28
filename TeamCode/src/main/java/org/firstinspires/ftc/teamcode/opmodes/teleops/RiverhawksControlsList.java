package org.firstinspires.ftc.teamcode.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Driver Controls", group = "B: Controls List")
public class RiverhawksControlsList extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addLine("--==DRIVER 1 CONTROLS==--");
        telemetry.addData("Left Joystick", "Forward/Backward");
        telemetry.addData("Right Joystick", "Rotation");
        telemetry.addData("Right Trigger", "Slow Mode, based on press intensity");
        telemetry.addData("Button: A", "Run Pushythingy servo, Press and Hold");
        telemetry.addLine();
        telemetry.addLine("--==NO DRIVER 2 CONTROLS==--");
        telemetry.addLine("sorry, jeffrey :(");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            telemetry.update();

        }
    }
}

