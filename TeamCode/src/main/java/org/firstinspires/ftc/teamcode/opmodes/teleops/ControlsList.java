package org.firstinspires.ftc.teamcode.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Driver Controls", group = "B: Controls List")
public class ControlsList extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addLine("--==DRIVER 1 CONTROLS==--");
        telemetry.addData("Left Joystick", "Forward/Backward & Strafe");
        telemetry.addData("Right Joystick", "Rotation");
        telemetry.addData("Right Trigger", "Slow Mode, based on press intensity");
        telemetry.addData("Button: B", "Enable Brake Mode: Press & hold");
        telemetry.addData("Button: Options", "Reset IMU Heading");
        telemetry.addData("DPad Up", "Robot-Centric Drive Forward");
        telemetry.addData("DPad Down", "Robot-Centric Drive Backward");
        telemetry.addLine();
        telemetry.addLine("--==DRIVER 2 CONTROLS==--");
        telemetry.addData("Right Trigger", "Spindexer, manual shoot");
        telemetry.addData("Left Trigger", "Spindexer, manual intake");
        telemetry.addData("Right Bumper", "Intake In");
        telemetry.addData("Left Bumper", "Intake Out");
        telemetry.addData("DPad Up", "Start Shooter, Far Launch Zone: Toggle");
        telemetry.addData("DPad Down", "Start Shooter, Close Launch Zone: Toggle");
        telemetry.addData("Button: Y", "Spindexer, Shoot 3");
        telemetry.addData("Button: A", "Spindexer, Intake 1 ");
        telemetry.addData("Button: X", "Hard Motor Stop");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            telemetry.update();

        }
    }
}

