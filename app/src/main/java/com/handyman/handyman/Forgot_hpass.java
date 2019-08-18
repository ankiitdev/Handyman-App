package com.handyman.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.katepratik.msg91api.MSG91;

public class Forgot_hpass extends AppCompatActivity {
String hmob;
    MSG91 msg91;
    CaptchaGenerator cg;

    EditText et_mob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_hpass);
        et_mob=(EditText)findViewById(R.id.et_mob);
        cg=new CaptchaGenerator();
    }

    public void nex(View view)
    {
        String mnumber;
        mnumber=et_mob.getText().toString();
        //Intent intent=new Intent(Forgot_hpass.this,Forgot_hpass_cp.class);
        //intent.putExtra("OTP_HMAN",otp);
        //intent.putExtra("MOBILE",mnumber);
        //startActivity(intent);
        if(mnumber.toString().isEmpty())
        {
            et_mob.setError("Error");
            Toast.makeText(this, "Please enter your mobile number!!", Toast.LENGTH_SHORT).show();
        }
        else
        {

             msg91=new MSG91("196023AwNplgJ6qS5a722783");
            String otp=cg.getOTP();
            //msg91.composeMessage("HNDMAN","Your OTP for HANDYMAN APP is "+otp);
            msg91.composeMessage("HMANFP","Enter this OTP to reset your password: "+otp);
            msg91.to(mnumber);
            msg91.setRoute("4");
            msg91.setCampaign("Transactional");
            msg91.send();
            Toast.makeText(this, "OTP sent successfully on your registered number!", Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(Forgot_hpass.this,Forgot_hpass_verify.class);
            intent.putExtra("OTP_HMAN",otp);
            intent.putExtra("MOBILE",mnumber);
            startActivity(intent);
            finish();

        }

    }
}
