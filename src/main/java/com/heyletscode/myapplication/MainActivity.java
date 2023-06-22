package com.heyletscode.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        //initialize the city arrayList
        ArrayList<City> arrayList = new ArrayList<>();

        //add cities to arrayList
        arrayList.add(new City(R.drawable.bulgaria, "Sofia"));
        arrayList.add(new City(R.drawable.romania, "Bucharest"));
        arrayList.add(new City(R.drawable.hungary, "Budapest"));
        arrayList.add(new City(R.drawable.greece, "Athens"));
        arrayList.add(new City(R.drawable.italy, "Rome"));
        arrayList.add(new City(R.drawable.austria, "Vienna"));
        arrayList.add(new City(R.drawable.germany, "Berlin"));
        arrayList.add(new City(R.drawable.france, "Paris"));
        arrayList.add(new City(R.drawable.serbia, "Belgrade"));
        arrayList.add(new City(R.drawable.italy, "Turin"));
        arrayList.add(new City(R.drawable.germany, "Munich"));
        arrayList.add(new City(R.drawable.france, "Nancy"));
        arrayList.add(new City(R.drawable.greece, "Heraclio"));
        arrayList.add(new City(R.drawable.austria, "Salzburg"));
        arrayList.add(new City(R.drawable.romania, "Cluj-Napoca"));

        //using the adapter we just made:
        CityAdapter cityAdapter = new CityAdapter(this, R.layout.list_row, arrayList);

        listView.setAdapter(cityAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Singleton.val = i;
                startActivity(new Intent(MainActivity.this, DisplayActivity.class));
            }
        });
    }
}