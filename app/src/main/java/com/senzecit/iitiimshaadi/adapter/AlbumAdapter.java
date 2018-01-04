package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.senzecit.iitiimshaadi.R;

/**
 * Created by ravi on 22/11/17.
 */

public class AlbumAdapter extends BaseAdapter {
    Context context;
    String[] listItem;

    int[] imageItem = {R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2,
            R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2,
            R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2, R.drawable.profile_img2};

    public AlbumAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return imageItem.length;
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
        holder.imageView.setImageResource(imageItem[i]);
        return view;
    }

    class Holder{

        ImageView imageView;

        public Holder(View convertView){
            imageView = (ImageView) convertView.findViewById(R.id.grid_imageView);

        }
    }
}
