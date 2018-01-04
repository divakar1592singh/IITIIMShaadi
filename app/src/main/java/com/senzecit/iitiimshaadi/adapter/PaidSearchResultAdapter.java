package com.senzecit.iitiimshaadi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.fragment.ResultPaidSearchPartnerFragment;


/**
 * Created by ravi on 13/11/17.
 */

public class PaidSearchResultAdapter extends RecyclerView.Adapter<PaidSearchResultAdapter.MyViewHolder> implements View.OnClickListener {

    boolean detail,partnerPref,album;
    ResultPaidSearchPartnerFragment.ResultPaidSearchPartnerFragmentCommunicator communicator;
    public PaidSearchResultAdapter(ResultPaidSearchPartnerFragment.ResultPaidSearchPartnerFragmentCommunicator communicator){
        this.communicator = communicator;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mDetails.setOnClickListener(this);
        holder.mAlbum.setOnClickListener(this);
        holder.mSearchPartnerIV.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detailsBtn:
                break;
            case R.id.albumsBtm:
                communicator.albums();
                break;
            case R.id.idSearchpartnerIV:
                communicator.details();
                break;

        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        Button mDetails,mPartnerPref,mAlbum;
        ImageView mSearchPartnerIV;
        public MyViewHolder(View itemView) {
            super(itemView);
            mDetails = itemView.findViewById(R.id.detailsBtn);



            mAlbum = itemView.findViewById(R.id.albumsBtm);
            mSearchPartnerIV = itemView.findViewById(R.id.idSearchpartnerIV);
        }
    }
}
