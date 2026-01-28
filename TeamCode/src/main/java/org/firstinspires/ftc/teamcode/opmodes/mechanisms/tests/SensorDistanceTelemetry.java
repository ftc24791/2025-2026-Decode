package org.firstinspires.ftc.teamcode.opmodes.mechanisms.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class SensorDistanceTelemetry extends LinearOpMode {
    private ColorSensor sensor_color;
    private DistanceSensor sensor_distance;
    double hue;

    @Override
    public void runOpMode() {

        sensor_color = hardwareMap.get(ColorSensor.class, "sensor_color");
        sensor_distance = hardwareMap.get(DistanceSensor.class,"sensor_color");


        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Distance", sensor_distance.getDistance(DistanceUnit.CM));
            telemetry.addData("Red", sensor_color.red());
            telemetry.addData("Green", sensor_color.green());
            telemetry.addData("Blue", sensor_color.blue());

            if (sensor_distance.getDistance(DistanceUnit.CM) < 100) {
                telemetry.addLine("i can see under 100cm!!!");
            }

            if (sensor_color.green() > (sensor_color.red() * 2) && sensor_color.green() > (sensor_color.blue() * 2)) {
                //green
                telemetry.addLine("i see greeeeen!");
            }
            else if (sensor_color.red() > 100 && sensor_color.blue() > 100 && (sensor_color.red() + sensor_color.blue()) > (sensor_color.green() * 2)) {
                //purple
                telemetry.addLine("i see purpleeeeeeeee!");
            } else {
                telemetry.addLine("i either see something, or i dont. but i certainly cant see green or purple");
            }

            telemetry.update();
        }
    }
}