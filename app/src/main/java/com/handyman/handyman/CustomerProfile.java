package com.handyman.handyman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
SharedPreferences sp;
String cname,cmobile,ccity,cid,cpass;
TextView tv_headername,tv_headermobile,tv_initials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        sp = getSharedPreferences("clog", MODE_PRIVATE);
        cname = sp.getString("cname", "");
        cmobile = sp.getString("cmobile", "");
        ccity = sp.getString("ccity", "");
        cid = sp.getString("cid", "");
        cpass=sp.getString("cpass","");
        if (cmobile.isEmpty()) {
            Intent i = new Intent(CustomerProfile.this, CustomerLogin.class);
            startActivity(i);
            ActivityCompat.finishAffinity(this);

        } else {


            //Toast.makeText(this, ""+cname, Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.putExtra("cust_name", cname);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View hview = navigationView.getHeaderView(0);
            tv_headername = (TextView) hview.findViewById(R.id.tv_headername);
            tv_headermobile = (TextView) hview.findViewById(R.id.tv_headermobile);
            tv_headername.setText(cname);
            tv_headermobile.setText(cmobile);
            tv_initials = (TextView) hview.findViewById(R.id.tv_initials);
            ShortName sname = new ShortName();
            String initials = sname.getShortName(cname);
            tv_initials.setText(initials);
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
        getMenuInflater().inflate(R.menu.customer_profile, menu);
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
            Intent i=new Intent(CustomerProfile.this,MyBookings_cust.class);
            startActivity(i); // Handle the camera action
        } else if (id == R.id.nav_chpaswd) {
              // Intent i=new Intent(CustomerProfile.this,MyBookings_cust.class);
              // startActivity(i);
            Intent i=new Intent(CustomerProfile.this,change_cpass.class);
            //Toast.makeText(this, ""+cpass, Toast.LENGTH_SHORT).show();
            i.putExtra("pswd",cpass);
            i.putExtra("mob",cmobile);
            startActivity(i);

        } else if (id == R.id.nav_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, download this app!");
            startActivity(shareIntent);     //Intent i=new Intent(CustomerProfile.this,change_cpass.class);
           // startActivity(i);

        } else if (id == R.id.nav_send) {


                SharedPreferences.Editor ed = sp.edit();
                ed.clear();
                ed.commit();
                Intent intent = new Intent(CustomerProfile.this, CustomerLogin.class);
                startActivity(intent);
                finish();
                //ActivityCompat.finishAffinity(CustomerProfile.this);
                //ActivityCompat.finishAfterTransition(this);

        } else if (id == R.id.nav_gallery)
        {
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
            Intent i=new Intent(CustomerProfile.this,DevActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void plumber(View view)
    {

        Intent intent=new Intent(CustomerProfile.this,ServiceType.class);
        String service="Plumber";
        intent.putExtra("service",service);
        intent.putExtra("city",ccity);

        startActivity(intent);
    }

    public void electrician(View view)
    {
        Intent intent=new Intent(CustomerProfile.this,ServiceType.class);
        String service="Electrician";
        intent.putExtra("service",service);
        intent.putExtra("city",ccity);
        startActivity(intent);
    }

    public void carpenter(View view)
    {
        Intent intent=new Intent(CustomerProfile.this,ServiceType.class);
        String service="Carpenter";
        intent.putExtra("service",service);
        intent.putExtra("city",ccity);
        startActivity(intent);
    }

    public void gardener(View view)
    {
        Intent intent=new Intent(CustomerProfile.this,ServiceType.class);
        String service="Gardener";
        intent.putExtra("service",service);
        intent.putExtra("city",ccity);
        startActivity(intent);
    }



    public void painter(View view)
    {
        Intent intent=new Intent(CustomerProfile.this,ServiceType.class);
        String service="Painter";
        intent.putExtra("service",service);
        intent.putExtra("city",ccity);
        startActivity(intent);
    }

    public void iron_works(View view)
    {
        Intent intent=new Intent(CustomerProfile.this,ServiceType.class);
        String service="Iron Works";
        intent.putExtra("service",service);
        intent.putExtra("city",ccity);
        startActivity(intent);
    }
    public void edit_cust_profile(View view)
    {
        Intent i=new Intent(CustomerProfile.this,EditCustProfile.class);
        startActivity(i);
    }
}
