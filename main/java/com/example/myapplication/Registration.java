package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.io.File;
import java.io.IOException;

public class Registration extends AppCompatActivity {
   private final int PICK_IMAGE_REQUEST = 1;
   private  Bitmap bitmap;
   private Uri filePath;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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
            //login btn click
        Button btnreg34=findViewById(R.id.buttonreg1);
        btnreg34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regtologin=new Intent(Registration.this,Login.class);
                startActivity(regtologin);
            }
        });

        //init fields
        final EditText field_reg=findViewById(R.id.editRegName);
        final EditText field_user=findViewById(R.id.editRegUsr);
        final EditText field_regpwd=findViewById(R.id.editRegPwd);
        final EditText field_phone=findViewById(R.id.editPhone);
        final Button field_regbtn=findViewById(R.id.buttonreg);
        final RadioGroup rdogroup=findViewById(R.id.rdogroup);
        final Button fileinputs=findViewById(R.id.choosepic1);



        fileinputs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });




//button click

        field_regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String reg_name=field_reg.getText().toString();
                    if(TextUtils.isEmpty(reg_name)){
                        field_reg.setError("Name required");
                        field_reg.requestFocus();
                        return;
                    }

               //username  validation

                String reg_user=field_user.getText().toString();
                if(TextUtils.isEmpty(reg_user)){
                    field_user.setError("Username required");
                    field_user.requestFocus();
                    return;
                }

                //password  validation

                String reg_pwd=field_regpwd.getText().toString();
                if(TextUtils.isEmpty(reg_pwd)){
                    field_regpwd.setError("Password required");
                    field_regpwd.requestFocus();
                    return;
                }

                //phone validation

                String reg_phone=field_phone.getText().toString();
                if(TextUtils.isEmpty(reg_phone)){
                    field_phone.setError("Phone required");
                    field_phone.requestFocus();
                    return;
                }
                    int rdo_id=rdogroup.getCheckedRadioButtonId();






            }
        });





    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
          ImageView uploadImg=findViewById(R.id.upload_img);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                uploadImg.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
