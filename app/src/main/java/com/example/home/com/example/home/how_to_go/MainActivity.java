package com.example.home.how_to_go;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    public ImageButton btn_seek;
    public ImageButton btn_see;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_seek = (ImageButton)findViewById(R.id.btn_seek);
        btn_see = (ImageButton)findViewById(R.id.btn_see);

        btn_seek.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, SeekActivity.class);
                startActivity(intent);
            }
        });

        btn_see.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, SeeActivity.class);
                startActivity(intent);
            }

        });


        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
