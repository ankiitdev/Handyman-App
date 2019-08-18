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

public class CustomerRegister extends AppCompatActivity {
    EditText[] ets = new EditText[5];
    int[] ids = {R.id.et_cname, R.id.et_mobile, R.id.et_password, R.id.et_address, R.id.et_city};
    String[] values = new String[ets.length];
    int i;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
        for (i = 0; i < ets.length; i++) {
            ets[i] = (EditText) findViewById(ids[i]);
        }
        ch=new ConnectionHelper();
        con=ch.getConnection();


    }

    public void creg(View view) {
        for (i = 0; i < ets.length; i++) {
            if (ets[i].getText().toString().isEmpty()) {
                ets[i].setError("Empty");
                ets[i].requestFocus();
                break;
            }
        }
        if (i == ets.length) {
            for (i = 0; i < ets.length; i++) {
                values[i] = ets[i].getText().toString().trim();
            }
        }

        try {
            stmt=con.createStatement();
            String QUERY;
            QUERY="INSERT INTO hmn_creg values('"+values[0]+"','"+values[1]+"','"+values[2]+"','"+values[3]+"','"+values[4]+"')";

            stmt.execute(QUERY);
            //Toast.makeText(this, "User registered!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(CustomerRegister.this,CustomerProfile.class);
            startActivity(intent);


        } catch (SQLException e) {
            // e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void login(View view)
    {
        Intent intent=new Intent(CustomerRegister.this,CustomerLogin.class);
        startActivity(intent);
    }
}
