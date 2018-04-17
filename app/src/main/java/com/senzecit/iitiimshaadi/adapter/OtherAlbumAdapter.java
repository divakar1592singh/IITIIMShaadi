package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.model.api_response_model.other_profile.AllAlbumPic;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.List;

/**
 * Created by ravi on 22/11/17.
 */

public class OtherAlbumAdapter extends BaseAdapter {

    Context context;
    List<AllAlbumPic> albumList;
    AppPrefs prefs;
    OnOtherAlbumClickListner listner;

    public OtherAlbumAdapter(Context context, List<AllAlbumPic> albumList) {
        this.context = context;
        this.albumList = albumList;
        listner = (OnOtherAlbumClickListner)context;
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.other_album_item, viewGroup, false);
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
//                Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
                listner.onAlbumClick(finalHolder.imageView, CONSTANTS.IMAGE_BASE_URL + albumList.get(i).getPicOrgUrl());
            }
        });

        return view;
    }

    class Holder {

        ImageView imageView;
        public Holder(View convertView) {
            imageView = convertView.findViewById(R.id.grid_imageView);
        }
    }

    public interface OnOtherAlbumClickListner{
        void onAlbumClick(ImageView thumbView, String imageURL);
    }

}
