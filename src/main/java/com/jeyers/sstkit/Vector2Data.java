package com.jeyers.sstkit;

import java.text.DecimalFormat;

public class Vector2Data {
    public double x;
    public double y;

    public Vector2Data(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2Data vector2Round(Vector2Data in){
        in.x = Math.round(x);
        in.y = Math.round(y);
        return in;
    }

    public static String vector2ToString(Vector2Data in, String separator) {
        DecimalFormat decimalFormat = new DecimalFormat("0.######");
        return decimalFormat.format(in.x)
                + separator
                + decimalFormat.format(in.y);
    }
}