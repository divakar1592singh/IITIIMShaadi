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
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 22/11/17.
 */

public class AlbumAdapter extends BaseAdapter {

    Context context;
    AlbumCommunicator communicator;
    List<Album> albumList;
    AppPrefs prefs;

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
                communicator.setImage(String.valueOf(albumList.get(i).getPicOrgUrl()));
            }
        });

        holder.mSettingIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String albumId = String.valueOf(albumList.get(i).getId());
                communicator.setAlbumPermission(albumId);
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

    public void callWebServiceForDelAlbum(String album_id, int position) {

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


    public interface AlbumCommunicator{

        void showImage(ImageView thumbView, int pos);
        void setImage(String url);
        void setAlbumPermission(String albumId);
    }
}
