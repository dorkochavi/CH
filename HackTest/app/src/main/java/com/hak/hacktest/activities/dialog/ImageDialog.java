package com.hak.hacktest.activities.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ImageView;

import com.hak.hacktest.R;
import com.squareup.picasso.Picasso;



public class ImageDialog {

    public ImageDialog(Context context , String path) {
        Dialog dialog;
        dialog = new Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogTheme;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_dialog);
        ImageView image = (ImageView)dialog.findViewById(R.id.choose_image);
        ImageView close = (ImageView)dialog.findViewById(R.id.close);
        Picasso.with(context).load(path).into(image);

        close.setOnClickListener(v -> {dialog.dismiss();});

        dialog.show();
    }
}
