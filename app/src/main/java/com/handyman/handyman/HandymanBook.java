package com.handyman.handyman;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class HandymanBook extends AppCompatActivity {
SharedPreferences sp;
String cid,cname,cmobile,ccity,caddress,id,name,desc,status,date,hmob;
EditText et_desc;
TextView tv_hman_name,tv_hman_prof,tv_hman_no;
SimpleDateFormat sdf;
java.util.Date dt;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    AlertDialog.Builder ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handyman_book);
        et_desc=(EditText)findViewById(R.id.et_desc);
        tv_hman_name=(TextView)findViewById(R.id.tv_hman_name);
        tv_hman_prof=(TextView)findViewById(R.id.tv_hman_prof);
        tv_hman_no=(TextView)findViewById(R.id.tv_hman_no);

        sp=getSharedPreferences("clog",MODE_PRIVATE);
        cname=sp.getString("cname","");
        cmobile=sp.getString("cmobile","");
        cid=sp.getString("cid","");
        ccity=sp.getString("ccity","");
        caddress=sp.getString("caddress","");
        Intent intent1=getIntent();
        id=intent1.getStringExtra("hid");
        name=intent1.getStringExtra("hname");
        hmob=intent1.getStringExtra("hnum");
        //desc=et_desc.getText().toString().trim();
       // Toast.makeText(this, ""+cid+cname+cmobile+name+hmob, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, ""+cname+cmobile, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+cname, Toast.LENGTH_SHORT).show();
        ch=new ConnectionHelper();
        con=ch.getConnection();
        tv_hman_name.setText(name);
        tv_hman_no.setText(hmob);
        sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dt=new java.util.Date();
        date=sdf.format(dt).toString();

        //Toast.makeText(this,""+date,Toast.LENGTH_SHORT).show();

    }

    public void addNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.spalshscreen).setContentTitle("FOR CUSTOMER").setColor(101).setContentText("Handyman Booked Successfully!!");
        //Intent intent=new Intent(this,Udaan.class);
        //PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //builder.setContentIntent(contentIntent);

        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    public void book(View view) {
        // Toast.makeText(this, ""+desc, Toast.LENGTH_SHORT).show();
        if (et_desc.getText().toString().isEmpty()) {
            et_desc.setError("");
            et_desc.requestFocus();
        } else {
            desc=et_desc.getText().toString();
            Toast.makeText(this, ""+desc, Toast.LENGTH_SHORT).show();
            try {
                status = "pending";
                stmt = con.createStatement();
                String QUERY = "Insert into hmn_book values('" + id + "','" + cid + "','" + cname + "','" + cmobile + "','" + name + "','" + caddress + "','" + desc + "','" + status + "','"+date+"')";
                stmt.execute(QUERY);
                Toast.makeText(this, "Booked Successfully!", Toast.LENGTH_SHORT).show();
                addNotification();

                et_desc.setText("");
                ad = new AlertDialog.Builder(HandymanBook.this);
                ad.setTitle("Call");
                ad.setMessage("Do yo want to call " + hmob);

                ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + hmob));
                        startActivity(intent);
                    }
                });
                ad.show();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
