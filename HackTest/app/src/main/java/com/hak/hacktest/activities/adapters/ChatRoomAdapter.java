package com.hak.hacktest.activities.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hak.hacktest.R;
import com.hak.hacktest.activities.dialog.ImageDialog;
import com.hak.hacktest.activities.objects.ChatRoom;
import com.hak.hacktest.activities.util.CircularTextView;
import com.hak.hacktest.activities.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ChatRoomAdapter extends ArrayAdapter<ChatRoom> {


    private Context context;
    private ArrayList<ChatRoom> list;

    public ChatRoomAdapter(Context context , ArrayList<ChatRoom> list) {
        super(context, R.layout.chat_room_layout_item , list);
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_room_layout_item, parent, false);
        final ChatRoom item = list.get(position);
        TextView category = (TextView)view.findViewById(R.id.category);
        TextView description = (TextView)view.findViewById(R.id.description);
        TextView name = (TextView)view.findViewById(R.id.name);
        ImageView image = (ImageView)view.findViewById(R.id.choose_image);
        CircularTextView un_read_messages = (CircularTextView) view.findViewById(R.id.un_read_messages);
        name.setText(item.getSellerName());
        category.setText(item.getChatName());
        description.setText(item.getDescription());
            un_read_messages.setVisibility(View.GONE);

        Picasso.with(context)
                .load(item.getPath())
                .transform(new RoundedTransformation(999,4))
                .fit()
                .centerCrop()
                .into(image);

        image.setOnClickListener(v -> {new ImageDialog(context , item.getPath());});

        return view;
    }


}
