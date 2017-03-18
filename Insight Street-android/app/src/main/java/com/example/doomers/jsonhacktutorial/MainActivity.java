package com.example.doomers.jsonhacktutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



    public class MainActivity extends AppCompatActivity {
        ArrayAdapter<String> adapter;
        ArrayList<String> items;
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ListView listView=(ListView)findViewById(R.id.listv);
            items=new ArrayList<String>();
            adapter=new ArrayAdapter(this, R.layout.item_layout,R.id.txt,items);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this,"POSITION"+position,Toast.LENGTH_LONG);
                }
            });
        }


        public void onStart(){
            super.onStart();
            // Create request queue
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            //  Create json array request
            JsonArrayRequest jsonArrayRequest=new JsonArrayRequest("https://api.myjson.com/bins/4i74s",new Response.Listener<JSONArray>(){

                public void onResponse(JSONArray jsonArray){
                    // Successfully download json
                    // So parse it and populate the listview
                    for(int i=0;i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            items.add(jsonObject.getString("shop_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("Error", "Unable to parse json array");
                }
            });
            // add json array request to the request queue
            requestQueue.add(jsonArrayRequest);
        }


    }

