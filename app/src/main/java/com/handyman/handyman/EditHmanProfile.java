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

public class EditHmanProfile extends AppCompatActivity {
    SharedPreferences sp;
    String hname,hid;
    EditText[] ets=new EditText[5];
    int [] ids={R.id.ep_hmobile,R.id.ep_haddress,R.id.ep_hcity,R.id.ep_hpcode,R.id.ep_hemail};
    String [] values=new String[ets.length];
    int i;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    EditText ep_hname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hman_profile);
        sp=getSharedPreferences("hman_det",MODE_PRIVATE);
        hname=sp.getString("fname","")+" "+sp.getString("lname","");
        hid=sp.getString("id","");
        ep_hname=(EditText)findViewById(R.id.ep_hname);
        ep_hname.setText(hname);
        for (i = 0; i < ets.length; i++) {
            ets[i] = (EditText) findViewById(ids[i]);
        }
        ch=new ConnectionHelper();
        con=ch.getConnection();

    }
    public void hupdate(View view)
    {
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
            QUERY="update hmn_hreg set mobile='"+values[0]+"', address='"+values[1]+"', city='"+values[2]+"', pincode='"+values[3]+"', email='"+values[4]+"' where id='"+hid+"' ";

            stmt.execute(QUERY);
            Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(EditHmanProfile.this,HmanProfile.class);
            startActivity(intent);


        } catch (SQLException e) {
            // e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
