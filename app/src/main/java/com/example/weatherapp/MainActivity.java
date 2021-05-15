package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Permission;

import static com.android.volley.Request.*;

public class MainActivity extends AppCompatActivity {
Button b1;
TextView tv,tv2,tv3,tv4;
EditText e1;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        b1=(Button)findViewById(R.id.button);
        tv=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
        tv3=(TextView)findViewById(R.id.textView3);
        tv4=(TextView)findViewById(R.id.textView4);
        e1=(EditText) findViewById(R.id.editTextTextPersonName);
         checkinternet();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myapi="b8effe3bdd69f8d77e06f585d0998014";
                String city=e1.getText().toString();
                String url="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=b8effe3bdd69f8d77e06f585d0998014";
               RequestQueue queue =Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest request=new JsonObjectRequest(Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject object = response.getJSONObject("main");
                            JSONObject object1 = response.getJSONObject("wind");
                            JSONObject object2=response.getJSONObject("clouds");
                            JSONArray array=response.getJSONArray("weather");
                                //JSONArray s=array.getJSONArray(1);
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject obj=array.getJSONObject(i);
                                s=obj.getString("main");
                            }
                            String temp0=object.getString("temp");
                            String tempmax=object.getString("temp_max");
                            String temp1=object1.getString("speed");
                            String temp2=object2.getString("all");
                          Double tempature=(Double.parseDouble(temp0))-273.15;
                          Double tempature1=(Double.parseDouble(tempmax))-273.15;

                            tv.setText("Tempature:- "+tempature.toString().substring(0,4)+"C");
                            tv2.setText("WIND:- "+temp1);
                            tv3.setText("WEATHER: "+s);
                            tv4.setText("MAX TEMPATURE: "+tempature1.toString().substring(0,4)+"C");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);

            }
        });
    }
    public void checkinternet()
    {
        ConnectivityManager manager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        if(null!=networkInfo)
        {
           if(networkInfo.getType()==ConnectivityManager.TYPE_WIFI)
           {
               Toast.makeText(MainActivity.this,"WIFI enabled ",Toast.LENGTH_SHORT).show();
           }
           else if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE)
           {
               Toast.makeText(MainActivity.this,"Mobile data enabled ",Toast.LENGTH_SHORT).show();
           }
           else
           {
               Toast.makeText(MainActivity.this,"NO INETERNET CONNCATION ",Toast.LENGTH_LONG).show();
           }
        }
     }
}