package com.discover.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends AppCompatActivity implements OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnPost = findViewById(R.id.buttonPost);
        btnPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.buttonPost:
                Intent changeActivity = new Intent(
                        getApplicationContext(),
                        WeatherReport.class);
                startActivity(changeActivity);
                break;
        }
    }
}
