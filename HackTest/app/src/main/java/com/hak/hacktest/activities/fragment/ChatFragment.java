package com.hak.hacktest.activities.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hak.hacktest.R;
import com.hak.hacktest.activities.ChatActivity;
import com.hak.hacktest.activities.adapters.ChatRoomAdapter;
import com.hak.hacktest.activities.objects.ChatRoom;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private ListView lv;
    private ArrayList<ChatRoom> listItems;
    private ArrayAdapter<ChatRoom> adapter;
    private View v;
    private String path = "https://cdn.pixabay.com/photo/2016/12/17/18/51/persimmon-1914127_960_720.jpg";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.chat_fragment, container, false);
        listItems = new ArrayList<>();
        listItems.add(new ChatRoom(path ,"chat name" , "Dor kohavi" , "Omri lugasi" , "1" , "1" , "Description"));
        lv = (ListView)v.findViewById(R.id.list_chat);
        adapter = new ChatRoomAdapter(getActivity() , listItems);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(getActivity() , ChatActivity.class)));
        return v;
    }
}
