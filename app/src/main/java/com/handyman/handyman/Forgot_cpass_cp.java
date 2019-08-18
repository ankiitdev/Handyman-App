package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Forgot_cpass_cp extends AppCompatActivity {
    EditText et_p;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_cpass_cp);
        et_p=(EditText)findViewById(R.id.et_p);
        ch = new ConnectionHelper();
        con = ch.getConnection();
    }

    public void proceed(View view)
    {
        Intent i=getIntent();
        String mob=i.getStringExtra("MOB");


        if(et_p.getText().toString().trim().isEmpty())
        {
            et_p.setError("Empty");
            et_p.requestFocus();
        }
        else
        {   String pass=et_p.getText().toString().trim();
            //Toast.makeText(this, ""+mob+" "+pass, Toast.LENGTH_SHORT).show();
            try {
                stmt = con.createStatement();
                String QUERY;
                QUERY = "update hmn_creg set cpswd='" + pass + "' where cmobile='" + mob + "'";

                stmt.execute(QUERY);
                Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Forgot_cpass_cp.this, CustomerLogin.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, "Please login to continue", Toast.LENGTH_SHORT).show();


            } catch (SQLException e) {
                // e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}
