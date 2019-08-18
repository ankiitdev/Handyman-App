package com.handyman.handyman;

public class CaptchaGenerator
{
    public String getCapCode()
    {
        String code="";
        code+=(char)getRandomNumber('A','Z');
        code+=(char)getRandomNumber('A','Z');
        code+=(char)getRandomNumber('0','9');
        code+=(char)getRandomNumber('0','9');
        code+=(char)getRandomNumber('a','z');
        code+=(char)getRandomNumber('a','z');
        return code;
    }

    public String getOTP()
    {
        String otp="";
        for(int i=1;i<=4;i++)
        {
            otp+=(char)getRandomNumber('0','9');
        }
        return otp;
    }

    public String getVoucherCode()
    {
        String vc="";
        for(int i=1;i<=16;i++)
        {
            vc+=(char)getRandomNumber('0','9');
        }
        return vc;
    }


    public String getRandomPassword()
    {
        String pass="";
        pass+=(char)getRandomNumber('A','Z');
        pass+=(char)getRandomNumber('A','Z');
        pass+=(char)getRandomNumber(91,96);
        pass+=(char)getRandomNumber(91,96);
        pass+=(char)getRandomNumber('0','9');
        pass+=(char)getRandomNumber('0','9');
        pass+=(char)getRandomNumber('a','z');
        pass+=(char)getRandomNumber('a','z');
        return pass;
    }

    private int getRandomNumber(int min,int max)
    {
        java.util.Random r=new java.util.Random();
        return r.nextInt(max-min+1)+min;
    }










}
