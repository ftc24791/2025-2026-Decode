package org.firstinspires.ftc.teamcode.opmodes.teleops.officialdrivecontrol;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Controls", group = "B: Controls List")
public class Controls_List extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addLine("--==DRIVER 1 CONTROLS==--");
        telemetry.addData("Left Joystick", "Forward/Backward & Strafe");
        telemetry.addData("Right Joystick", "Rotation");
        telemetry.addData("Right Trigger", "Slow Mode, based on press intenstiy");
        telemetry.addData("Button B", "Enable Brake Mode: Press & hold");
        telemetry.addData("Button Options", "Reset IMU Heading");
        telemetry.addLine();
        telemetry.addLine("--==DRIVER 2 CONTROLS==--");
        telemetry.addData("Right Trigger", "Intake In: Press & Hold");
        telemetry.addData("Left Trigger", "Intake Out: Press & Hold");
        telemetry.addData("DPad Up", "Start Shooter, Far Launch Zone: Toggle");
        telemetry.addData("DPad Down", "Start Shooter, Close Launch Zone: Toggle");
        telemetry.addData("Button Y", "Pushythingy Up: Press & Hold");
        telemetry.addData("Button X", "Shooter Off");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            telemetry.update();

        }
    }
}

