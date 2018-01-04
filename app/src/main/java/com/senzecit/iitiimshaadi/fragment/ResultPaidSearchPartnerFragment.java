package com.senzecit.iitiimshaadi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.PaidSearchResultAdapter;

/**
 * Created by ravi on 13/11/17.
 */

public class ResultPaidSearchPartnerFragment extends Fragment {

    View view;
    RecyclerView mSearchResultRecyclerView;
    ResultPaidSearchPartnerFragmentCommunicator communicator;
    TextView mAgeMin,mAgeMax,mCountry,mCity,mReligion,mCast,mMotherTongue,mmaritalStatus,mIncome,mHeightMin,mHeightmax;

    public void setResultPaidSearchPartnerFragmentCommunicator(ResultPaidSearchPartnerFragmentCommunicator communicator){
        this.communicator = communicator;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result_paid_search_partner,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        PaidSearchResultAdapter adapter = new PaidSearchResultAdapter(communicator);
        mSearchResultRecyclerView.setAdapter(adapter);
    }
    private void init(){
        mAgeMin = view.findViewById(R.id.minAgeTV);
        mAgeMax = view.findViewById(R.id.maxAgeTV);
        mCountry = view.findViewById(R.id.countryTV);
        mCity = view.findViewById(R.id.cityTV);
        mReligion = view.findViewById(R.id.religionTV);
        mCast = view.findViewById(R.id.castTV);
        mMotherTongue = view.findViewById(R.id.motherTougeTV);
        mmaritalStatus = view.findViewById(R.id.maritalStatusTV);
        mIncome = view.findViewById(R.id.incomeTV);
        mHeightMin = view.findViewById(R.id.heightMinTV);
        mHeightmax = view.findViewById(R.id.heightMaxTV);

        mSearchResultRecyclerView = (RecyclerView) view.findViewById(R.id.partnerSearchResulttRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mSearchResultRecyclerView.setLayoutManager(layoutManager);
    }

    public interface ResultPaidSearchPartnerFragmentCommunicator{
        void details();
        void partnerPref();
        void albums();
    }
}
