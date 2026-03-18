package com.jeyers.sstkit;

import java.text.DecimalFormat;

public class Vector2Data {
    public double x;
    public double y;

    public Vector2Data(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void round() {
        this.x = Math.round(this.x);
        this.y = Math.round(this.y);
    }

    public static String vector2ToString(Vector2Data in, String separator) {
        DecimalFormat decimalFormat = new DecimalFormat("0.######");
        return decimalFormat.format(in.x)
                + separator
                + decimalFormat.format(in.y);
    }
}