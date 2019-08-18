package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.katepratik.msg91api.MSG91;

public class CMobileActivity extends AppCompatActivity {
EditText et_mobile;
MSG91 msg91;
CaptchaGenerator cg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmobile);
        et_mobile=(EditText) findViewById(R.id.et_mobile);
        cg=new CaptchaGenerator();
    }

    public void next(View view)
    {
      String mnumber;
      mnumber=et_mobile.getText().toString();
      if(mnumber.isEmpty())
      {
       et_mobile.setError("Error");
          Toast.makeText(this, "Please enter your mobile number!!", Toast.LENGTH_SHORT).show();
      }
      else
      {
        msg91=new MSG91("196023AwNplgJ6qS5a722783");
        String otp=cg.getOTP();
        msg91.composeMessage("HNDMAN","Your OTP for HANDYMAN APP is "+otp);
        msg91.to(mnumber);
        msg91.setRoute("4");
        msg91.setCampaign("Transactional");
        msg91.send();
          Toast.makeText(this, "OTP sent successfully on your registered number!", Toast.LENGTH_SHORT).show();
          Intent intent=new Intent(CMobileActivity.this,OTPVerify.class);
          intent.putExtra("OTP",otp);
          intent.putExtra("mobilen",mnumber);
          startActivity(intent);

      }
    }
}
