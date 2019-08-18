package com.handyman.handyman;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.katepratik.msg91api.MSG91;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Orders extends AppCompatActivity {
String nme,mobile,desc,hid,address,time;
TextView tv_oid,tv_custname,tv_custmobile,tv_description,tv_address,book_t;
String QUERY;
Button acpt,rjct;
ProgressDialog progressDialog;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    MSG91 msg91;
    CaptchaGenerator cg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Intent i=getIntent();
        progressDialog=new ProgressDialog(this);
        nme=i.getStringExtra("cust_name");
        mobile=i.getStringExtra("cust_mobile");
        desc=i.getStringExtra("cust_desc");
        hid=i.getStringExtra("orderid");
        address=i.getStringExtra("address");
        time=i.getStringExtra("time");
        //Toast.makeText(this, ""+nme+" "+mobile+" "+desc+" "+hid+" "+address, Toast.LENGTH_SHORT).show();
        tv_address=(TextView)findViewById(R.id.tv_address);
        tv_oid=(TextView)findViewById(R.id.tv_oid);
        tv_custname=(TextView)findViewById(R.id.tv_custname);
        tv_custmobile=(TextView)findViewById(R.id.tv_custmobile);
        tv_description=(TextView)findViewById(R.id.tv_description);
        book_t=(TextView)findViewById(R.id.book_t);
        cg=new CaptchaGenerator();
        tv_oid.setText(hid);
        tv_custname.setText(nme);
        tv_custmobile.setText(mobile);
        tv_description.setText(desc);
        tv_address.setText(address);
        book_t.setText(time);
        ch=new ConnectionHelper();
        con=ch.getConnection();
        acpt=(Button)findViewById(R.id.acpt);
        rjct=(Button)findViewById(R.id.rjct);
    }

    public void addNotificationaccept()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.spalshscreen).setContentTitle("FOR HANDYMAN").setColor(101).setContentText("Booking Accepted");
        //Intent intent=new Intent(this,Udaan.class);
        //PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //builder.setContentIntent(contentIntent);

        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    public void addNotificationreject()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.spalshscreen).setContentTitle("FOR HANDYMAN").setColor(101).setContentText("Booking Rejected");
        //Intent intent=new Intent(this,Udaan.class);
        //PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //builder.setContentIntent(contentIntent);

        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }




    public class backgroundTaskone extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog pd;
        public backgroundTaskone(Orders orders) {
            pd=new ProgressDialog(orders);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (con == null) {
                Toast.makeText(Orders.this, "Check your internet connection!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    stmt = con.createStatement();
                    QUERY = "UPDATE hmn_book set status='confirmed' where orderid='" + hid + "' ";
                    stmt.executeQuery(QUERY);
                    progressDialog.dismiss();
                    //Toast.makeText(Orders.this, "Booking confirmed !!", Toast.LENGTH_SHORT).show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
        }
    }

    public class backgroundTasktwo extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog prd;
        public backgroundTasktwo(Orders ord)
        {
            prd=new ProgressDialog(ord);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prd.setMessage("Please Wait...");
            prd.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (con == null) {
                Toast.makeText(Orders.this, "Check your internet connection!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    stmt = con.createStatement();
                    QUERY = "UPDATE ak_handyman_bookings set status='declined' where orderid='" + hid + "'";
                    stmt.executeQuery(QUERY);
                    progressDialog.dismiss();
                    //Snackbar.make(view,"Order Rejected",Snackbar.LENGTH_LONG).setAction("Dismiss",null).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

                return null;
            }




        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            prd.dismiss();
        }
    }

    public void accept(View view)
    {

        new backgroundTaskone(this).execute();
        msg91=new MSG91("196023AwNplgJ6qS5a722783");
        String otp=cg.getOTP();
        //msg91.composeMessage("HNDMAN","Your OTP for HANDYMAN APP is "+otp);
        msg91.composeMessage("HMANBK","Hello "+nme+"! Your booking has been accepted by the Handyman. Thanks for using our services.");
        msg91.to(mobile);
        msg91.setRoute("4");
        msg91.setCampaign("Transactional");
        msg91.send();
        Toast.makeText(this, "Booking confirmed !!", Toast.LENGTH_SHORT).show();
        addNotificationaccept();
        acpt.setEnabled(false);
        acpt.setBackgroundColor(Color.parseColor("#808080"));
    }

    public void reject(View view)
    {
        new backgroundTasktwo(this).execute();
        msg91=new MSG91("196023AwNplgJ6qS5a722783");
        String otp=cg.getOTP();
        //msg91.composeMessage("HNDMAN","Your OTP for HANDYMAN APP is "+otp);
        msg91.composeMessage("HMANBK","Hello "+nme+"! Your booking has been declined by the Handyman. Thanks for using our services.");
        msg91.to(mobile);
        msg91.setRoute("4");
        msg91.setCampaign("Transactional");
        msg91.send();
        Toast.makeText(this, "Booking cancelled !!", Toast.LENGTH_SHORT).show();
        addNotificationreject();
        rjct.setEnabled(false);
        rjct.setBackgroundColor(Color.parseColor("#808080"));
    }

}
