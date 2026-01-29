package org.firstinspires.ftc.teamcode.opmodes.autonomi.bytime;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Movement;
@Disabled
@Autonomous
public class RedFarLaunchbyTime extends LinearOpMode {

    private DcMotor shooter;
    private DcMotor intake;


    @Override
    public void runOpMode() throws InterruptedException {

        Movement movement = new Movement(hardwareMap, telemetry);

        waitForStart();

        if (isStopRequested()) return;


        //Autonomous intructions here:
        movement.moveForward(1, 750);

    }
}
