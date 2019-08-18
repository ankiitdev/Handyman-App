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

public class change_cpass extends AppCompatActivity {
    SharedPreferences sp;
    String pass, old_pass, new_pass,pswd,mob;
    EditText clogin_opass, clogin_npass;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_cpass);
        sp = getSharedPreferences("", MODE_PRIVATE);
        pass = sp.getString("cpass", "");

        clogin_opass = (EditText) findViewById(R.id.clogin_opass);
        clogin_npass = (EditText) findViewById(R.id.clogin_npass);
        Intent i=getIntent();
        pswd=i.getStringExtra("pswd");
        mob=i.getStringExtra("mob");
        //Toast.makeText(this, ""+pswd+""+pass, Toast.LENGTH_SHORT).show();
        ch = new ConnectionHelper();
        con = ch.getConnection();
    }

    public void cpswd_cstmr(View view) {
        if (clogin_npass.getText().toString().isEmpty()) {
            clogin_npass.setError("Empty");
            clogin_npass.requestFocus();
        } else if (clogin_opass.getText().toString().isEmpty()) {
            clogin_opass.setError("Empty");
            clogin_opass.requestFocus();
        } else {
            new_pass = clogin_npass.getText().toString().trim();
            old_pass = clogin_opass.getText().toString().trim();

            if (old_pass.equals(pswd)) {

                try {
                    stmt = con.createStatement();
                    String QUERY;
                    QUERY = "update hmn_creg set cpswd='" + new_pass + "' where cmobile='" + mob + "'";

                    stmt.execute(QUERY);
                    Toast.makeText(this, "Password Updated!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(change_cpass.this, CustomerProfile.class);
                    startActivity(intent);


                } catch (SQLException e) {
                    //e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            } else {
                   clogin_opass.setText("");
                   clogin_npass.setText("");
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, ""+pass, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
