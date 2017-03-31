package com.hak.hacktest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hak.hacktest.R;
import com.hak.hacktest.activities.dialog.ImageDialog;
import com.hak.hacktest.activities.objects.ChatRoom;
import com.hak.hacktest.activities.util.CustomLayout;
import com.hak.hacktest.activities.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

public class ChatActivity extends AppCompatActivity {
    private String path = "https://cdn.pixabay.com/photo/2016/12/17/18/51/persimmon-1914127_960_720.jpg";
    ChatRoom chatroom = new ChatRoom(path , "chat name" , "Dor kohavi" , "Omri lugasi" , "1" , "1" , "Description");


    EditText etMessage;
    ImageView sendButton;
     ImageView circleImage;
    ListView lv;
    TextView userName;
    ImageView menu;
     ImageView previewImage;
     TextView previewText;
     TextView typing;

    CustomLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        previewImage = (ImageView) findViewById(R.id.circale_image);
        circleImage = (ImageView) findViewById(R.id.preview_image);
        relativeLayout = (CustomLayout) findViewById(R.id.activity_chat);

        setImages();
        setBackGround();
    }

    private void setBackGround(){
        Picasso.with(this)
                .load(chatroom.getPath())
                .into(relativeLayout);
    }

    private void setImages(){
        Picasso.with(this)
                .load( chatroom.getPath())
                .transform(new RoundedTransformation(999,4))
                .fit()
                .centerCrop()
                .into(circleImage);

        circleImage.setOnClickListener(v -> {new ImageDialog(this , chatroom.getPath());});

        Picasso.with(this)
                .load(chatroom.getPath())
                .transform(new RoundedTransformation(999,4))
                .fit()
                .centerCrop()
                .into(previewImage);
    }

}
