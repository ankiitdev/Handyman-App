package com.handyman.handyman;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.content.DialogInterface;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class MyBookings_cust extends AppCompatActivity {
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    SharedPreferences sp;
    AlertDialog.Builder ad;
    ListView lv_bookings;
    SimpleAdapter SA;
    ResultSet rs;
    String QUERY;
    String []from={"hname","hwork","hmobile","hstatus","cur_date"};
    ArrayList<HashMap<String,String>> al_book=new ArrayList<HashMap<String, String>>();
    int []to={R.id.hman_name,R.id.hman_work,R.id.hman_mobile,R.id.hman_status,R.id.book_time};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings_cust);
        lv_bookings=(ListView)findViewById(R.id.lv_bookings);
        ch=new ConnectionHelper();
        con=ch.getConnection();
        SA=new SimpleAdapter(this,al_book,R.layout.customlist_mybookings,from,to);
        // Toast.makeText(this, ""+service, Toast.LENGTH_SHORT).show();
        sp=getSharedPreferences("clog",MODE_PRIVATE);
        //Toast.makeText(this, ""+sp.getString("cid",""), Toast.LENGTH_SHORT).show();
        lv_bookings.setAdapter(SA);
       // lv_bookings.setEmptyView(findViewById(R.id.empty_view));
      /*  if(SA.isEmpty()) {


                ad=new AlertDialog.Builder(MyBookings_cust.this);
                ad.setTitle("ALERT");
                ad.setMessage("You haven't made any bookings yet!");

                ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(MyBookings_cust.this,CustomerProfile.class);
                        startActivity(intent);
                        finish();
                    }
                });
                ad.show();

        }

       else
        {
           // ad = new android.support.v7.app.AlertDialog.Builder(MyBookings_cust.this);
            lv_bookings.setAdapter(SA);
             } */

        try {
            Intent i=getIntent();
            String name=i.getStringExtra("cust_name");
            stmt=con.createStatement();
            QUERY="SELECT * FROM hmn_book where cid='"+sp.getString("cid","")+"'";
            rs=stmt.executeQuery(QUERY);
            while (rs.next())
            {
                HashMap<String,String> hm=new HashMap<String, String>();
                //hm.put("hid",resultSet.getString("hid"));
                hm.put("hname",rs.getString("hname"));
                hm.put("hwork",rs.getString("cno"));
                hm.put("hmobile",rs.getString("current_location"));
                hm.put("hstatus",rs.getString("status"));
                hm.put("cur_date",rs.getString("cur_date"));
                al_book.add(hm);
            }
           SA.notifyDataSetChanged();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
