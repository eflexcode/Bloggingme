package com.eflexsoft.bloggingme.model;

import androidx.databinding.BindingAdapter;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class NavBinding {

    public static void main(String[] args)
    {
        PrettyTime p = new PrettyTime();
        System.out.println(p.format(new Date()));
        //prints: “moments from now”

        System.out.println(p.format(new Date(System.currentTimeMillis() + 1000*60*10)));
        //prints: “10 minutes from now”
    }



}
