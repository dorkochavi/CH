package com.hak.hacktest.activities.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hak.hacktest.R;

public class DescriptionDialog {

    private Dialog dialog;
    private EditText et_description;
    private Activity activity;
    private Button button;
    private OnMyDialogResult mDialogResult;




    public DescriptionDialog build(Activity activity, String description){
        this.activity = activity;
        dialog = new Dialog(activity, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogTheme;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.desc_dialog);
        et_description = (EditText)dialog.findViewById(R.id.et_desc) ;
        button = (Button) dialog.findViewById(R.id.done);
        button.setOnClickListener(v -> {done();});
        et_description.setText(description);
        dialog.show();
        return this;
    }

    private void done(){
        if(et_description.getText().toString().isEmpty()){
            Toast.makeText(activity, "Please fill a valid description", Toast.LENGTH_SHORT).show();
        }else{
            if( mDialogResult != null ){
                mDialogResult.finish(et_description.getText().toString());
            }
            dialog.dismiss();
        }
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(String result);
    }
}
