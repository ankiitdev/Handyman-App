package com.handyman.handyman;

/**
 * Created by hp on 31-01-2018.
 */
public class ShortName {
    public String getShortName(String name)
    {
        String shortname="";
        String []str=name.split(" ");
        for(int i=0;i<str.length;i++)
        {
            shortname+=str[i].charAt(0);
        }
        return shortname;
    }
}
