package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Forgot_cpass_verify extends AppCompatActivity {
    String otp_hman;
    EditText et_otp;
    String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_cpass_verify);
        et_otp=(EditText)findViewById(R.id.et_otp);
        Intent i=getIntent();
        otp_hman=i.getStringExtra("OTP_HMAN");
        mobile=i.getStringExtra("MOBILE");
    }

    public void codenr(View view)
    {
        Intent intent=new Intent(Forgot_cpass_verify.this,Forgot_hpass.class);
        startActivity(intent);
        Toast.makeText(this, "Enter your number again!", Toast.LENGTH_SHORT).show();
    }

    public void checkotp(View view)
    {
        // Intent i=new Intent(Forgot_cpass_verify.this,Forgot_cpass_cp.class);
         //i.putExtra("MOB",mobile);
         //startActivity(i);

        if(et_otp.getText().toString().isEmpty())
        {
            et_otp.setError("Enter OTP");
            et_otp.requestFocus();
            Toast.makeText(this, "Please enter the OTP!!", Toast.LENGTH_SHORT).show();
        }
        else if(et_otp.getText().toString().trim().equals(otp_hman))
        {
            Intent i=new Intent(Forgot_cpass_verify.this,Forgot_cpass_cp.class);
            //intent.putExtra("mobile",mnumber);
            i.putExtra("MOB",mobile);
            startActivity(i);
            finish();
        }
        else
        {
            Toast.makeText(this, "Enter correct OTP!!", Toast.LENGTH_SHORT).show();
        }


    }
}
