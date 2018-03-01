package com.aayushbhat.aayushiron.sivertongue;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.karan.churi.PermissionManager.PermissionManager;

public class MainActivity extends AppCompatActivity {

    ImageButton emotion, speed, filler;
    PermissionManager permissionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);

        emotion = findViewById(R.id.button);
        speed = findViewById(R.id.button4);
        filler = findViewById(R.id.imageButton6);

        emotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), emo.class);
                startActivity(i);
            }
        });

        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getApplicationContext(), spee.class);
                startActivity(j);
            }
        });

        filler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), filler.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.checkResult(requestCode, permissions, grantResults);
    }
}
