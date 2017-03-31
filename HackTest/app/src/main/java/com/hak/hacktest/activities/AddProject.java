package com.hak.hacktest.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hak.hacktest.R;
import com.hak.hacktest.activities.dialog.DescriptionDialog;
import com.hak.hacktest.activities.response.Response_add_project;
import com.hak.hacktest.activities.retroFit.API;
import com.hak.hacktest.activities.util.ConnectionManager;
import com.hak.hacktest.activities.util.ScalingUtilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProject extends AppCompatActivity {

    private  final String  LOG_TAG = this.getClass().getSimpleName();
    private Spinner spinner;
    private ProgressDialog progress;
    public static final int REQUEST_CODE_FOR_IMAGE = 513;
    private static final int CAMERA_REQUEST = 1888;
    private String ImagePath;
    private File destination; // for the camera


    private TextView description;
    private TextView price;
    private ImageView project_image;
    private ImageView plus_image;
    private Button confirm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        description = (TextView)findViewById(R.id.input_description);
        price = (TextView)findViewById(R.id.input_price);
        project_image = (ImageView)findViewById(R.id.project_image);
        plus_image = (ImageView)findViewById(R.id.plus_image);
        confirm = (Button)findViewById(R.id.confirm);
        project_image.requestFocus();


        description.setOnClickListener(v -> {
            DescriptionDialog descriptionDialog = new DescriptionDialog();
            descriptionDialog.build(AddProject.this , description.getText().toString()).setDialogResult(result -> {
                description.setText(result);
            });
        });

        plus_image.setOnClickListener(v -> {
            registerForContextMenu(v);
            openContextMenu(v);
        });


        confirm.setOnClickListener(v -> uploadProfilePhoto());
        setSpinner();
    }



    @Override
    public void onCreateContextMenu (ContextMenu menu, View
            v, ContextMenu.ContextMenuInfo menuInfo){
        //Context menu
        menu.setHeaderTitle(getResources().getString(R.string.menu_title_for_media));
        menu.add(Menu.NONE, 1, Menu.NONE, "Gallery");
        menu.add(Menu.NONE, 2, Menu.NONE, "Camera");
    }

    @Override
    public boolean onContextItemSelected (MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case 1:
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_CODE_FOR_IMAGE);
                break;
            case 2:
                destination = new File(Environment.getExternalStorageDirectory(),"image"+new Date().getTime()+".png");
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void setSpinner(){
        spinner = (Spinner) findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    public void setNewImage(Bitmap bm , String path){
        project_image.setImageBitmap(bm);
        ImagePath = decodeFile(path , 1000 , 1000);
    }

    private void uploadProfilePhoto() {

        if(!ConnectionManager.isNetworkAvailable())
            post(R.string.connection_error);
        else if(ImagePath == null){
            post(R.string.error_no_image);
        }
        else if(spinner.getSelectedItem().toString().equals(getResources().getStringArray(R.array.types)[0])){
            post(R.string.error_no_category);
        }
        else if(description.getText().toString().isEmpty()){
            post(R.string.error_no_description);
        }
        else if(price.getText().toString().isEmpty()){
            post(R.string.error_no_price);
        }
        else {
            showDialog();
            addProject(
                    ImagePath ,
                    "1",
                    "Omri lugasi" ,
                    spinner.getSelectedItem().toString(),
                    description.getText().toString(),
                    price.getText().toString() );

        }
    }


    private void showDialog() {
        progress = new ProgressDialog(this);
        progress.setMessage(getString(R.string.now_loading));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    private void hideDialog() {
        if (progress != null)
            progress.dismiss();
    }


    private void addProject(String imagePath, String userID, String userName, String spinner, String description, String price){
        File file = new File(imagePath);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        API.getAPI().addProject(

                filePart,
                userID,
                userName ,
                spinner,
                description,
                price

        ).enqueue(new Callback<Response_add_project>() {
            @Override
            public void onResponse(Call<Response_add_project> call, Response<Response_add_project> response) {
                if(response.body().isStatus()) {
//                    SnappyManager.getInstant().saveNewProjectLocally(new Project(response.body().getProject_id(),GlobalData.getInstant().getUserID(), GlobalData.getInstant().getUserName()
//                            ,response.body().getPath(), spinner, description, price));
                    addProjectResponse(true , R.string.project_add);
                }else{
                    addProjectResponse(false , R.string.server_issue);

                }
            }

            @Override
            public void onFailure(Call<Response_add_project> call, Throwable t) {
                addProjectResponse(false , R.string.server_issue);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOR_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            setNewImage(BitmapFactory.decodeFile(picturePath) , picturePath);
        }else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if(new File(destination.getPath()).exists())
                setNewImage(BitmapFactory.decodeFile(destination.getPath()) , destination.getPath());
            else
                Toast.makeText(this, "Dor, tell omri #5", Toast.LENGTH_SHORT).show();
        }
    }

    private void post(int stringID){
        Toast.makeText(AddProject.this, getString(stringID), Toast.LENGTH_SHORT).show();
    }

    public String decodeFile(String path,int DESIREDWIDTH, int DESIREDHEIGHT) {
        String strMyImagePath = null;
        Bitmap scaledBitmap = null;

        try {
            // Part 1: Decode image
            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);

            if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) {
                // Part 2: Scale image
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);
            } else {
                unscaledBitmap.recycle();
                return path;
            }

            // Store to tmp file

            String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + "/TMMFOLDER");
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }

            Date d = new Date();
            String s = d.getTime()+"tmp.png";

            File f = new File(mFolder.getAbsolutePath(), s);

            strMyImagePath = f.getAbsolutePath();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (Exception e) {

                e.printStackTrace();
            }

            scaledBitmap.recycle();
        } catch (Throwable e) {
        }

        if (strMyImagePath == null) {
            return path;
        }
        return strMyImagePath;

    }


    /**** CallBack response ****/


    public void addProjectResponse(boolean responseStatus, int post) {
        hideDialog();
        if(responseStatus){
            post(post);
            onBackPressed();
        }else{
            post(post);
        }
    }

}
