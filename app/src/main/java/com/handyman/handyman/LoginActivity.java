package com.handyman.handyman;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {
String hnum;
String hpass;
SharedPreferences sp;
EditText hlogin_num,hlogin_pass;
ResultSet rs;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hlogin_num=(EditText)findViewById(R.id.hlogin_num);
        hlogin_pass=(EditText)findViewById(R.id.hlogin_pass);
        ch = new ConnectionHelper();
        con = ch.getConnection();
    }

    public void hmanr(View view)
    {


        runOnUiThread(new Runnable() {

            public void run() {

                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

    }
    public void hlogin(View view)
    {

           // stmt=con.createStatement();
           // String QUERY="SELECT mobile,password from ak_handyman where mobile='"+hnum+"' and password='"+hpass+"'";
            //stmt.execute(QUERY);
        //Toast.makeText(this, ""+hnum, Toast.LENGTH_SHORT).show();
            if(hlogin_num.getText().toString().isEmpty())
            {
                hlogin_num.setError("Empty");
                hlogin_num.requestFocus();

            }
            else if(hlogin_pass.getText() .toString().isEmpty())
            {
                hlogin_pass.setError("Empty");
                hlogin_pass.requestFocus();
            }

            else {
                hnum = hlogin_num.getText().toString().trim();
                hpass = hlogin_pass.getText().toString().trim();

                new Login(this).execute();

            }
    }

    public class Login extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog pd;

        public Login(LoginActivity loginActivity) {
            pd=new ProgressDialog(loginActivity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Please Wait...");
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (con == null) {
               // Toast.makeText(LoginActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    stmt = con.createStatement();
                    String QUERY = "SELECT * from hmn_hreg where mobile='" + hnum + "' and password='" + hpass + "'";
                    rs = stmt.executeQuery(QUERY);
                    if (rs.next()) {
                        //
                        sp = getSharedPreferences("hman_det", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putString("fname", rs.getString("fname"));
                        ed.putString("lname", rs.getString("lname"));
                        ed.putString("mobile", rs.getString("mobile"));
                        ed.putString("works", rs.getString("works"));
                        ed.putString("address", rs.getString("address"));
                        ed.putString("city", rs.getString("city"));
                        ed.putString("id", rs.getString("id"));
                        ed.putString("pincode", rs.getString("pincode"));
                        ed.putString("email", rs.getString("email"));
                        //ed.putString("id", rs.getString("id"));
                        ed.putString("password",rs.getString("password"));
                        // sp.getString("id", "");
                        ed.commit();
                        pd.dismiss();
                        //Toast.makeText(this, ""+rs.getString("fname"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HmanProfile.class);
                        //Toast.makeText(this, ""+rs.getString("password"), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        ActivityCompat.finishAffinity(LoginActivity.this);
                    } else {
                        pd.dismiss();

                        runOnUiThread(new Runnable() {

                            public void run() {
                                Toast.makeText(LoginActivity.this, "Invalid Username or password", Toast.LENGTH_SHORT).show();
                                hlogin_num.setText("");
                                hlogin_pass.setText("");

                            }
                        });
                    }
                    Intent intent = new Intent();
                    //sp = getSharedPreferences("", MODE_PRIVATE);
                    //String id = sp.getString("id", "");
                    //String id=rs.getString("id");
                    //intent.putExtra("hid", id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }



            return null;
        }
    }

    public void Forgot_hpass(View view)
    {
        //Intent i=new Intent(LoginActivity.this,Forgot_hpass.class);
        Intent i=new Intent(LoginActivity.this,Forgot_hpass.class);
        startActivity(i);
    }
}



