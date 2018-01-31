package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query;

import java.util.List;

/**
 * Created by ravi on 14/11/17.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder> implements View.OnClickListener {

    Context mContext;
    List<Query> queryList;
    public SearchResultAdapter(Context mContext, List<Query> queryList){
        this.mContext = mContext;
        this.queryList = queryList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mDietTxt, mEmploymentTxt, mCompanyTxt, mHeightTxt, mReligiontxt, mEducationTxt;
        Button mDetails,mPartnerPref,mAlbum;
        public MyViewHolder(View itemView) {
            super(itemView);

            mDietTxt = itemView.findViewById(R.id.idDietTV);
            mEmploymentTxt = itemView.findViewById(R.id.idEmploymentTV);
            mCompanyTxt = itemView.findViewById(R.id.idCompanyTV);
            mHeightTxt = itemView.findViewById(R.id.idHeightTV);
            mReligiontxt = itemView.findViewById(R.id.idReligionTV);
            mEducationTxt = itemView.findViewById(R.id.idEducationTV);

            mDetails = itemView.findViewById(R.id.detailsBtn);
//            mPartnerPref = itemView.findViewById(R.id.partnerPrefBtn);
            mAlbum = itemView.findViewById(R.id.albumsBtm);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item,parent,false);
        return new SearchResultAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mDietTxt.setText(queryList.get(position).getDiet());
        holder.mEmploymentTxt.setText(queryList.get(position).getWorkingAs());
        holder.mCompanyTxt.setText(queryList.get(position).getNameOfCompany());
        holder.mHeightTxt.setText(queryList.get(position).getHeight());
        holder.mReligiontxt.setText(queryList.get(position).getReligion());
        holder.mEducationTxt.setText(queryList.get(position).getHighestEducation() );

        holder.mDetails.setOnClickListener(this);
//        holder.mPartnerPref.setOnClickListener(this);
        holder.mAlbum.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return queryList.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detailsBtn:
                break;
            case R.id.partnerPrefBtn:
                break;
            case R.id.albumsBtm:
                break;

        }
    }

}
