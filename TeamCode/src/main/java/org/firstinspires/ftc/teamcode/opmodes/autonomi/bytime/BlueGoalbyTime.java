 package org.firstinspires.ftc.teamcode.opmodes.autonomi.bytime;

 import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
 import com.qualcomm.robotcore.eventloop.opmode.Disabled;
 import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

 import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;
 import org.firstinspires.ftc.teamcode.opmodes.mechanisms.MovementbyTime;
 import org.firstinspires.ftc.teamcode.opmodes.mechanisms.ShooterPIDF;
 import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Spindexer;

 @Disabled
@Autonomous
public class BlueGoalbyTime extends LinearOpMode {

    private MovementbyTime movementbyTime;
    int NUM_SLOTS = 3;
    int TICKS_PER_REV = 288; // for core hex
    int TICKS_PER_SLOT = TICKS_PER_REV / NUM_SLOTS; // shud be 96
    int currentSlot = 0; //pindexer needs to be aligned properlyt

    //allows for panels to tune these
    public static double SHOOTER_kP = 0.002;
    public static double SHOOTER_kI = 0.0;
    public static double SHOOTER_kD = 0.0001;
    public static double SHOOTER_kF = 0.00005;

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() throws InterruptedException {

        Hardware robot = new Hardware();
        robot.init(hardwareMap);
        Spindexer spindexer = new Spindexer(robot.spindexer, 420, 3); //check

        ShooterPIDF shooterPIDF = new ShooterPIDF(
                hardwareMap, "shooter",
                SHOOTER_kP,
                SHOOTER_kI,
                SHOOTER_kD,
                SHOOTER_kF
        );
        movementbyTime = new MovementbyTime(hardwareMap, telemetry);


        waitForStart();

        if (isStopRequested()) return;

        shooterPIDF.update();

        //Add Autonomous instructions here (functions) PS. Make sure to adjust.
        movementbyTime.moveBack(1, 1375);
        robot.shooter.setVelocity(1140); //tune
        sleep(3000);
        movementbyTime.turnRight(1,60);
        spindexer.shoot();
        sleep(2000);
        robot.shooter.setPower(0);
        sleep(1000);
        movementbyTime.strafeLeft(1,600);
        movementbyTime.moveBack(1,600);
        movementbyTime.stopMotors();

    }

}

