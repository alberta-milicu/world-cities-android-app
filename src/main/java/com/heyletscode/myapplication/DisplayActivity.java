package com.heyletscode.myapplication;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    private static final String TAG = "AIDLCheck";
    private TextView mTextViewResult;
    private RequestQueue mQueue;

    IComputeLinearDistanceInterface service;
    ComputeLinearDistanceConnection connection;

    class ComputeLinearDistanceConnection implements ServiceConnection {

        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IComputeLinearDistanceInterface.Stub.asInterface((IBinder) boundService);
            Log.d(DisplayActivity.TAG, "onServiceConnected() connected");
            Toast.makeText(DisplayActivity.this, "Service connected", Toast.LENGTH_LONG)
                    .show();
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Log.d(DisplayActivity.TAG, "onServiceDisconnected() disconnected");
            Toast.makeText(DisplayActivity.this, "Service connected", Toast.LENGTH_LONG)
                    .show();
        }
    }

    /** Binds this activity to the service. */
    private void initService() {
        connection = new ComputeLinearDistanceConnection();
        Intent i = new Intent();
        i.setClassName("com.heyletscode.myapplication", com.heyletscode.myapplication.ComputeLinearDistance.class.getName());
        boolean ret = bindService(i, connection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "initService() bound with " + ret);
    }

    /** Unbinds this activity from the service. */
    private void releaseService() {
        unbindService(connection);
        connection = null;
        Log.d(TAG, "releaseService() unbound.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);

        ArrayList<Double> longitudeArrayList = new ArrayList<>();
        ArrayList<Double> latitudeArrayList = new ArrayList<>();

        latitudeArrayList.add(42.698334); //1
        latitudeArrayList.add(44.439663); //2
        latitudeArrayList.add(47.497913); //3
        latitudeArrayList.add(37.98381); //4
        latitudeArrayList.add(41.902782); //5
        latitudeArrayList.add(48.2082); //6
        latitudeArrayList.add(52.52); //7
        latitudeArrayList.add(48.8566); //8
        latitudeArrayList.add(44.8125); //9
        latitudeArrayList.add(45.0703); //10
        latitudeArrayList.add(48.1351); //11
        latitudeArrayList.add(48.6921); //12
        latitudeArrayList.add(35.3387); //13
        latitudeArrayList.add(47.8095); //14
        latitudeArrayList.add(46.7712); //15

        //................................//

        longitudeArrayList.add(23.31994); //1
        longitudeArrayList.add(26.096306); //2
        longitudeArrayList.add(19.040236); //3
        longitudeArrayList.add(23.72754); //4
        longitudeArrayList.add(12.496366); //5
        longitudeArrayList.add(16.3738); //6
        longitudeArrayList.add(13.405); //7
        longitudeArrayList.add(2.3522); //8
        longitudeArrayList.add(20.4612); //9
        longitudeArrayList.add(7.6869); //10
        longitudeArrayList.add(11.582); //11
        longitudeArrayList.add(6.1844); //12
        longitudeArrayList.add(25.1442); //13
        longitudeArrayList.add(13.055); //14
        longitudeArrayList.add(23.6236); //15


        mTextViewResult = findViewById(R.id.textView);
//        TextView mComputeText = findViewById(R.id.computeText);

        mQueue = Volley.newRequestQueue(this);

        jsonParse();

        Spinner spinner = findViewById(R.id.spinner);
        
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);

        initService();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                final TextView mComputeText = (TextView) findViewById(R.id.computeText);
                //double distance;
//                mComputeText.setText(Integer.toString(spinner.getSelectedItemPosition()));
                double distance = 0;
                if (service != null) {
                    try {
                        distance = service.compute(latitudeArrayList.get(Singleton.val),
                                longitudeArrayList.get(Singleton.val),
                                latitudeArrayList.get(spinner.getSelectedItemPosition()),
                                longitudeArrayList.get(spinner.getSelectedItemPosition()));
//                        mComputeText.setText((int)distance);
                        Toast.makeText(DisplayActivity.this, Double.toString(distance), Toast.LENGTH_LONG).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "Service not connected");
                }
            }
        });


    }

    private void jsonParse() {

        String url = "https://api.jsonserve.com/MAM3SC";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("city_table");

//                            for(int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject city = jsonArray.getJSONObject(i);
//
//                                String cityName = city.getString("city_name");
//
//                                mTextViewResult.append(cityName + "\n\n");
//                            }

                            JSONObject city = jsonArray.getJSONObject(Singleton.val);

                            String cityName = city.getString("city_name");
                            int cityPopulation = city.getInt("city_population");
                            int cityNormalArea = city.getInt("city_normal_area");
                            int cityMetropolitanArea = city.getInt("city_metropolitan_area");
                            int cityPopDensity = city.getInt("city_pop_density");
                            String cityTimezone = city.getString("city_timezone");
                            String cityAirport = city.getString("city_airport");
                            String cityCurrency = city.getString("city_currency");
                            String cityAttractions = city.getString("city_attractions");
                            String cityBest = city.getString("city_best");
                            double cityTemperature = city.getDouble("city_temperature");
                            String cityLandform = city.getString("city_landform");
                            double cityLatitude = city.getDouble("city_latitude");
                            double cityLongitude = city.getDouble("city_longitude");

                            mTextViewResult.append("City Name: " + cityName + "\n\n"
                                    + "City Population: " + cityPopulation + "\n\n"
                                    + "City Normal Area: " +  cityNormalArea + "\n\n"
                                    +  "City Metropolitan Area: " + cityMetropolitanArea + "\n\n"
                                    + "City Population Density: " +  cityPopDensity + "\n\n"
                                    +  "City Timezone: " + cityTimezone + "\n\n"
                                    +  "City Airport: " +  cityAirport + "\n\n"
                                    +  "City Currency: " +  cityCurrency + "\n\n"
                                    +  "City Attractions: " +  cityAttractions + "\n\n"
                                    +  "City Best Known For: " +  cityBest + "\n\n"
                                    +  "City Temperature: " +  cityTemperature + "\n\n"
                                    +  "City Landform: " +  cityLandform + "\n\n"
                                    +  "City Latitude: " +  cityLatitude + "\n\n"
                                    +  "City Longitude: " +  cityLongitude + "\n\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }

}