package com.jeyers.sstkit;

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
}