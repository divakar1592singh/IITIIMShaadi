package com.senzecit.iitiimshaadi.sliderView.with_list;

/**
 * Created by senzec on 4/12/17.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;

import java.util.ArrayList;

/**
 * Created by senzec on 30/11/17.
 */


public class SliderDialogListLayoutAdapter extends RecyclerView.Adapter<SliderDialogListLayoutAdapter.MyViewHolder> {

    public ArrayList<SliderDialogListLayoutModel> listData;
    private Context context;
    OnItemClickListener mItemClickListener;

    public SliderDialogListLayoutAdapter(Context context, ArrayList<SliderDialogListLayoutModel> listData) {
        this.context=context;
        this.listData = listData;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, genre;
        View container;
        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.unit);
//            genre = (TextView) view.findViewById(R.id.quantity);
            this.container=view;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            int positionInList = getAdapterPosition() % listData.size();
//            Toast.makeText(context,""+listData.get(positionInList).getName(),Toast.LENGTH_SHORT).show();
            mItemClickListener.onItemClick(v, getAdapterPosition(), ""); //OnItemClickListener mItemClickListener;

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String id);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_dialog_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        SliderDialogListLayoutModel movie = listData.get(position);
        holder.title.setText(movie.getName());

        /*if(listData.size()>0){
            int positionInList = position % listData.size();
            SliderDialogListLayoutModel movie = listData.get(positionInList);
            holder.title.setText(movie.getName());

            if(listData.get(positionInList).getColor().equalsIgnoreCase("1")){
                holder.container.setBackgroundColor(Color.RED);
                System.out.println("HOlder Red "+position +" "+positionInList);
            }
            else{
                holder.container.setBackgroundColor(Color.WHITE);
            }*/
//        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.slider_dialog_list_row, null);
            holder = new ViewHolder();
            holder.unitView = (TextView) convertView.findViewById(R.id.unit);
            holder.quantityView = (TextView) convertView.findViewById(R.id.quantity);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.unitView.setText(listData.get(position).getName().toString());
//        holder.quantityView.setText(listData.get(position).getUnit().toString());

        return convertView;
    }

    static class ViewHolder {
        TextView unitView;
        TextView quantityView;
    }*/

}