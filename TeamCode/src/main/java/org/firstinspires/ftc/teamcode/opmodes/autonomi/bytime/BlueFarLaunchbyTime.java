package org.firstinspires.ftc.teamcode.opmodes.autonomi.bytime;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmodes.mechanisms.MovementbyTime;

@Disabled
@Autonomous
public class BlueFarLaunchbyTime extends LinearOpMode {


    private MovementbyTime movementbyTime;
    private DcMotor shooter;
    private DcMotor intake;


    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        DcMotor shooter = hardwareMap.dcMotor.get("shooter");

        movementbyTime = new MovementbyTime(hardwareMap, telemetry);

        waitForStart();

        if (isStopRequested()) return;

        //Add Autonomous instructions here (functions):
        movementbyTime.moveForward(1, 500);


    }


}
