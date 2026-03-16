package com.jeyers.sstkit;

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
        return in.x + separator + in.y + separator + in.z;
    }
}