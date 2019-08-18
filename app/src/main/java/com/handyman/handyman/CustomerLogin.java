package com.handyman.handyman;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import com.dd.processbutton.iml.ActionProcessButton;
import com.dd.*;
import com.dd.processbutton.iml.GenerateProcessButton;

public class CustomerLogin extends AppCompatActivity {
String cnum,cpass;
EditText clogin_num,clogin_pass;
SharedPreferences sp;
Button btnSignIn;
//ActionProcessButton.ProgressBar progressBar;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;
    AlertDialog.Builder ad;
    ArrayList<String> al_data=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        clogin_num=(EditText)findViewById(R.id.clogin_num);
        clogin_pass=(EditText)findViewById(R.id.clogin_pass);
        //btnSignIn=(ActionProcessButton) findViewById(R.id.btnSignIn);

        ch=new ConnectionHelper();
        con=ch.getConnection();
        //ProgressGenerator progressGenerator = new ProgressGenerator(this);



    }

    public void cstmr(View view)
    {
        //Intent intent=new Intent(CustomerLogin.this,CMobileActivity.class);
        Intent intent=new Intent(CustomerLogin.this,CMobileActivity.class);
        startActivity(intent);
    }

    public void clogin(View view)
    {
        if(clogin_num.getText().toString().isEmpty())
        {
            clogin_num.setError("Error");
            clogin_num.requestFocus();
        }
        else if(clogin_pass.getText().toString().isEmpty())
        {
            clogin_pass.setError("Error");
            clogin_pass.requestFocus();
        }
        else
        {
            cnum=clogin_num.getText().toString().trim();
            cpass=clogin_pass.getText().toString().trim();

            new MyLogin(this).execute();
            //Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();


        }
    }

    public class MyLogin extends AsyncTask<Void, Void,Void>
    {
        ProgressDialog pd;

        public MyLogin(CustomerLogin customerLogin) {
           pd=new ProgressDialog(customerLogin);
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
                //Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
                //Toast.makeText(CustomerLogin.this, "No connection!", Toast.LENGTH_SHORT).show();
            } else {

                try {
                    stmt=con.createStatement();
                    String LOGIN="select * from hmn_creg where cmobile='"+cnum+"' and cpswd='"+cpass+"'";
                    rs=stmt.executeQuery(LOGIN);
                    if(rs.next())
                    {
                        //pd.dismiss();
                        sp=getSharedPreferences("clog",MODE_PRIVATE);
                        SharedPreferences.Editor ed=sp.edit();
                        ed.putString("cname",rs.getString("cname"));
                        ed.putString("cid",rs.getString("cid"));
                        ed.putString("cmobile",rs.getString("cmobile"));
                        ed.putString("caddress",rs.getString("caddress"));
                        ed.putString("ccity",rs.getString("ccity"));
                        ed.putString("cpass",rs.getString("cpswd"));
                        ed.commit();

                        Intent intent=new Intent(CustomerLogin.this,CustomerProfile.class);
                        startActivity(intent);
                        ActivityCompat.finishAffinity(CustomerLogin.this);


                    }
                    else
                    {
                        pd.dismiss();


                        runOnUiThread(new Runnable() {

                            public void run() {

                                //Toast.makeText(getApplicationContext(), "Example for Toast", Toast.LENGTH_SHORT).show();
                                Toast.makeText(CustomerLogin.this, "Invalid Username or password", Toast.LENGTH_SHORT).show();
                                clogin_num.setText("");
                                clogin_pass.setText("");
                            }
                        });

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }}


            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(CustomerLogin.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void change_cpassword(View view)
    {
        Intent intent=new Intent(CustomerLogin.this,Forgot_cpass.class);
        startActivity(intent);
    }
}

