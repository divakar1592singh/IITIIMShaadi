package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.model.api_response_model.all_album.Album;
import com.senzecit.iitiimshaadi.utils.Constants;

import java.util.List;

/**
 * Created by ravi on 22/11/17.
 */

public class AlbumAdapter extends BaseAdapter {

    Context context;
    List<Album> albumList ;
    String[] listItem;

    int[] imageItem = {R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2,
            R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2,
            R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2};

    public AlbumAdapter(Context context, List<Album> albumList){
        this.context = context;
        this.albumList = albumList;
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
        Holder holder =null;
        if(view==null){
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_coverage_item,viewGroup,false);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.album_item,viewGroup,false);
            holder = new Holder(view);
            view.setTag(holder);
        }else{
            holder = (Holder) view.getTag();
        }
//        holder.imageView.setImageResource(imageItem[i]);

        try {
            Glide.with(context).load(Constants.IMAGE_BASE_URL+albumList.get(i).getPicOrgUrl()).error(R.drawable.ic_not_available).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    class Holder{

        ImageView imageView;

        public Holder(View convertView){
            imageView = (ImageView) convertView.findViewById(R.id.grid_imageView);

        }
    }
}
