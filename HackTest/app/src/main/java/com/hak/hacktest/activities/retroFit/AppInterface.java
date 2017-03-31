package com.hak.hacktest.activities.retroFit;

import com.hak.hacktest.activities.response.GetRemoteProjects;
import com.hak.hacktest.activities.response.Response_add_project;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AppInterface {

    /***************
     *
     *
     * Manage projects
     *
     **************
     */


    //add new project
    @Multipart
    @POST("project/addProject")
    Call<Response_add_project> addProject(@Part MultipartBody.Part filePart, @Part("user_id") String user_id, @Part("user_name") String user_name,
                                          @Part("category") String category, @Part("description") String description, @Part("price") String price);


    @FormUrlEncoded
    @POST("project/getAllProject")
    Call<GetRemoteProjects> getProjectsAdapter(@Field("user_id") String id, @Field("from_position") String savedNumber);


}
