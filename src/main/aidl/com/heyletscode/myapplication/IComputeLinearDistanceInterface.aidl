// IComputeLinearDistanceInterface.aidl
package com.heyletscode.myapplication;

// Declare any non-default types here with import statements

interface IComputeLinearDistanceInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    double compute(double latitude1, double longitude1, double latitude2, double longitude2);
}