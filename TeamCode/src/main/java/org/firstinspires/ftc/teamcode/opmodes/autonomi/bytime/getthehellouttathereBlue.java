package org.firstinspires.ftc.teamcode.opmodes.autonomi.bytime;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Movement;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.ShooterPIDF;

@Autonomous
public class getthehellouttathereBlue extends LinearOpMode {

    private Movement movement;
    private DcMotor shooter;
    private DcMotor intake;

    @Override
    public void runOpMode() throws InterruptedException {

        Hardware robot = new Hardware();
        robot.init(hardwareMap);


        ShooterPIDF shooterPIDF = new ShooterPIDF(
                hardwareMap,
                "shooter",
                0.002, 0.0, 0.0001, 0.00005
        );
        movement = new Movement(hardwareMap, telemetry);


        waitForStart();

        if (isStopRequested()) return;

        //Add Autonomous instructions here (functions) PS. Make sure to adjust.
        movement.moveForward(1,500);

    }

}
