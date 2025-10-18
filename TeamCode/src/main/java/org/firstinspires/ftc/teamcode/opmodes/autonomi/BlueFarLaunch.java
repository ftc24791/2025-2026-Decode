package org.firstinspires.ftc.teamcode.opmodes.autonomi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.opmodes.Movement;
import org.opencv.imgproc.Moments;


@Autonomous
public class BlueFarLaunch extends LinearOpMode {


    private Movement movement;
    private DcMotor shooter;
    private DcMotor intake;


    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        DcMotor shooter = hardwareMap.dcMotor.get("shooter");

        movement = new Movement (hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        //Add Autonomous instructions here (functions):
        movement.moveForward(1, 1000);


    }


}
