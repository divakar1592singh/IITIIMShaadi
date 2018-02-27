package com.senzecit.iitiimshaadi.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.senzecit.iitiimshaadi.viewController.SettingsActivity;

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
    String[] listItem;
    AppPrefs prefs;

    int[] imageItem = {R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2,
            R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2,
            R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2};

    public AlbumAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
        prefs = AppController.getInstance().getPrefs();
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
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
//        holder.imageView.setImageResource(imageItem[i]);

        try {
            Glide.with(context).load(Constants.IMAGE_BASE_URL + albumList.get(i).getPicOrgUrl()).error(R.drawable.ic_not_available).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
            }
        });

        holder.mSetProfileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Set Profile", Toast.LENGTH_SHORT).show();
                String profile = String.valueOf(albumList.get(i).getPicOrgUrl());
                callWebServiceForSetProfile(profile);

            }
        });

        holder.mSettingIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Setting", Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(context, "Output : " + position, Toast.LENGTH_SHORT).show();
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
                        .setTitle("Account Deletion!")
                        .setMessage("are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                String albumId = String.valueOf(albumList.get(i).getId());
                                callWebServiceForDelAlbum(albumId, i);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

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

    public void callWebServiceForSetProfile(String profile) {

//        String token = "1984afa022ab472e8438f115d0c5ee1b";

        String token = prefs.getString(Constants.LOGGED_TOKEN);

        ProgressClass.getProgressInstance().showDialog(context);
        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<AddFolderResponse> call = apiInterface.setProfileAlbum(token, profile);
        call.enqueue(new Callback<AddFolderResponse>() {
            @Override
            public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    AddFolderResponse serverResponse = response.body();
                    if (serverResponse.getMessage().getSuccess() != null) {
                        if (serverResponse.getMessage().getSuccess().toString().equalsIgnoreCase("success")) {

                            AlertDialogSingleClick.getInstance().showDialog(context, "Info", "Deleted Successfully");

                        } else {
                            Toast.makeText(context, "Confuse", Toast.LENGTH_SHORT).show();
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
    }


    public void callWebServiceForSetting(String album_id, String privacy) {

//        String token = "1984afa022ab472e8438f115d0c5ee1b";

        String token = prefs.getString(Constants.LOGGED_TOKEN);

        ProgressClass.getProgressInstance().showDialog(context);
        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
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
                            Toast.makeText(context, "Confuse", Toast.LENGTH_SHORT).show();
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
    }

    public void callWebServiceForDelAlbum(String album_id, int position) {

//        String token = "1984afa022ab472e8438f115d0c5ee1b";

        String token = prefs.getString(Constants.LOGGED_TOKEN);

        ProgressClass.getProgressInstance().showDialog(context);
        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
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
                            AlertDialogSingleClick.getInstance().showDialog(context, "Info", "Deleted Successfully");

                        } else {
                            Toast.makeText(context, "Confuse", Toast.LENGTH_SHORT).show();
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
    }


}
