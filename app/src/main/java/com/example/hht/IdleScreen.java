package com.example.hht;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import eo.view.batterymeter.BatteryMeterView;

public class IdleScreen extends ScreenController {


    private CardView cardView;
    private PopupWindow mPopupWindow;
    private ConstraintLayout mainlinearLayout;
    private TextView batteryPercentage;
    private TextView currentDayText;
    private TextView currentDateText;
    private eo.view.batterymeter.BatteryMeterView batteryMeterView;
    private TextClock timeClockHr;
    private TextClock timeClockMin;
    private com.google.android.material.progressindicator.LinearProgressIndicator linearProgressIndicator;
    private int progressStatus = 0;

    private  boolean flag=false;
    @Override
    public void every1sec() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
            }
        });
    }

    @Override
    public void initialize(Bundle savedInstanceState) {
        setThread(true,false,0);
        setContentView(R.layout.activity_main);
        //countDown =(TextView) findViewById(R.id.countDownTextView);
        getSupportActionBar().hide();

        cardView=(CardView) findViewById(R.id.cardViewEnterID);
        //batteryPercentage=(TextView) findViewById(R.id.batteryText);
        currentDayText=(TextView) findViewById(R.id.currentDayText);
        currentDateText=(TextView) findViewById(R.id.currentDateText);
        batteryMeterView=(BatteryMeterView) findViewById(R.id.batteryTextIcon);
        mainlinearLayout=(ConstraintLayout) findViewById(R.id.mainlinearLayout);
        timeClockHr=(TextClock) findViewById(R.id.textClockHr);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/Ubuntu-Bold.ttf");
        timeClockHr.setTypeface(typeface);
        timeClockMin=(TextClock) findViewById(R.id.textClockMin);
        timeClockMin.setTypeface(typeface);

        //Date configuration
        Date date= new Date();
        String dateFormat ="EEEE";
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(dateFormat);
        currentDayText.setText(simpleDateFormat.format(date));
        Calendar calendar = Calendar.getInstance();
        currentDateText.setText(new SimpleDateFormat(" dd MMM YYYY").format(calendar.getTime()).toString());


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                showErrorDialog("WRONG PIN");
                  showLoginPopup();
            }
        });

        registerReceiver(mBatInfoReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;
            float batteryPct = level * 100 / (float)scale;
            //batteryPercentage.setText(String.valueOf(batteryPct) + "%");
            batteryMeterView.setChargeLevel(level);
            batteryMeterView.setCharging(isCharging);



        }
    };

    private void showLoginPopup()
    {
        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.user_login, null);

        final EditText idTb = customView.findViewById(R.id.id);
        final EditText passwordTb = customView.findViewById(R.id.pin);
        Button userLoginButton = customView.findViewById(R.id.userLoginButton);
        Button closeLoginPopupButton = customView.findViewById(R.id.closeLoginPopupButton);


        userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idTb.getText().toString();
                String pin = passwordTb.getText().toString();

                Log.d("password",pin);

                if (pin.equals("1131")){
                    mPopupWindow.dismiss();
                    Intent myIntent = new Intent(IdleScreen.this, UserProfile.class);
                    startActivity(myIntent);

                }
                else {
                    mPopupWindow.dismiss();
                    showErrorDialog("WRONG PIN");
                    Toast.makeText(getApplicationContext(),"Wrong password. Try again.",Toast.LENGTH_LONG).show();
                }
            }
        });

        closeLoginPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setElevation(5.0f);
        mPopupWindow.showAtLocation(mainlinearLayout, Gravity.CENTER,0,0);
        mPopupWindow.setFocusable(true);
        mPopupWindow.update();
        mPopupWindow.setOutsideTouchable(false);

        /*InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);*/
    }
    private void showErrorDialog(String msg)
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.error_dialog, null);
        final TextView errorMsg = customView.findViewById(R.id.errorMsg);
        linearProgressIndicator = customView.findViewById(R.id.progressBar);
        errorMsg.setText(msg);
        Button okBtn = customView.findViewById(R.id.okBtn);


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=true;
                progressStatus=0;
                linearProgressIndicator.setProgress(progressStatus);
                mPopupWindow.dismiss();

            }
        });
        mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setElevation(5.0f);
        mPopupWindow.showAtLocation(mainlinearLayout, Gravity.CENTER,0,0);
        mPopupWindow.update();
        mPopupWindow.setOutsideTouchable(false);


        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if(flag)
                    {
                        flag=false;
                        break;

                    }
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            if(progressStatus>=100)
                            {
                                flag=true;
                                mPopupWindow.dismiss();
                                progressStatus=0;
                                linearProgressIndicator.setProgress(progressStatus);

                            }
                            linearProgressIndicator.setProgress(progressStatus);
                            //textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
