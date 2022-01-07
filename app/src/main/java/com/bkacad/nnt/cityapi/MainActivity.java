package com.bkacad.nnt.cityapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String URL = "https://dataservice.accuweather.com/locations/v1/topcities/50?apikey=CqlyQGP8qAHI1AcDlUKLYfpwgA0dad6b&language=vi-VN";

    private ArrayList<City> cityList = new ArrayList<>();

    private Button btnGet;
    private ListView lvCity;
    private MyAdapter myAdapter;


    private void getData(){
        StringRequest myRequest = new StringRequest(Request.Method.GET, URL,
                response -> {
                    try{
                        JSONArray myJsonArr = new JSONArray(response);
                        // Xử lý , thêm dữ liệu list vào đây

                        for(int i = 0 ; i < myJsonArr.length(); i++){
                            City item = new City();
                            JSONObject tmp = myJsonArr.getJSONObject(i);
                            item.setName(tmp.getString("EnglishName"));
                            item.setArea(tmp.getJSONObject("Region").getString("EnglishName"));
                            item.setLongitude(tmp.getJSONObject("GeoPosition").getString("Longitude"));
                            item.setLatitude(tmp.getJSONObject("GeoPosition").getString("Latitude"));
                            Log.d("city", item.toString());
                            this.cityList.add(item);
                        }
                        myAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                volleyError -> Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(myRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCity = findViewById(R.id.lvCity);
        btnGet = findViewById(R.id.btnGet);

        // Request lên Server -> lấy dữ liệu về -> bóc tách thông tin, in console
//        getData();

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        myAdapter = new MyAdapter(this, cityList);
        lvCity.setAdapter(myAdapter);

        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, cityList.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}