package com.handyman.handyman;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
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

public class HmanProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
SharedPreferences sp;
TextView tv_headername,tv_mobile,tv_initials,tv_hidd,c_name,c_mob,c_loc,empty_view;
String hname,hmobile,SERVICE,hid,status;
ListView lv;
AlertDialog.Builder ad;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet resultSet;
    String cname,cmobile,add,passwrd;
    String []from={"cname","cno","current_location","orderid","description","time"};
    ArrayList<HashMap<String,String>> al_book=new ArrayList<HashMap<String, String>>();
    int []to={R.id.c_name,R.id.c_mob,R.id.c_loc,R.id.tv_hidd,R.id.tv_desc,R.id.tv_time};
    SimpleAdapter SA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hman_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sp = getSharedPreferences("hman_det", MODE_PRIVATE);
        empty_view=(TextView)findViewById(R.id.empty_view);
        hname = sp.getString("fname", "") + " " + sp.getString("lname", "");
        hmobile = sp.getString("mobile", "");
        hid = sp.getString("id", "");
        passwrd = sp.getString("password", "");
       // Toast.makeText(this, "" + hname + hmobile, Toast.LENGTH_SHORT).show();

        if (hmobile.isEmpty()) {
            Intent i = new Intent(HmanProfile.this, LoginActivity.class);
            startActivity(i);
            finish();
        } else {

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View hview = navigationView.getHeaderView(0);
            tv_headername = (TextView) hview.findViewById(R.id.tv_hdrname);
            tv_mobile = (TextView) hview.findViewById(R.id.tv_hdrmobile);
            tv_headername.setText(hname);
            tv_mobile.setText(hmobile);
            tv_initials = (TextView) hview.findViewById(R.id.tv_hinitials);
            ShortName sname = new ShortName();
            String initials = sname.getShortName(hname);
            tv_initials.setText(initials);
            lv = (ListView) findViewById(R.id.hmanpro_list);
            ch = new ConnectionHelper();
            con = ch.getConnection();
            SA = new SimpleAdapter(this, al_book, R.layout.custom_hlist, from, to);


            lv.setAdapter(SA);


            try {
                stmt = con.createStatement();
                SERVICE = "SELECT * FROM hmn_book where hid='" + hid + "'";
                resultSet = stmt.executeQuery(SERVICE);


                while (resultSet.next()) {
                    HashMap<String, String> hm = new HashMap<String, String>();

                    hm.put("cname", resultSet.getString("cname"));
                    hm.put("cno", resultSet.getString("cno"));
                    hm.put("current_location", resultSet.getString("current_location"));
                    hm.put("description", resultSet.getString("description"));
                    hm.put("orderid", resultSet.getString("orderid"));
                    hm.put("time", resultSet.getString("cur_date"));

                    al_book.add(hm);

                }


            } catch (SQLException e) {
                //  e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if (SA.getCount() == 0) {
                empty_view.setVisibility(View.VISIBLE);
                Toast.makeText(this, "You are not booked yet!!", Toast.LENGTH_LONG).show();
            } else {
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String cname = ((TextView) view.findViewById(R.id.c_name)).getText().toString();
                        String add = ((TextView) view.findViewById(R.id.c_loc)).getText().toString();
                        String cmobile = ((TextView) view.findViewById(R.id.c_mob)).getText().toString();
                        String oid = ((TextView) view.findViewById(R.id.tv_hidd)).getText().toString();
                        String desc = ((TextView) view.findViewById(R.id.tv_desc)).getText().toString();
                        String time = ((TextView) view.findViewById(R.id.tv_time)).getText().toString();
                        Intent i = new Intent(HmanProfile.this, Orders.class);
                        i.putExtra("cust_name", cname);
                        i.putExtra("cust_mobile", cmobile);
                        i.putExtra("cust_desc", desc);
                        i.putExtra("orderid", oid);
                        i.putExtra("address", add);
                        i.putExtra("time", time);


                        startActivity(i);


                    }
                });


            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hman_profile, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i=new Intent(HmanProfile.this,HmanProfile.class);
            startActivity(i);
            finish();// Handle the camera action
        } else if (id == R.id.nav_gallery) {
            SharedPreferences.Editor ed=sp.edit();
            ed.clear();
            ed.commit();
            Intent intent=new Intent(HmanProfile.this,LoginActivity.class);
            startActivity(intent);
            ActivityCompat.finishAffinity(HmanProfile.this);

        } else if (id == R.id.nav_slideshow) {
            Intent i=new Intent(HmanProfile.this,change_hpass.class);
            i.putExtra("mob",hmobile);
            i.putExtra("pswd",passwrd);
            //Toast.makeText(this, ""+passwrd, Toast.LENGTH_SHORT).show();
            startActivity(i);
        }  else if (id == R.id.nav_share) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, download this app!");
            startActivity(shareIntent);

        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Help & Support!!!!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("plain/test");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"ankiitshrma@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "FEEDBACK/QUERIES");
            intent.putExtra(Intent.EXTRA_TEXT, "Your feedback and queries here...!!");
            startActivity(Intent.createChooser(intent,"Choose an app to send email"));

        }

         else if (id==R.id.nav_dev)
        {
            Intent i=new Intent(HmanProfile.this,DevActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void edit_hman_profile(View view)
    {
        Intent i=new Intent(HmanProfile.this,EditHmanProfile.class);
        startActivity(i);
    }
}
