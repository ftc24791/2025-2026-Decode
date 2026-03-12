package org.firstinspires.ftc.teamcode.opmodes.teleops;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Hardware;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.ShooterPIDF;
import org.firstinspires.ftc.teamcode.opmodes.mechanisms.Spindexer;

@Configurable //makes it so now we can tune values in Panels i think
@TeleOp(name = "TeleOperation - Competition", group = "A: Official Drive Control")
public class TeleOperation extends LinearOpMode {

    int NUM_SLOTS = 3;
    int TICKS_PER_REV = 3500; // for core hex
    int TICKS_PER_SLOT = TICKS_PER_REV / NUM_SLOTS; // shud be 96
    int currentSlot = 0; //pindexer needs to be aligned properlyt

    //allows for panels to tune these
    public static double SHOOTER_kP = 0.002;
    public static double SHOOTER_kI = 0.0;
    public static double SHOOTER_kD = 0.0001;
    public static double SHOOTER_kF = 0.00005;

    boolean readyToShoot;

    Hardware robot = new Hardware();


    //DcMotorEx spindexer;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);
        Spindexer spindexer = new Spindexer(robot.spindexer, 420, 3);
        Intake intake = new Intake(robot.intake);

        boolean turning = false;

        ShooterPIDF shooterPIDF = new ShooterPIDF(
                hardwareMap, "shooter",
                SHOOTER_kP,
                SHOOTER_kI,
                SHOOTER_kD,
                SHOOTER_kF
        );

        double minShootVel = 1300;


        waitForStart();
        if (isStopRequested()) return;



        while (opModeIsActive()) {

            boolean joystickActive = Math.abs(gamepad1.left_stick_x) > 0.3 || Math.abs(gamepad1.left_stick_y) > 0.3 || Math.abs(gamepad1.right_stick_x) > 0.3;

            shooterPIDF.update();
            intake.update();

            double currentShootVel = robot.shooter.getVelocity();

            boolean manualSpinMode = false;
            double y = -gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;
            double x = gamepad1.left_stick_x;

            double rotX = x * 1.1;
            double rotY = y;

            double botHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double fieldX = rotX * Math.cos(-botHeading) - rotY * Math.sin(-botHeading);
            double fieldY = rotX * Math.sin(-botHeading) + rotY * Math.cos(-botHeading);

            double speedMultiplier = 1.0 - (0.6 * gamepad1.right_trigger);
            double denominator = Math.max(Math.abs(fieldY) + Math.abs(fieldX) + Math.abs(rx), 1);

            double frontLeftPower = (fieldY + fieldX + rx) / denominator * speedMultiplier;
            double backLeftPower = (fieldY - fieldX + rx) / denominator * speedMultiplier;
            double frontRightPower = (fieldY - fieldX - rx) / denominator * speedMultiplier;
            double backRightPower = (fieldY + fieldX - rx) / denominator * speedMultiplier;

            robot.frontLeftMotor.setPower(frontLeftPower);
            robot.backLeftMotor.setPower(backLeftPower);
            robot.frontRightMotor.setPower(frontRightPower);
            robot.backRightMotor.setPower(backRightPower);


            if (gamepad2.x) {
                robot.intake.setPower(0);
                shooterPIDF.setTargetVelocity(0);
                spindexer.stopSpindexer();
            }


            if (gamepad2.dpad_down) {
                shooterPIDF.setTargetVelocity(1300);
                ; //tune
            }

            if (gamepad2.dpad_up) {
                shooterPIDF.setTargetVelocity(1420); //tune
            }


            if (gamepad2.right_bumper) {
                intake.setState(Intake.IntakeState.INTAKE);
            }
            else if (gamepad2.left_bumper) {
                intake.setState(Intake.IntakeState.OUTTAKE);
            }
            else {
                intake.setState(Intake.IntakeState.IDLE);
            }

            if (gamepad1.options) {
                robot.imu.resetYaw();
            }

            if (gamepad2.y && currentShootVel >= minShootVel) {
                spindexer.shoot();
            }

            if (gamepad2.a) spindexer.intakeOneSlot();

            if (gamepad2.right_trigger > 0.5 || gamepad2.left_trigger > 0.5) {
                manualSpinMode = true;
                if (gamepad2.right_trigger > 0.5) {
                    spindexer.manualSpin(1);
                } else if (gamepad2.left_trigger > 0.5) {
                    spindexer.manualSpin(-1);
                }
            } else {
                if (manualSpinMode && (gamepad2.right_trigger < 0.5 && gamepad2.left_trigger < 0.5)) {
                    spindexer.stopSpindexer();
                    manualSpinMode = false;
                }
            }

            if (gamepad1.left_trigger > 0.5) {
                robot.frontLeftMotor.setZeroPowerBehavior(BRAKE);
                robot.frontRightMotor.setZeroPowerBehavior(BRAKE);
                robot.backLeftMotor.setZeroPowerBehavior(BRAKE);
                robot.backRightMotor.setZeroPowerBehavior(BRAKE);
                telemetry.addLine("Brake Mode On");
            } else {
                robot.frontLeftMotor.setZeroPowerBehavior(FLOAT);
                robot.frontRightMotor.setZeroPowerBehavior(FLOAT);
                robot.backLeftMotor.setZeroPowerBehavior(FLOAT);
                robot.backRightMotor.setZeroPowerBehavior(FLOAT);
                telemetry.addLine("Brake Mode Off");
            }

            if (joystickActive) { //this basically ensures the joysticks have priority
                turning = false;
            }

            if (!turning && !joystickActive) {
                if (gamepad1.dpad_up) {
                    robot.frontLeftMotor.setPower(1);
                    robot.frontRightMotor.setPower(1);
                    robot.backLeftMotor.setPower(1);
                    robot.backRightMotor.setPower(1);
                } else if (gamepad1.dpad_down) {
                    robot.frontLeftMotor.setPower(-1);
                    robot.frontRightMotor.setPower(-1);
                    robot.backLeftMotor.setPower(-1);
                    robot.backRightMotor.setPower(-1);
                } else {
                    robot.frontLeftMotor.setPower(frontLeftPower);
                    robot.backLeftMotor.setPower(backLeftPower);
                    robot.frontRightMotor.setPower(frontRightPower);
                    robot.backRightMotor.setPower(backRightPower);
                }



            }


            telemetry.addData("Front Right", robot.frontRightMotor.getPower());
            telemetry.addData("Front Left", robot.frontLeftMotor.getPower());
            telemetry.addData("Back Right", robot.backRightMotor.getPower());
            telemetry.addData("BackLeft", robot.backLeftMotor.getPower());
            telemetry.addData("Heading", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.addLine();
            telemetry.addData("Shooter Velocity", robot.shooter.getVelocity());
            telemetry.addData("Intake", robot.intake.getPower());
            telemetry.addLine();
            telemetry.addData("Spindexer Power", robot.spindexer.getPower());
            telemetry.addData("Spindexer Velocity", robot.spindexer.getVelocity());
            telemetry.addData("Spindexer Position", robot.spindexer.getCurrentPosition());
            telemetry.update();
        }
    }


}
