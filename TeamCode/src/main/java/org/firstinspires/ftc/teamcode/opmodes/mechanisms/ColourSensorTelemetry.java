package org.firstinspires.ftc.teamcode.opmodes.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp
public class ColourSensorTelemetry extends LinearOpMode {
    private NormalizedColorSensor sensor_color;
    double hue;

    @Override
    public void runOpMode() {
        sensor_color = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Light Detected", ((OpticalDistanceSensor) sensor_color).getLightDetected());
            NormalizedRGBA colors = sensor_color.getNormalizedColors();
            hue = JavaUtil.colorToHue(colors.toColor());

            //Determining the amount of red, green, and blue
            telemetry.addData("Red", "%.3f", colors.red);
            telemetry.addData("Green", "%.3f", colors.green);
            telemetry.addData("Blue", "%.3f", colors.blue);

            //Determining HSV and alpha
            telemetry.addData("Hue", JavaUtil.colorToHue(colors.toColor()));
            telemetry.addData("Saturation", "%.3f", JavaUtil.colorToSaturation(colors.toColor()));
            telemetry.addData("Value", "%.3f", JavaUtil.colorToValue(colors.toColor()));
            telemetry.addData("Alpha", "%.3f", colors.alpha);

            //Using hue to detect color
            if(hue < 30){
                telemetry.addData("Color", "Red");
            }
            else if (hue < 60) {
                telemetry.addData("Color", "Orange");
            }
            else if (hue < 90){
                telemetry.addData("Color", "Yellow");
            }
            else if (hue < 150){
                telemetry.addData("Color", "Green");
            }
            else if (hue < 225){
                telemetry.addData("Color", "Blue");
            }
            else if (hue < 350){
                telemetry.addData("Color", "Purple");
            }
            else{
                telemetry.addData("Color", "Red");
            }
            telemetry.update();
        }
    }
}