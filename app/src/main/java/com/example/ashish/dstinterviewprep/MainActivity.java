package com.example.ashish.dstinterviewprep;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
/*
    <color name="colorPrimary">#24e21a</color>
    <color name="colorPrimaryDark">#00ab1a</color>*/
public class MainActivity extends AppCompatActivity {
    TextView desc;
    LinearLayout visit;
    Button start;
    Animation bottomToTop, leftToRight, rightToLeft;
    String url = "https://courses.learncodeonline.in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        desc = findViewById(R.id.desc);
        start = findViewById(R.id.start_button);
        visit = findViewById(R.id.adv_layout);
        bottomToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        leftToRight = AnimationUtils.loadAnimation(this, R.anim.left_to_right);
        rightToLeft = AnimationUtils.loadAnimation(this, R.anim.right_to_left);
        desc.startAnimation(bottomToTop);
        start.startAnimation(leftToRight);
        visit.startAnimation(rightToLeft);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, QuestionAnswerActivity.class);
                startActivity(i);
            }
        });
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebsite(url);
            }
        });
    }

    private void showWebsite(String webUrl) {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}