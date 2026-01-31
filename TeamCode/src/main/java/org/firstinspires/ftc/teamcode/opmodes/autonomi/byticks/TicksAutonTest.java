package org.firstinspires.ftc.teamcode.opmodes.autonomi.byticks;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.tests.MovementTicks;

@Autonomous
public class TicksAutonTest extends LinearOpMode {

    private MovementTicks movementbyticks;
    private DcMotor shooter;
    private DcMotor intake;

    @Override
    public void runOpMode() throws InterruptedException {

        Hardware robot = new Hardware();
        robot.init(hardwareMap);

        movementbyticks = new MovementTicks(hardwareMap, this);

        waitForStart();

        if (isStopRequested()) return;

        movementbyticks.driveForward(10,1);
        sleep(500);
        movementbyticks.driveBackward(10, 1);
        sleep(500);
        movementbyticks.strafeRight(10, 1);
        sleep(500);
        movementbyticks.strafeLeft(10,1);
        sleep(500);
        movementbyticks.turnRight(45,1);
        sleep(500);
        movementbyticks.turnLeft(45,1);

    }

}
