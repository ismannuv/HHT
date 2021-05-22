package com.example.hht;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class UserProfile extends ScreenController {

    private PopupWindow mPopupWindow;
    private ConstraintLayout mainlinearLayout;
    private int timeout=0;


    @Override
    public void every1sec() {
        if(timeout>=10)
        {
            timeout=0;
            finish();

        }
        timeout++;
    }

    @Override
    public void initialize(Bundle savedInstanceState) {
        setThread(true,false,0);
        setContentView(R.layout.user_profile);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mainlinearLayout=(ConstraintLayout) findViewById(R.id.userProfileLayout);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.change_pin,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.changePin:
                showChangePinPopup();
                Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void showChangePinPopup()
    {
        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.changepin, null);
        final EditText oldPin = customView.findViewById(R.id.oldPin);
        final EditText newPin = customView.findViewById(R.id.newPin);
        final EditText confirmPin = customView.findViewById(R.id.confirmPin);
        Button changePin = customView.findViewById(R.id.changePinButton);
        Button closePopupButton = customView.findViewById(R.id.closePopupButton);


        changePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = oldPin.getText().toString();
                String pin = newPin.getText().toString();
                String confirPin = confirmPin.getText().toString();

            }
        });

        closePopupButton.setOnClickListener(new View.OnClickListener() {
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


}
