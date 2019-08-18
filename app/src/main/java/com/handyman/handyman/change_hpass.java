package com.handyman.handyman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class change_hpass extends AppCompatActivity {
    SharedPreferences sp;
    String pass, opass, npass,mob;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    EditText hlogin_opass, hlogin_npass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_hpass);
        hlogin_opass = (EditText) findViewById(R.id.hlogin_opass);
        hlogin_npass = (EditText) findViewById(R.id.hlogin_npass);
        //sp = getSharedPreferences("hman_det", MODE_PRIVATE);
       // pass = sp.getString("password", "");
       // mob=sp.getString("mobile","");
        Intent i=getIntent();
        mob=i.getStringExtra("mob");
        pass=i.getStringExtra("pswd");
        ch = new ConnectionHelper();
        con = ch.getConnection();
    }

    public void cpswd_hman(View view) {
        //Toast.makeText(this, ""+pass, Toast.LENGTH_SHORT).show();
        if (hlogin_npass.getText().toString().isEmpty()) {
            hlogin_npass.setError("Empty");
            hlogin_npass.requestFocus();
        } else if (hlogin_opass.getText().toString().isEmpty()) {
            hlogin_opass.setError("Empty");
            hlogin_opass.requestFocus();
        } else {
            npass = hlogin_npass.getText().toString().trim();
            opass = hlogin_opass.getText().toString().trim();
           // Toast.makeText(this, ""+opass, Toast.LENGTH_SHORT).show();

            if (hlogin_opass.getText().toString().equals(pass)) {
                try {
                    stmt = con.createStatement();
                    String QUERY;
                    QUERY = "update hmn_hreg set password='" + npass + "' where mobile='" + mob + "'";

                    stmt.execute(QUERY);
                    Toast.makeText(this, "Password Updated!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(change_hpass.this, HmanProfile.class);
                    startActivity(intent);


                } catch (SQLException e) {
                    // e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            } else {
                      hlogin_npass.setText("");
                      hlogin_opass.setText("");
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, ""+pass, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
