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
import com.senzecit.iitiimshaadi.adapter.SearchResultAdapter;


/**
 * Created by ravi on 14/11/17.
 */

public class ResultSearchPartnerFragment extends Fragment {
    View view;
    RecyclerView mSearchResultRecyclerView;
    ResultSearchPartnerFragmentCommunicator communicator;
    TextView mAgeMin,mAgeMax,mCountry,mCity,mReligion,mMotherTongue,mmaritalStatus,mIncome;

    public  void setResultSearchPartnerFragmentCommunicator(ResultSearchPartnerFragmentCommunicator communicator){
        this.communicator = communicator;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result_search_partner,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        SearchResultAdapter adapter = new SearchResultAdapter(communicator);
        mSearchResultRecyclerView.setAdapter(adapter);
    }
    private void init(){
        mSearchResultRecyclerView = (RecyclerView) view.findViewById(R.id.partnerSearchResulttRV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mSearchResultRecyclerView.setLayoutManager(layoutManager);

        mAgeMin = view.findViewById(R.id.minAgeTV);
        mAgeMax = view.findViewById(R.id.maxAgeTV);
        mCountry = view.findViewById(R.id.countryTV);
        mCity = view.findViewById(R.id.cityTV);
        mReligion = view.findViewById(R.id.religionTV);
        mMotherTongue = view.findViewById(R.id.motherTougeTV);
        mmaritalStatus = view.findViewById(R.id.maritalStatusTV);
        mIncome = view.findViewById(R.id.incomeTV);
    }

    public interface ResultSearchPartnerFragmentCommunicator{
        void details();
        void partnerPref();
        void albums();
    }
}
