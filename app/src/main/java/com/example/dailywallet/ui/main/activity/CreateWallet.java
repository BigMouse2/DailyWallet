package com.example.dailywallet.ui.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dailywallet.MainActivity;
import com.example.dailywallet.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateWallet extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        Button create = (Button) findViewById(R.id.save);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityMainActivity();
            }
        });

        FloatingActionButton back  = (FloatingActionButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReturnHomeActivity();
            }
        });

    }
 /////test test test
    public void openActivityMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openReturnHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}