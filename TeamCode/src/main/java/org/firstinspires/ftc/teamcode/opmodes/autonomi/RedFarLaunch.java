package org.firstinspires.ftc.teamcode.opmodes.autonomi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.opmodes.Movement;
import org.opencv.imgproc.Moments;

@Autonomous
public class RedFarLaunch extends LinearOpMode {

    private DcMotor shooter;
    private DcMotor intake;


    @Override
    public void runOpMode() throws InterruptedException {

        Movement movement = new Movement(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;


        //Autonomous intructions here:
        movement.moveForward(1, 1000);

    }
}
