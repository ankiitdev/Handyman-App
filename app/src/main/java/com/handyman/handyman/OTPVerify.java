package com.handyman.handyman;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

public class OTPVerify extends AppCompatActivity {
TextView tv_det;
String mnumber;
String otp;
EditText et_otp;
SmsVerifyCatcher smsVerifyCatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);
        tv_det=(TextView)findViewById(R.id.tv_det);
        et_otp=(EditText)findViewById(R.id.et_otp);
        Intent intent=getIntent();
        mnumber=intent.getStringExtra("mobilen");
        otp=intent.getStringExtra("OTP");
        tv_det.setText("Enter the 4-digit OTP received at "+mnumber);



    }

    public void codenotrec(View view)
    {
        Intent intent=new Intent(OTPVerify.this,CMobileActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Enter your number again!", Toast.LENGTH_SHORT).show();
       // ActivityCompat.finishAffinity(this);
    }
    public void checkotp(View view)
    {
        if(et_otp.getText().toString().isEmpty())
        {
            et_otp.setError("Enter OTP");
            et_otp.requestFocus();
            Toast.makeText(this, "Please enter the OTP!!", Toast.LENGTH_SHORT).show();
        }
        else if(et_otp.getText().toString().trim().equals(otp))
        {
            Intent intent=new Intent(OTPVerify.this,OTPSuccessful.class);
            intent.putExtra("mobile",mnumber);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Enter correct OTP!!", Toast.LENGTH_SHORT).show();
        }

    }


}
