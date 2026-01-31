package org.firstinspires.ftc.teamcode.opmodes.autonomi.bytime;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.MovementbyTime;

@Autonomous
public class SimpleMove extends LinearOpMode {

    private MovementbyTime movementbyTime;
    private DcMotor shooter;
    private DcMotor intake;

    @Override
    public void runOpMode() throws InterruptedException {

        Hardware robot = new Hardware();
        robot.init(hardwareMap);

        movementbyTime = new MovementbyTime(hardwareMap, telemetry);

        waitForStart();

        if (isStopRequested()) return;

        movementbyTime.moveForward(1,1000);

    }

}
