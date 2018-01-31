package com.senzecit.iitiimshaadi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.fragment.ResultPaidSearchPartnerFragment;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * Created by ravi on 13/11/17.
 */

public class PaidSearchResultAdapter extends RecyclerView.Adapter<PaidSearchResultAdapter.MyViewHolder> implements View.OnClickListener {

    boolean detail,partnerPref,album;

    Context mContext;
    List<Query> queryList;
    public PaidSearchResultAdapter(Context mContext, List<Query> queryList){
        this.mContext = mContext;
        this.queryList = queryList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        Button mDetails,mPartnerPref,mAlbum;
        ImageView mSearchPartnerIV;
        TextView mNameTxt, mDietTxt, mAgeTxt, mEmploymentTxt, mCompanyTxt, mHeightTxt, mReligiontxt, mEducationTxt;

        public MyViewHolder(View itemView) {
            super(itemView);

            mNameTxt = itemView.findViewById(R.id.idNameTV);
            mDietTxt = itemView.findViewById(R.id.idDietTV);
            mAgeTxt = itemView.findViewById(R.id.idAgeTV);
            mEmploymentTxt = itemView.findViewById(R.id.idEmploymentTV);
            mCompanyTxt = itemView.findViewById(R.id.idCompanyTV);
            mHeightTxt = itemView.findViewById(R.id.idHeightTV);
            mReligiontxt = itemView.findViewById(R.id.idReligionTV);
            mEducationTxt = itemView.findViewById(R.id.idEducationTV);

            mDetails = itemView.findViewById(R.id.detailsBtn);
            mAlbum = itemView.findViewById(R.id.albumsBtm);
            mSearchPartnerIV = itemView.findViewById(R.id.idSearchpartnerIV);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item_paid,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mNameTxt.setText(queryList.get(position).getName());
        holder.mDietTxt.setText(queryList.get(position).getDiet());
        holder.mAgeTxt.setText(queryList.get(position).getBirthDate());
        holder.mEmploymentTxt.setText(queryList.get(position).getWorkingAs());
        holder.mCompanyTxt.setText(queryList.get(position).getNameOfCompany());
        holder.mHeightTxt.setText(queryList.get(position).getHeight());
        holder.mReligiontxt.setText(queryList.get(position).getReligion());
        holder.mEducationTxt.setText(queryList.get(position).getHighestEducation() );

        holder.mDetails.setOnClickListener(this);
        holder.mAlbum.setOnClickListener(this);
        holder.mSearchPartnerIV.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detailsBtn:
                break;
            case R.id.albumsBtm:
//                communicator.albums();
                break;
            case R.id.idSearchpartnerIV:
//                communicator.details();
                break;

        }
    }



}
