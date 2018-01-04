package com.senzecit.iitiimshaadi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.adapter.ExpandableListViewAdapter;
import com.senzecit.iitiimshaadi.adapter.ExpandableListViewAdapterSearchPartner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ravi on 13/11/17.
 */

public class PaidSearchResultDetailFragment extends Fragment implements View.OnClickListener {
    View view;
    List<String> listDataHeader,listDataHeaderPartner;
    HashMap<String, List<String>> listDataChild,listDataChildPartner;
    ScrollView mScrollView;
    ExpandableListView expListView,expListViewPartner;
    ExpandableListViewAdapterSearchPartner listAdapter;
    Button mDetails,mPartnerPref,mAlbum;

    TextView mName,mDOB,mMaritalStatus,mReligion,mCounty,mEducations;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_paid_search_result_detail,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        mScrollView.smoothScrollTo(0,0);
        mScrollView.setFocusableInTouchMode(true);
        mScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        prepareListData();
        listAdapter = new ExpandableListViewAdapterSearchPartner(getContext(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        prepareListDataPartner();
        listAdapter = new ExpandableListViewAdapterSearchPartner(getContext(), listDataHeaderPartner, listDataChildPartner);
        expListViewPartner.setAdapter(listAdapter);

        mDetails.setOnClickListener(this);
        mPartnerPref.setOnClickListener(this);
        mAlbum.setOnClickListener(this);
    }

    private void init(){

        expListView = (ExpandableListView)view.findViewById(R.id.expandableLV);
        expListViewPartner = (ExpandableListView)view.findViewById(R.id.expandablePartnerLV);
        mDetails = view.findViewById(R.id.detailsBtn);
        mPartnerPref = view.findViewById(R.id.partnerPrefBtn);
        mAlbum = view.findViewById(R.id.albumsBtm);
        mScrollView = view.findViewById(R.id.idScrollView);

        mName = view.findViewById(R.id.nameTV);
        mDOB = view.findViewById(R.id.dobTV);
        mMaritalStatus = view.findViewById(R.id.maritalStatusTV);
        mReligion = view.findViewById(R.id.religionTV);
        mCounty = view.findViewById(R.id.countryTV);
        mEducations = view.findViewById(R.id.educationTV);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Basics & Lifestyle");
        listDataHeader.add("Religious Background");
        listDataHeader.add("Contact Details");
        listDataHeader.add("Family Details");
        listDataHeader.add("Education & Career");
        listDataHeader.add("About Me");
        listDataHeader.add("Acquaintances");

        // Adding child data
        List<String> basicsLifestyle = new ArrayList<String>();
        basicsLifestyle.add("Name");
        basicsLifestyle.add("Profile Created For");
        basicsLifestyle.add("Age");
        basicsLifestyle.add("Diet");
        basicsLifestyle.add("Date Of Birth");
        basicsLifestyle.add("Marital Status");
        basicsLifestyle.add("Drink");
        basicsLifestyle.add("Smoke");
        basicsLifestyle.add("Height");
        basicsLifestyle.add("Interests");

        List<String> religiousBackgroung = new ArrayList<String>();
        religiousBackgroung.add("Religion");
        religiousBackgroung.add("Caste");
        religiousBackgroung.add("Mother Tonge");


        List<String> contactDetail = new ArrayList<String>();
        contactDetail.add("Phone Number");
        contactDetail.add("Alternate Number");
        contactDetail.add("Permanent Address");
        contactDetail.add("Permanent City");
        contactDetail.add("Permanent State");
        contactDetail.add("Permanent Country");
        contactDetail.add("Zip Code");
        contactDetail.add("Current Address");
        contactDetail.add("Current City");
        contactDetail.add("Current State");
        contactDetail.add("Current Country");
        contactDetail.add("Zip Code");

        List<String> familyDetails = new ArrayList<String>();
        familyDetails.add("Father's Name");
        familyDetails.add("Father's Occupation");
        familyDetails.add("Mother's Name");
        familyDetails.add("Mother's Occupation");
        familyDetails.add("Details on Sisters");
        familyDetails.add("Details on Brothers");

        List<String> educationCareer = new ArrayList<String>();
        educationCareer.add("Schooling");
        educationCareer.add("Schooling Year");
        educationCareer.add("Graduation");
        educationCareer.add("Graduation College");
        educationCareer.add("Graduation Year");
        educationCareer.add("Post Graduation");
        educationCareer.add("Post Graduation College");
        educationCareer.add("Post Graduation Year");
        educationCareer.add("Highest Education");
        educationCareer.add("Working With");
        educationCareer.add("Working As");
        educationCareer.add("Annual Income");
        educationCareer.add("Work Location");
        educationCareer.add("LinkdIn Url");

        List<String> aboutMe = new ArrayList<String>();
        aboutMe.add("Write someting about you");

        List<String> acquiantances = new ArrayList<String>();
        acquiantances.add("Name");
        acquiantances.add("Remark");


        listDataChild.put(listDataHeader.get(0), basicsLifestyle); // Header, Child data
        listDataChild.put(listDataHeader.get(1), religiousBackgroung);
        listDataChild.put(listDataHeader.get(2), contactDetail);
        listDataChild.put(listDataHeader.get(3), familyDetails);
        listDataChild.put(listDataHeader.get(4), educationCareer);
        listDataChild.put(listDataHeader.get(5), aboutMe);
        listDataChild.put(listDataHeader.get(6), acquiantances);

    }



    private void prepareListDataPartner() {
        listDataHeaderPartner = new ArrayList<String>();
        listDataChildPartner = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeaderPartner.add("Basics & Lifestyle");
        listDataHeaderPartner.add("Religious Background");
        listDataHeaderPartner.add("Education & Career");
        listDataHeaderPartner.add("Groom");

        // Adding child data
        List<String> basicsLifestyle = new ArrayList<String>();
        basicsLifestyle.add("Age");
        basicsLifestyle.add("Min Height");
        basicsLifestyle.add("Max Height");
        basicsLifestyle.add("Marital Status");

        List<String> religiousBackgroung = new ArrayList<String>();
        religiousBackgroung.add("Preferred Religion");
        religiousBackgroung.add("Preferred Caste");
        religiousBackgroung.add("Preferred Country");


        List<String> educationCareer = new ArrayList<String>();
        educationCareer.add("Preferred Education");

        List<String> aboutMe = new ArrayList<String>();
        aboutMe.add("Choice of Groom");

        listDataChildPartner.put(listDataHeaderPartner.get(0), basicsLifestyle); // Header, Child data
        listDataChildPartner.put(listDataHeaderPartner.get(1), religiousBackgroung);
        listDataChildPartner.put(listDataHeaderPartner.get(2), educationCareer);
        listDataChildPartner.put(listDataHeaderPartner.get(3), aboutMe);

    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.detailsBtn:
               mDetails.setBackground(getResources().getDrawable(R.drawable.button_shape_profile_select));
               mDetails.setTextColor(getResources().getColor(R.color.colorWhite));

               mPartnerPref.setBackground(getResources().getDrawable(R.drawable.button_shape_profile_unselect));
               mPartnerPref.setTextColor(getResources().getColor(R.color.colorMaroon));

               expListView.setVisibility(View.VISIBLE);
               expListViewPartner.setVisibility(View.GONE);
               break;
           case R.id.partnerPrefBtn:
               mPartnerPref.setBackground(getResources().getDrawable(R.drawable.button_shape_profile_select));
               mPartnerPref.setTextColor(getResources().getColor(R.color.colorWhite));

               mDetails.setBackground(getResources().getDrawable(R.drawable.button_shape_profile_unselect));
               mDetails.setTextColor(getResources().getColor(R.color.colorMaroon));

               expListView.setVisibility(View.GONE);
               expListViewPartner.setVisibility(View.VISIBLE);
               break;
           case R.id.albumsBtm:
               break;
       }
    }
}
