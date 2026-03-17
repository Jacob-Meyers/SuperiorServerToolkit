package com.jeyers.sstkit;

import java.text.DecimalFormat;

import static java.io.File.separator;

public class Vector3Data {
    public double x;
    public double y;
    public double z;

    public Vector3Data(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3Data vector3Round(Vector3Data in){
        in.x = Math.round(x);
        in.y = Math.round(y);
        in.z = Math.round(z);
        return in;
    }

    public static String vector3ToString(Vector3Data in, String separator) {
        DecimalFormat decimalFormat = new DecimalFormat("0.######");
        return decimalFormat.format(in.x)
                + separator
                + decimalFormat.format(in.y)
                + separator
                + decimalFormat.format(in.z);
    }
}