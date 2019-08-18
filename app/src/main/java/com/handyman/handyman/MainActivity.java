package com.handyman.handyman;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import jp.wasabeef.blurry.Blurry;


public class MainActivity extends AppCompatActivity {
ProgressDialog pd;
TextView hman;
Button btn1,btn2;
RadioGroup rbtn_grp;
//RadioButton rbtn_cust,rbtn_hman;
LinearLayout btn_hman,btn_cust;
ConnectionDetector cd;
    AlertDialog.Builder ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbtn_grp=findViewById(R.id.rbtn_grp);
        btn_cust=findViewById(R.id.btn_cust);
        btn_hman=findViewById(R.id.btn_hman);
        hman=findViewById(R.id.hman);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        Typeface customPost = Typeface.createFromAsset(getAssets(),"fonts/AmityJack.ttf");
        hman.setTypeface(customPost);
        //rbtn_cust = findViewById(R.id.rbtn_cust);
        //rbtn_hman = findViewById(R.id.rbtn_hman);

        //cust = (Button) findViewById(R.id.cust);
        //hman = (Button) findViewById(R.id.hman);

        cd = new ConnectionDetector(this);
        ad = new AlertDialog.Builder(MainActivity.this);
        ad.setTitle("Alert");
        ad.setMessage("Check your Internet connection!!");
        if (cd.isConnected() == false) {
            //Toast.makeText(this, "Check your Internet connection!!", Toast.LENGTH_LONG).show();
            ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                }
            });
            ad.show();



        } else {

            runOnUiThread(new Runnable() {

                public void run() {
/*
                    //Toast.makeText(getApplicationContext(), "Example for Toast", Toast.LENGTH_SHORT).show();
                    pd = new ProgressDialog(MainActivity.this);
                    cust.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pd.setMessage("Please Wait...");
                            pd.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent = new Intent(MainActivity.this, CustomerProfile.class);
                                    startActivity(intent);
                                    pd.dismiss();
                                }
                            }, 2000);
                        }
                    });


                    hman.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pd.setMessage("Please Wait...");
                            pd.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent = new Intent(MainActivity.this, HmanProfile.class);
                                    startActivity(intent);
                                    pd.dismiss();
                                }
                            }, 2000);
                        }
                    });

*/

                }
            });

        }

        rbtn_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbtn_cust:
                         btn_hman.setVisibility(View.GONE);
                         btn_cust.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "CUST", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.rbtn_hman:
                        btn_cust.setVisibility(View.GONE);
                         btn_hman.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "HMAN", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });





    }

    public void cust_signin(View view)
    {
     Intent intent = new Intent(MainActivity.this,CustomerLogin.class);
     startActivity(intent);
    }

    public void cust_signup(View view)
    {
        Intent intent = new Intent(MainActivity.this,CustomerRegister.class);
        startActivity(intent);
    }

    public void hman_signin(View view)
    {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void hman_signup(View view)
    {
        Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

 /* public class ConnectionDetector{
        Context context;

        public ConnectionDetector(Context context){
            this.context = context;
        }

        public boolean isConnected(){
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Service.CONNECTIVITY_SERVICE);
            if(connectivityManager != null){
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null){
                    if(info.getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
            return false;
        }


    }
    */


}
