package com.eflexsoft.bloggingme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eflexsoft.bloggingme.databinding.ActivityOptionsBinding;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_options);

        ActivityOptionsBinding activityOptionsBinding = DataBindingUtil.setContentView(this, R.layout.activity_options);

//        List<SlideModel> slideModelList = new ArrayList<>();
//        slideModelList.add(new SlideModel(R.drawable.diary, "it's all about me and my ideas", ScaleTypes.CENTER_INSIDE));
//        slideModelList.add(new SlideModel(R.drawable.idea, "have an idea or something happened today !", ScaleTypes.CENTER_INSIDE));
//        slideModelList.add(new SlideModel(R.drawable.pen, "write it for others to read", ScaleTypes.CENTER_INSIDE));
//        slideModelList.add(new SlideModel(R.drawable.videocall, "it's a representation of me", ScaleTypes.CENTER_INSIDE));
//        slideModelList.add(new SlideModel(R.drawable.mobile_reading, "read about others", ScaleTypes.CENTER_INSIDE));
//        slideModelList.add(new SlideModel(R.drawable.question, "ask questions get answers", ScaleTypes.CENTER_INSIDE));
//
//        activityOpionsBinding.imageSlider.setImageList(slideModelList,ScaleTypes.CENTER_INSIDE);

        activityOptionsBinding.loginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionsActivity.this,CreateAccountActivity.class));

            }
        });

        activityOptionsBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionsActivity.this,LoginActivity.class));
            }
        });

    }
}