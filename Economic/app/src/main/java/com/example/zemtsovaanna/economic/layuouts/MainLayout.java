package com.example.zemtsovaanna.economic.layuouts;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.zemtsovaanna.economic.R;

/**
 * Created by zemtsovaanna on 05.09.16.
 */
public class MainLayout extends AppCompatActivity {
    private LinearLayout relativeLayout;
    private int width;
    private int height;
    private Button buttonAdd;
    private Button buttonLook;
    private Button buttonSettings;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        relativeLayout = new LinearLayout(this);
        relativeLayout.setOrientation(LinearLayout.VERTICAL);
        myScreenSize();
        LinearLayout.LayoutParams viewGroupLayoutParams = new LinearLayout.LayoutParams(width,height);
        setContentView(relativeLayout,viewGroupLayoutParams);
        BitmapDrawable d = (BitmapDrawable)
                getResources().getDrawable(R.drawable.fon5);
        relativeLayout.setBackgroundDrawable(d);
        viewGroupLayoutParams = new LinearLayout.LayoutParams(width/2+width/6,height/10);
        viewGroupLayoutParams.gravity = Gravity.CENTER;
        viewGroupLayoutParams.topMargin = width/6;
        buttonAdd = new Button(this);
        viewGroupLayoutParams.setLayoutDirection(R.drawable.rounded_button);
        buttonAdd.setText("Добавить расходы");
        buttonAdd.setAlpha((float) 0.7);


        buttonAdd.setBackgroundResource(R.drawable.rounded_button);
        relativeLayout.addView(buttonAdd,viewGroupLayoutParams);

        viewGroupLayoutParams = new LinearLayout.LayoutParams(width/2+width/6,height/10);
        viewGroupLayoutParams.gravity = Gravity.CENTER;
        viewGroupLayoutParams.topMargin = width/6-width/8;
        viewGroupLayoutParams.setLayoutDirection(R.drawable.rounded_button);
        buttonLook = new Button(this);
        buttonLook.setText("Посмотреть расходы");
        buttonLook.setAlpha((float) 0.7);

        buttonLook.setBackgroundResource(R.drawable.rounded_button);
        relativeLayout.addView(buttonLook,viewGroupLayoutParams);
        viewGroupLayoutParams = new LinearLayout.LayoutParams(width/2+width/6,height/10);
        viewGroupLayoutParams.gravity = Gravity.BOTTOM|Gravity.RIGHT;
        viewGroupLayoutParams.topMargin = width/2+width/6;
        viewGroupLayoutParams.rightMargin =width/10;
        viewGroupLayoutParams.bottomMargin = height/10;
        buttonSettings = new Button(this);
        buttonSettings.setText("Настройки");
        buttonSettings.setAlpha((float) 0.7);
        buttonSettings.setBackgroundResource(R.drawable.rounded_button);
        relativeLayout.addView(buttonSettings,viewGroupLayoutParams);
        listeners();
    }

    public void myScreenSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
    }

    public void listeners(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainLayout.this, AddLayout.class);
                startActivity(intent);
            }
        });
        buttonLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainLayout.this, ListLayout.class);
                startActivity(intent);
            }
        });

    }
}
