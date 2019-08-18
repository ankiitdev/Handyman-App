package com.handyman.handyman;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity {
    Spinner works;
    String hworks[] = {"Carpenter", "Plumber", "Electrician", "Painter", "Gardener", "SERVICE NOT MENTIONED"};
    ArrayAdapter arrayAdapter;
    EditText[] ets = new EditText[8];
    int[] ids = {R.id.et_fname, R.id.et_lname, R.id.et_mobile, R.id.et_address, R.id.et_city, R.id.et_pcode, R.id.et_email, R.id.et_password};
    String[] values = new String[ets.length];
    String fname,lname,mob,address,city,pcode,mail,pass;
    EditText et_fname,et_lname,et_mobile,et_address,et_city,et_pcode,et_email,et_password,et_stype;
    int i;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        works = (Spinner) findViewById(R.id.sp_works);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, hworks);

        works.setAdapter(arrayAdapter);
        et_fname=(EditText)findViewById(R.id.et_fname);
        et_lname=(EditText)findViewById(R.id.et_lname);
        et_mobile=(EditText)findViewById(R.id.et_mobile);
        et_address=(EditText)findViewById(R.id.et_address);
        et_city=(EditText)findViewById(R.id.et_city);
        et_pcode=(EditText)findViewById(R.id.et_pcode);
        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);
        et_stype=(EditText)findViewById(R.id.et_stype);
       /*for (i = 0; i < ets.length; i++) {
            ets[i] = (EditText) findViewById(ids[i]);
        }*/

        ch = new ConnectionHelper();
        con = ch.getConnection();
    }

    public void register(View view) {
    /*  for(i=0;i<ets.length;i++)
      {
          if(ets[i].getText().toString().isEmpty())
          {
              ets[i].setError("Empty");
              ets[i].requestFocus();
              break;
          }
      }
        if (i == ets.length) {
            for (i = 0; i < ets.length; i++) {
                values[i] = ets[i].getText().toString().trim();
            }
        }*/
            // Toast.makeText(this, ""+values[0]+values[1]+values[2]+values[3]+values[4]+values[5]+values[6]+values[7]+works.getSelectedItem(), Toast.LENGTH_SHORT).show();
        fname=et_fname.getText().toString().trim();
        lname=et_lname.getText().toString().trim();
        mob=et_mobile.getText().toString().trim();
        address=et_address.getText().toString().trim();
        city=et_city.getText().toString().trim();
        pcode=et_pcode.getText().toString().trim();
        mail=et_email.getText().toString().trim();
        pass=et_password.getText().toString().trim();
        //Toast.makeText(this, ""+fname+lname+mob+address+city+pcode+mail+pass, Toast.LENGTH_SHORT).show();

        if(et_fname.getText().toString().isEmpty())
        {
            et_fname.setError("Empty");
            et_fname.requestFocus();

        }
        else if(et_lname.getText().toString().isEmpty())
        {
            et_lname.setError("Empty");
            et_lname.requestFocus();

        }
        else if(et_mobile.getText().toString().isEmpty())
        {
            et_mobile.setError("Empty");
            et_mobile.requestFocus();

        }
        else if(et_address.getText().toString().isEmpty())
        {
            et_address.setError("Empty");
            et_address.requestFocus();

        }
        else if(et_city.getText().toString().isEmpty())
        {
            et_city.setError("Empty");
            et_city.requestFocus();

        }
        else if(et_pcode.getText().toString().isEmpty())
        {
            et_pcode.setError("Empty");
            et_pcode.requestFocus();

        }
        else if(et_email.getText().toString().isEmpty())
        {
            et_email.setError("Empty");
            et_email.requestFocus();

        }
        else if(et_password.getText().toString().isEmpty())
        {
            et_password.setError("Empty");
            et_password.requestFocus();

        }


        else
        {
            new background(this).execute();
            Toast.makeText(RegisterActivity.this, "User registered! Please LOGIN to continue...", Toast.LENGTH_SHORT).show();

        }

    }

          public class background extends AsyncTask<Void,Void,Void>
          {
              ProgressDialog pd;

              public background(RegisterActivity loginActivity) {
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
                  //Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                 // startActivity(intent);
              }

              @Override
              protected Void doInBackground(Void... voids) {

                  try {
                      stmt = con.createStatement();
                      String QUERY;
                      // QUERY = "INSERT INTO hmn_hreg values('" + values[0] + "','" + values[1] + "','" + values[2] + "','" + works.getSelectedItem() + "','" + values[3] + "','" + values[4] + "','" + values[5] + "','" + values[6] + "','" + values[7] + "')";
                      QUERY = "INSERT INTO hmn_hreg values('" + fname + "','" + lname + "','" + mob + "','" + works.getSelectedItem() + "','" + address + "','" + city + "','" + pcode + "','" + mail + "','" + pass + "')";

                      stmt.execute(QUERY);
                      pd.dismiss();
                    //  Toast.makeText(RegisterActivity.this, "User registered! Please LOGIN to continue...", Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                      startActivity(intent);
                      finish();


                  } catch (SQLException e) {
                       e.printStackTrace();
                      //Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                  }


                  return null;
              }
          }


}



