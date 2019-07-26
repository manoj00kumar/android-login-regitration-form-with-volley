package com.example.myapplication;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    AlertDialog.Builder builder;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        builder = new AlertDialog.Builder(this);
        Button lbtn=findViewById(R.id.button);
        lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();
            }
        });
          Button regbtn1=findViewById(R.id.buttonreg);
          regbtn1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent rin1=new Intent(Login.this,Registration.class);
                  startActivity(rin1);
              }
          });


          //forgot password
Button btn_forgot=findViewById(R.id.buttonforgot);
  btn_forgot.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://localhost/demo/forgot-password.php"));
          startActivity(browserIntent);
      }
  });

    }

    public void loginuser(){
        String Logurl="http://10.0.2.2:80/api/index.php";
        EditText utxt=findViewById(R.id.editText);
      final   String uname=utxt.getText().toString();
        EditText upwd=findViewById(R.id.editText2);
     final    String pwd=upwd.getText().toString();

          if(TextUtils.isEmpty(uname)){
              utxt.setError("Username required");
              utxt.requestFocus();
              builder.setMessage("enter valid username");
              builder.setCancelable(true);
              builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      dialog.cancel();
                  }
              });
              AlertDialog alert= builder.create();
              alert.setTitle("error");
              alert.show();
              return;
          }
          if(TextUtils.isEmpty(pwd)){
              upwd.setError("password");
              builder.setMessage("enter valid password");
              builder.setCancelable(true);
              builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      dialog.cancel();
                  }
              });
              AlertDialog alert= builder.create();
              alert.setTitle("error");
              alert.show();
              upwd.requestFocus();
              return;
          }
StringRequest logreq=new StringRequest(Request.Method.POST, Logurl, new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
        try {
            JSONObject jsonobj = new JSONObject(response);
            Log.d("x", response);

            builder.setMessage(response);
              JSONObject job=new JSONObject(response);
            int status=  job.getInt("status");
            if(status==400){

                JSONObject dob=job.getJSONObject("data");
                int uid=dob.getInt("id");
                String username=dob.getString("username");
                String usr_name=dob.getString("name");
                User user=new User(uid, username, usr_name);
                SharedPref.getInstance(getApplicationContext()).userLogin(user);
                Log.d("JSON", dob.toString());
                Intent pin= new Intent(Login.this, Profile.class);
                startActivity(pin);
            }
            else{

                builder.setMessage("Either username or password is wrong");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert= builder.create();
                alert.setTitle("Login failed");
                alert.show();

            }
            builder.setCancelable(false);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert= builder.create();
            alert.setTitle("Respose");
            alert.show();

            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            VolleyLog.d("d", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
        VolleyLog.d("er",error.getMessage());
        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
    }
}){
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("user", uname);

        params.put("pwd", pwd);

        return params;
    }
};

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        //requestQueue.add(req);
        requestQueue.add(logreq);

    }

}






