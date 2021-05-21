package com.example.hht;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ViewDialog {

    public static final int ERROR = 0, SUCCESS = 1;
    public static boolean skipAll;

    public void showDialog(Activity activity, String msg, int type){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        /*switch(type)
        {
            case ERROR: //error
                dialog.setContentView(R.layout.custom_error_dialog);
                break;
            case SUCCESS: //success
                dialog.setContentView(R.layout.custom_success_dialog);
                break;
        }

        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButtonOK = dialog.findViewById(R.id.btn_dialog);*/
        /*dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipAll = true;
                dialog.dismiss();
            }
        });*/
        dialog.show();
    }
}
