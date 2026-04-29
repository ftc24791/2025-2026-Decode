package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PredictiveBrakingCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.TwoWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Constants { // ENSURE ANY PLACEHOLDERS ARE REPLACED BEFORE PATHING

    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(0) //PLACEHOLDER
            .headingPIDFCoefficients(new PIDFCoefficients(
                    0, 0, 0, 0)) //PLACEHOLDER
            .predictiveBrakingCoefficients(new PredictiveBrakingCoefficients(
                    0, 0, 0)) //PLACEHOLDER
            .centripetalScaling(0) //PLACEHOLDER; also unconfirmed, we may or may not need
            ;


    public static PathConstraints pathConstraints = new PathConstraints(
            0.99, 100, 1, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(new MecanumConstants())
                .twoWheelLocalizer(localizerConstants)
                .build();
    }


        public static MecanumConstants driveConstants = new MecanumConstants()
                .maxPower(1)
                .rightFrontMotorName("frontRightMotor")
                .rightRearMotorName("backRightMotor")
                .leftRearMotorName("backLeftMotor")
                .leftFrontMotorName("frontLeftMotor")
                .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
                .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
                .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
                .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
                .xVelocity(0) //PLACEHOLDER
                .yVelocity(0) //PLACEHOLDER
                ;


        public static TwoWheelConstants localizerConstants = new TwoWheelConstants()
                .forwardEncoder_HardwareMapName("this_is_a_placeholder")
                .strafeEncoder_HardwareMapName("this_is_another_placeholder")
                .IMU_HardwareMapName("imu")
                .IMU_Orientation(
                        new RevHubOrientationOnRobot(
                                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                                RevHubOrientationOnRobot.UsbFacingDirection.UP
                        )
                )
                .forwardPodY(0) //pedro has on the website a way to ...
                .strafePodX(0) // ... automatically find the offsets for 2-wheel
                .forwardEncoderDirection(Encoder.FORWARD) //PLACEHOLDER
                .strafeEncoderDirection(Encoder.FORWARD) //PLACEHOLDER
                .forwardTicksToInches(0) //PLACEHOLDER
                .strafeTicksToInches(0) //PLACEHOLDER
                ;


    }