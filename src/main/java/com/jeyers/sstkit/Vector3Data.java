package com.jeyers.sstkit;

import java.text.DecimalFormat;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/16/2026
/// Last Edit    3/16/2026
///

public class Vector3Data {
    public double x;
    public double y;
    public double z;

    public Vector3Data(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void round() {
        this.x = Math.round(this.x);
        this.y = Math.round(this.y);
        this.z = Math.round(this.z);
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