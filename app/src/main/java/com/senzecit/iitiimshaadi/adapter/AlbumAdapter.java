package com.senzecit.iitiimshaadi.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.all_album.Album;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.pic_response.SetProfileResponse;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.senzecit.iitiimshaadi.viewController.AlbumActivity;
import com.senzecit.iitiimshaadi.viewController.ProfileActivity;
import com.senzecit.iitiimshaadi.viewController.SplashActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 22/11/17.
 */

public class AlbumAdapter extends BaseAdapter {

    Context context;
    List<Album> albumList;
    AppPrefs prefs;
    private Uri mCropImagedUri;
    private final int CROP_IMAGE = 101;

    AlbumCommunicator communicator;

    public void setAlbumCommunicator(Activity activity){
        this.communicator = (AlbumCommunicator) activity;
    }

    public AlbumAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
        prefs = AppController.getInstance().getPrefs();


        try{
            communicator = (AlbumCommunicator) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Object getItem(int i) {
        return CONSTANTS.IMAGE_BASE_URL + albumList.get(i).getPicOrgUrl();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.album_item, viewGroup, false);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        try {
            Glide.with(context).load(CONSTANTS.IMAGE_BASE_URL + albumList.get(i).getPicOrgUrl()).error(R.drawable.ic_not_available).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Holder finalHolder = holder;
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.showImage(finalHolder.imageView, i);
            }
        });

        holder.mSetProfileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*//                Toast.makeText(context, "Set Profile", Toast.LENGTH_SHORT).show();
                String profile = String.valueOf(albumList.get(i).getPicOrgUrl());
//                callWebServiceForSetProfile(profile);
                String profileURI = CONSTANTS.IMAGE_BASE_URL + albumList.get(i).getPicOrgUrl();
//                cropImageFromURI(profileURI);
                BackgroundWorker backgroundWorker = new BackgroundWorker(context);
                backgroundWorker.execute(profileURI);*/
                communicator.setImage(String.valueOf(albumList.get(i).getPicOrgUrl()));

            }
        });

        holder.mSettingIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Setting", Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog_list);

                ListView lv = (ListView) dialog.findViewById(R.id.lv);

                String[] foldername = {"All Members", "Only Friends", "Private"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, foldername);
                lv.setAdapter(adapter);
                dialog.setCancelable(true);
                dialog.setTitle("Change Display Permissions");
                dialog.show();

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                        Toast.makeText(context, "Output : " + position, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        String albumId = String.valueOf(albumList.get(position).getId());
                        if (position == 0) {
                            callWebServiceForSetting(albumId, "2");
                        } else if (position == 1) {
                            callWebServiceForSetting(albumId, "4");
                        } else if (position == 2) {
                            callWebServiceForSetting(albumId, "6");
                        }
//                        callWebServiceForManipulatePartner(UserDefinedKeyword.MOVETO.toString(), friend_id, folderId);
//                        callWebServiceForDeactivate(album_id, privacy);
                    }
                });
            }
        });

        holder.mDeleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Album Deletion!")
                        .setMessage("are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                String albumId = String.valueOf(albumList.get(i).getId());
                                callWebServiceForDelAlbum(albumId, i);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });


        return view;
    }

    class Holder {

        ImageView imageView, mSetProfileIV, mSettingIV, mDeleteIV;

        public Holder(View convertView) {

            imageView = (ImageView) convertView.findViewById(R.id.grid_imageView);
            mSetProfileIV = (ImageView) convertView.findViewById(R.id.idSetProfileIV);
            mSettingIV = (ImageView) convertView.findViewById(R.id.idSettingIV);
            mDeleteIV = (ImageView) convertView.findViewById(R.id.idDeleteIV);

        }
    }

    public void callWebServiceForSetting(String album_id, String privacy) {

//        String token = "1984afa022ab472e8438f115d0c5ee1b";

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(context) == true){

        ProgressClass.getProgressInstance().showDialog(context);
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<AddFolderResponse> call = apiInterface.settingAlbum(token, album_id, privacy);
        call.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    AddFolderResponse serverResponse = response.body();
                    if (serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

                            AlertDialogSingleClick.getInstance().showDialog(context, "Info", "Permission Changed Successfully");

                        } else {
//                            Toast.makeText(context, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(context);
        }
    }

    public void callWebServiceForDelAlbum(String album_id, int position) {

//        String token = "1984afa022ab472e8438f115d0c5ee1b";

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(context) == true){

        ProgressClass.getProgressInstance().showDialog(context);
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
        Call<AddFolderResponse> call = apiInterface.deleteAlbum(token, album_id);
        call.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    AddFolderResponse serverResponse = response.body();
                    if (serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

                            albumList.remove(position);
                            notifyDataSetChanged();
//                            AlertDialogSingleClick.getInstance().showDialog(context, "Info", "Deleted Successfully");
                            Toast.makeText(context, "Deletion Successfull", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(context, "Confuse", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });

        }else {
            NetworkDialogHelper.getInstance().showDialog(context);
        }
    }

//    CROP IMAGE FROM URI
    public class BackgroundWorker extends AsyncTask<String,String,Uri> {
        Context context;
        String result;
        BackgroundWorker(Context ctx) {
            context = ctx;

        }


        @Override
        protected void onPreExecute() {
        ProgressClass.getProgressInstance().showDialog(context);
        }

        @Override
        protected Uri doInBackground(String... params) {

            String imagePath = params[0];
            Uri uri = loadImageFromUri(imagePath);

            return  uri;
        }

        @Override
        protected void onPostExecute(Uri result) {
        ProgressClass.getProgressInstance().stopProgress();

            System.out.println(result);
            try {
//                callWebServiceForFileUpload(result);
                performCropImage(result);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

  /*  @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }*/
    }

    public Uri loadImageFromUri(String imagePath){
        File file = null;

        try {
            URL myImageURL = new URL(imagePath);
            HttpURLConnection connection = (HttpURLConnection)myImageURL.openConnection();
            connection.setDoInput(true);
//            connection.connect();
            InputStream input = connection.getInputStream();

            // Get the bitmap
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            // Save the bitmap to the file
//
                    String dir = "Alpha";
            // External sdcard location
            File mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    dir);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("TAG", "Oops! Failed create "
                            + dir + " directory");
                    return null;
                }
            }

            String path = mediaStorageDir.getPath();
//                    --


            OutputStream fOut = null;
            file = new File(path, "temp.png");
            fOut = new FileOutputStream(file);

            myBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        }
        catch (IOException e) {}

        Log.w("tttt", "got bitmap");

        Uri uri = Uri.fromFile(file);
        return uri;
    }

    // ----------------------CROP--------------------------
    /**Crop the image
     * @Crop if crop supports by the device,otherwise false*/
    private boolean performCropImage(Uri mFinalImageUri) throws URISyntaxException{
        try {
            if(mFinalImageUri!=null){
                //call the standard crop action intent (the user device may not support it)
                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                //indicate image type and Uri
                cropIntent.setDataAndType(mFinalImageUri, "image/*");
                //set crop properties
                cropIntent.putExtra("crop", "true");
                cropIntent.putExtra("scale", true);
                //indicate output X and Y
                cropIntent.putExtra("outputX", 349);
                cropIntent.putExtra("outputY", 349);
                //retrieve data on return
                cropIntent.putExtra("return-data", false);

                File f = createNewFile("CROP_");
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                    Log.e("io", ex.getMessage());
                }

                mCropImagedUri = Uri.fromFile(f);
                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImagedUri);
                //start the activity - we handle returning in onActivityResult
                ((Activity) context).startActivityForResult(cropIntent, CROP_IMAGE);
                return true;
            }
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return false;
    }

    private File createNewFile(String prefix){
        if(prefix==null || "".equalsIgnoreCase(prefix)){
            prefix="IMG_";
        }
        File newDirectory = new File(Environment.getExternalStorageDirectory()+"/mypics/");
        if(!newDirectory.exists()){
            if(newDirectory.mkdir()){
                Log.d("MyProfileActivity", newDirectory.getAbsolutePath()+" directory created");
            }
        }
        File file = new File(newDirectory,(prefix+System.currentTimeMillis()+".jpg"));
        if(file.exists()){
            //this wont be executed
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }


    public interface AlbumCommunicator{

        void showImage(ImageView thumbView, int pos);
        void setImage(String url);
        void setAlbumPermission();
        void deleteAbum();
    }
}
