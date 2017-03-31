package com.hak.hacktest.activities.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hak.hacktest.R;
import com.hak.hacktest.activities.ScrollActivity;
import com.hak.hacktest.activities.adapters.MainAdapter;
import com.hak.hacktest.activities.objects.Project;
import com.hak.hacktest.activities.response.GetRemoteProjects;
import com.hak.hacktest.activities.retroFit.API;
import com.hak.hacktest.activities.util.ConnectionManager;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by omri on 30/03/2017.
 */

public class MainFragment extends Fragment {

    private View v;
    private ScrollActivity activity;
    private ArrayAdapter<Project> arrayAdapter;
    private ArrayList<Project> mMainArrayList;
    TextView mError;
    SwipeFlingAdapterView flingContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.main_fragment, container, false);
        activity = (ScrollActivity)getActivity();
         mError = (TextView)v.findViewById(R.id.error);
        flingContainer = (SwipeFlingAdapterView)v.findViewById(R.id.frame);
        populateData();
        return v;
    }


    public void populateData(){
        if(ConnectionManager.isNetworkAvailable()){

            API.getAPI().getProjectsAdapter("1" , "1").enqueue(new Callback<GetRemoteProjects>() {
                @Override
                public void onResponse(Call<GetRemoteProjects> call, Response<GetRemoteProjects> response) {
                    if(response.body().isStatus()){
                        mMainArrayList = response.body().getProjectList();
                        consumeDataToAdapter();
                        arrayAdapter.notifyDataSetChanged();
                    }else
                        post("list not found");
                }

                @Override
                public void onFailure(Call<GetRemoteProjects> call, Throwable t) {

                }
            });

        }else{
            post("Connect to the internet first");
        }
    }


    private void consumeDataToAdapter(){
        //choose your favorite adapter
        arrayAdapter = new MainAdapter(getActivity() , mMainArrayList);

        //set the listener and the adapter
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                mMainArrayList.remove(0);
                arrayAdapter.notifyDataSetChanged();
                showAdapterEmpty();

            }

            @Override
            public void onLeftCardExit(Object dataObject) {

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Project project = (Project)dataObject;
                // Inflate any custom view
                View customView = getActivity().getLayoutInflater().inflate(R.layout.custom_crouton_layout, null);

                TextView text = (TextView)customView.findViewById(R.id.text_message);
                text.setText(getString(R.string.new_chat) +" "+ project.getUser_name());
                Crouton.show(getActivity(), customView);
//                saveNewChatRoom(project , activity);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float v) {

            }
        });

        showAdapterEmpty();
    }

    private void showAdapterEmpty(){
        if(mMainArrayList.size() == 0) {
            mError.setVisibility(View.VISIBLE);
            flingContainer.setVisibility(View.GONE);
        }
    }


    public void post(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
