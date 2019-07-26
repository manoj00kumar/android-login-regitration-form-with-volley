package com.example.myapplication;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //private static final String JSON_URL = "https://simplifiedcoding.net/demos/view-flipper/heroes.php";

    private static final String JSON_URL = "http://10.0.2.2:80/api/index.php";
    //listview object
    ListView listView;

    //the hero list where we will store all the hero objects after parsing json
    List<Hero> heroList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent logint=new Intent(MainActivity.this,Login.class);
        startActivity(logint);
        listView= findViewById(R.id.listv);
        heroList=new ArrayList<Hero>();
        //loadHer0list();

    }

    public void loadHer0list(){
        final ProgressBar prbar =findViewById(R.id.listprog);
        prbar.setVisibility(View.VISIBLE);
        StringRequest req=new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplicationContext(), JSON_URL, Toast.LENGTH_SHORT).show();
              prbar.setVisibility(View.INVISIBLE);
              try{
                  JSONObject obj=new JSONObject(response);
                  JSONArray heroar =obj.getJSONArray("heroes");
                  //Toast.makeText(getApplicationContext(), heroar.toString(), Toast.LENGTH_LONG).show();
 for(int i=0; i< heroar.length();i++){
   JSONObject heroobj=heroar.getJSONObject(i);
   Hero  hero= new Hero(heroobj.getString("name"),heroobj.getString("imageurl"));
   heroList.add(hero);
 }

 ListViewAdapter listadp=new ListViewAdapter(heroList, getApplicationContext());
 listView.setAdapter(listadp);

            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
              }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        prbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        //requestQueue.add(req);
        AppController.getInstance().addToRequestQueue(req);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
