package org.firstinspires.ftc.teamcode.opmodes.autonomi.bytime;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Movement;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.ShooterPIDF;

@Autonomous
public class BlueGoalbyTime extends LinearOpMode {

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
        movement.moveForward(1, 1375);
        robot.shooter.setVelocity(1140); //tune
        sleep(3000);
        movement.turnRight(1,60);
        robot.pushythingy.setPosition(0);
        sleep(2000);
        robot.pushythingy.setPosition(1);
        sleep(1000);
        robot.intake.setPower(1);
        sleep(800);
        robot.intake.setPower(0);
        sleep(1000);
        robot.pushythingy.setPosition(0);
        sleep(2000);
        robot.pushythingy.setPosition(1);
        sleep(1000);
        robot.intake.setPower(1);
        sleep(1600);
        robot.pushythingy.setPosition(0);
        sleep(1000);
        robot.shooter.setPower(0);
        robot.pushythingy.setPosition(1);
        sleep(1000);
        movement.strafeLeft(1,600);
        movement.moveBack(1,600);
        movement.stopMotors();

    }

}
