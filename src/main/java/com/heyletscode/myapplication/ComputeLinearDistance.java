package com.heyletscode.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class ComputeLinearDistance extends Service {

    private static final String TAG = "DisplayActivity";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private final IComputeLinearDistanceInterface.Stub binder = new IComputeLinearDistanceInterface.Stub() {
        //
        public double compute(double latitude1, double longitude1, double latitude2, double longitude2) throws RemoteException {
            //Log.d(TAG, String.format("Compute.compute(%f, %f, %f, %f)", latitude1, latitude2, longitude1, longitude2));
            //return value1 + value2;
            latitude1 = Math.toRadians(latitude1);
            longitude1 = Math.toRadians(longitude1);
            latitude2 = Math.toRadians(latitude2);
            longitude2 = Math.toRadians(longitude2);

            double x = (longitude2 - longitude1) * Math.cos((latitude1 + latitude2) / 2);
            double y = latitude2 - latitude1;

            //use 6371.01 as earth radius
            //double dist_km = Math.sqrt(x*x + y*y) * 6371.01;

            double dist_km = Math.round(Math.sqrt(x*x + y*y) * 6371.01 * 100) / 100;


            return dist_km;
        }
    };

}
