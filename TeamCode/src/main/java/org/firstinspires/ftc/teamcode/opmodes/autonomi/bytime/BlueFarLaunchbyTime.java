package org.firstinspires.ftc.teamcode.opmodes.autonomi.bytime;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Movement;


@Autonomous
public class BlueFarLaunchbyTime extends LinearOpMode {


    private Movement movement;
    private DcMotor shooter;
    private DcMotor intake;


    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        DcMotor shooter = hardwareMap.dcMotor.get("shooter");

        movement = new Movement (hardwareMap, telemetry);

        waitForStart();

        if (isStopRequested()) return;

        //Add Autonomous instructions here (functions):
        movement.moveForward(1, 500);


    }


}
