package com.senzecit.iitiimshaadi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.fragment.ResultSearchPartnerFragment;

/**
 * Created by ravi on 14/11/17.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder> implements View.OnClickListener {

    ResultSearchPartnerFragment.ResultSearchPartnerFragmentCommunicator communicator;
    public SearchResultAdapter(ResultSearchPartnerFragment.ResultSearchPartnerFragmentCommunicator communicator){
        this.communicator = communicator;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item,parent,false);
        return new SearchResultAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mDetails.setOnClickListener(this);
//        holder.mPartnerPref.setOnClickListener(this);
        holder.mAlbum.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detailsBtn:
                communicator.details();
                break;
            case R.id.partnerPrefBtn:
                communicator.partnerPref();
                break;
            case R.id.albumsBtm:
                communicator.albums();
                break;

        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        Button mDetails,mPartnerPref,mAlbum;
        public MyViewHolder(View itemView) {
            super(itemView);
            mDetails = itemView.findViewById(R.id.detailsBtn);
//            mPartnerPref = itemView.findViewById(R.id.partnerPrefBtn);
            mAlbum = itemView.findViewById(R.id.albumsBtm);
        }
    }
}
