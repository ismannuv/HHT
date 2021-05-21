package com.example.hht;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class IdleScreen extends ScreenController {

    private TextView countDown;
    private int i=0;
    @Override
    public void every1sec() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                countDown.setText(Integer.toString(i));
            }
        });
        i++;
    }

    @Override
    public void initialize(Bundle savedInstanceState) {
        setThread(true,false,0);
        setContentView(R.layout.activity_main);
        countDown =(TextView) findViewById(R.id.countDownTextView);
    }
}
