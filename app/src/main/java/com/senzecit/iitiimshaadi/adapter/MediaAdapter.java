package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.senzecit.iitiimshaadi.R;

import java.util.ArrayList;

/**
 * Created by ravi on 17/11/17.
 */

public class MediaAdapter extends BaseAdapter {
    Context context;

    String[] listItem;
//    int[] imageItem = {R.drawable.media_mg1,R.drawable.media_img2,R.drawable.media_img3,R.drawable.media_img4,
//            R.drawable.media_img5,R.drawable.media_img6,R.drawable.media_img7,R.drawable.media_img8};
//
    ArrayList<String> imageItem;
    public MediaAdapter(Context context, ArrayList<String> imageItem){
        this.context = context;
        this.imageItem = imageItem;
    }
    @Override
    public int getCount() {
        return imageItem.size();
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.media_coverage_item,viewGroup,false);
            holder = new Holder(view);
            view.setTag(holder);
        }else{
            holder = (Holder) view.getTag();
        }

        int resourceId = context.getResources().getIdentifier(imageItem.get(i), "drawable",
                context.getPackageName());
        holder.imageView.setImageResource(resourceId);

        return view;
    }

    class Holder{

        ImageView imageView;

        public Holder(View convertView){
            imageView = convertView.findViewById(R.id.grid_imageView);

        }
    }
}
