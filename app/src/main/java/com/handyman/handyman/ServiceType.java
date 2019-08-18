package com.handyman.handyman;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ServiceType extends AppCompatActivity {
TextView tv,emptyview;
String service,city;
String SERVICE;
ListView handymanList;
    AlertDialog.Builder ad;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet resultSet;
    String []from={"hid","name","works","mobile"};
    ArrayList<HashMap<String,String>> al_hman=new ArrayList<HashMap<String, String>>();
    int []to={R.id.tv_hid,R.id.hman_name,R.id.hman_work,R.id.hman_mobile};
    SimpleAdapter SA;
    //ArrayAdapter AD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_type);
        emptyview = (TextView) findViewById(R.id.empty_view);
        handymanList = (ListView) findViewById(R.id.handymanList);
        ad = new AlertDialog.Builder(ServiceType.this);
        ad.setTitle("MESSAGE");
        ad.setMessage("You haven't made any bookings yet!!");
        //handymanList.setEmptyView(findViewById(R.id.empty_view));

        Intent intent = getIntent();
        service = intent.getStringExtra("service");
        city = intent.getStringExtra("city");
        ch = new ConnectionHelper();
        con = ch.getConnection();
        //handymanList.setEmptyView(findViewById(R.id.empty_view));
        SA = new SimpleAdapter(this, al_hman, R.layout.customlist, from, to);
        handymanList.setAdapter(SA);


        // handymanList.setEmptyView(findViewById(R.id.empty_view));
        // Toast.makeText(this, ""+service, Toast.LENGTH_SHORT).show();


        try {
            stmt = con.createStatement();
            SERVICE = "SELECT * FROM hmn_hreg where works='" + service + "'";
            resultSet = stmt.executeQuery(SERVICE);
            while (resultSet.next()) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("hid", resultSet.getString("id"));
                hm.put("name", resultSet.getString("fname") + " " + resultSet.getString("lname"));
                hm.put("works", resultSet.getString("works"));
                hm.put("mobile", resultSet.getString("mobile"));
                al_hman.add(hm);
            }
            //  AD=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,al_hman);
            //  handymanList.setAdapter(AD);

        } catch (SQLException e) {
            //  e.printStackTrace();
            Toast.makeText(ServiceType.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if (SA.getCount() == 0) {
            //empty_view.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            emptyview.setVisibility(View.VISIBLE);
            ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   Intent i=new Intent(ServiceType.this,CustomerProfile.class);
                   startActivity(i);
                   finish();
                }
            });
            ad.show();

        } else {


            handymanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String hid = ((TextView) view.findViewById(R.id.tv_hid)).getText().toString();
                    String hname = ((TextView) view.findViewById(R.id.hman_name)).getText().toString();
                    String hmob = ((TextView) view.findViewById(R.id.hman_mobile)).getText().toString();
                    Toast.makeText(ServiceType.this, "" + hid + hname, Toast.LENGTH_SHORT).show();

                    Intent intent1 = new Intent(ServiceType.this, HandymanBook.class);
                    intent1.putExtra("hid", hid);
                    intent1.putExtra("hname", hname);
                    intent1.putExtra("hnum", hmob);
                    startActivity(intent1);
                }
            });


        }
    }





}
