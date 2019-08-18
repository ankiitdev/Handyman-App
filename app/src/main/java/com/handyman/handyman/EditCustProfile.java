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

public class EditCustProfile extends AppCompatActivity {
    EditText[] ets = new EditText[3];
    int[] ids = {R.id.ep_cmobile, R.id.ep_caddress, R.id.ep_ccity};
    String[] values = new String[ets.length];
    int i;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    SharedPreferences sp;
    String cname,cid;
    EditText cust_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cust_profile);
        cust_name=(EditText)findViewById(R.id.ep_cname);
        sp=getSharedPreferences("",MODE_PRIVATE);
        cname=sp.getString("cname","");
        cid = sp.getString("cid", "");
        cust_name.setText(cname);
        for (i = 0; i < ets.length; i++) {
            ets[i] = (EditText) findViewById(ids[i]);
        }
        ch=new ConnectionHelper();
        con=ch.getConnection();

    }

    public void cupdate(View view){
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
            QUERY="update hmn_creg set cmobile='"+values[0]+"',caddress='"+values[1]+"',ccity='"+values[2]+"' where cid='"+cid+"'";

            stmt.execute(QUERY);
            Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(EditCustProfile.this,CustomerProfile.class);
            startActivity(intent);


        } catch (SQLException e) {
            // e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

}
