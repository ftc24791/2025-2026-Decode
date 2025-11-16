package org.firstinspires.ftc.teamcode.opmodes.autonomi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmodes.Movement;

@Autonomous
public class RedGoal extends LinearOpMode {

    private Movement movement;
    private DcMotor shooter;
    private DcMotor intake;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        DcMotor shooter = hardwareMap.dcMotor.get("shooter");

        movement = new Movement(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        //Add Autonomous instructions here (functions) PS. Make sure to adjust.
        movement.moveBack(1, 600);
        movement.turnRight(1, 90);
        movement.strafeRight(1, 500);
    }
}