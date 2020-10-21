package com.eflexsoft.bloggingme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.eflexsoft.bloggingme.databinding.ActivityMainBinding;
import com.eflexsoft.bloggingme.fragment.HomeFragment;
import com.eflexsoft.bloggingme.fragment.MessageFragment;
import com.eflexsoft.bloggingme.fragment.NotificationFragment;
import com.eflexsoft.bloggingme.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        activityMainBinding.out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//            }
//        });
        activityMainBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PostActivity.class));
            }
        });

        PrettyTime p = new PrettyTime();
        System.out.println(p.format(new Date()));
        //prints: “moments from now”
//        p.format("edw2w");

        System.out.println(p.format(new Date(System.currentTimeMillis() + 1000*60*10)));
        //prints: “10 minutes from now”

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new HomeFragment()).commit();
        }

        activityMainBinding.nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:

                        fragment = new HomeFragment();

                        break;
                    case R.id.search:
                        fragment = new SearchFragment();

                        break;
                    case R.id.message:

                        fragment = new MessageFragment();
                        break;

                    case R.id.notification:

                        fragment = new NotificationFragment();
                        break;


                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();

                return true;
            }
        });

    }
}