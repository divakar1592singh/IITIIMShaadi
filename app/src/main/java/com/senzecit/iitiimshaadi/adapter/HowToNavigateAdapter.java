package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.activity.YoutubeActivity;
import com.senzecit.iitiimshaadi.model.how_to_navigates.VideoModel;

import java.util.ArrayList;

/**
 * Created by senzec on 7/12/17.
 */

public class HowToNavigateAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<VideoModel> youtubeArrList;
    LayoutInflater layoutInflater;
    View itemView;
    String[] VideoID = {"BGeoMIfSLRY","_pfeDDDu_pE","G0dPbujWf0w","B9RKdo52kBQ","El8Cve9qPwU"};

    public HowToNavigateAdapter(Context mContext, ArrayList<VideoModel> youtubeArrList){
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
        this.youtubeArrList = youtubeArrList;
    }


    @Override
    public int getCount() {
        return youtubeArrList.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        itemView = layoutInflater.inflate(R.layout.list_row_youtube, null);
        TextView tvTitle = (TextView)itemView.findViewById(R.id.idVideoTitle);
        ImageView ivVideoThumb = (ImageView)itemView.findViewById(R.id.idVideoThumb);

        tvTitle.setText(youtubeArrList.get(i).getTitle());

        int resourceId = mContext.getResources().getIdentifier(youtubeArrList.get(i).getVideo_thumb(), "drawable",
                mContext.getPackageName());
        ivVideoThumb.setImageResource(resourceId);

        ivVideoThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, YoutubeActivity.class);
                intent.putExtra("VideoCode", VideoID[i]);
                mContext.startActivity(intent);
            }
        });

        return itemView;
    }
}
