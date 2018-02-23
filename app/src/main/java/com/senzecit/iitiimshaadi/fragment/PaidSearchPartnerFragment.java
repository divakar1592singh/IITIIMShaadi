package com.senzecit.iitiimshaadi.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.common.CountryModel;
import com.senzecit.iitiimshaadi.model.api_response_model.common.city.AllCity;
import com.senzecit.iitiimshaadi.model.api_response_model.common.city.CitiesAccCountryResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.PaidSubscriberResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.Query;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.SubsAdvanceSearchResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.search_partner_subs.PaidSubsAdvanceSearchRequest;
import com.senzecit.iitiimshaadi.model.common.caste.CasteAccReligionResponse;
import com.senzecit.iitiimshaadi.model.common.country.AllCountry;
import com.senzecit.iitiimshaadi.model.common.country.CountryListResponse;
import com.senzecit.iitiimshaadi.sliderView.with_list.SliderDialogListLayoutAdapter;
import com.senzecit.iitiimshaadi.sliderView.with_list.SliderDialogListLayoutModel;
import com.senzecit.iitiimshaadi.sliderView.with_selection.SliderDialogCheckboxLayoutAdapter;
import com.senzecit.iitiimshaadi.sliderView.with_selection.SliderDialogCheckboxLayoutModel;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.Constants;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 13/11/17.
 */

public class PaidSearchPartnerFragment extends Fragment implements View.OnClickListener {
    View view;
    AppPrefs prefs;
    ImageView mSearchByIdIV,mSerchByKeywordIV,mAdvanceSearchIV;
    FrameLayout mframeLayoutSearchById,mframeLayoutSearchByKeyword;
    LinearLayout mlinearLayoutAdvanceSearch;
    boolean idSearch,keywordSearch,advanceSearch = true;
    Button mSearchPartner;

    CountryModel countryResponse;
    List<CountryModel> countryListWithId;

    PaidSearchPartnerFragmentCommunicator communicator;
    RelativeLayout mPartnerCurrentCountry,mPartnerCurrentCity,mPartnerPermLoc, mPartnerReligion,
            mPartnerCaste,mPartnerMotherTongue,mPartnerMaritalStatus,mPartnerMinHeight,
    mPartnerMaxHeight,mPartnerEduOccup,mPartnerAnnIncome;
    LayoutInflater mDialogInflator;
    EditText mAgeMinET, mAgeMaxET, mSearchByIdET, mRandomKeywordET;
    TextView mPartnerCurrentCountryTV, mPartnerCurrentCityIV, mSelectReligionTV, mSelectCastTV,
            mSelectMotherToungeTV, mMaritalStatusTV, mEducationOccupationTV, mAnnualIncomeTV,
            mPartnerPermanentLocarionTV, mSelectHeightMinTV, mSelectHeightMaxTV;


    public void setPaidSearchPartnerFragmentCommunicator(PaidSearchPartnerFragmentCommunicator communicator){
        this.communicator = communicator;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

//        prefs = new AppPrefs(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_paid_search_partner,container,false);
        mDialogInflator = LayoutInflater.from(view.getContext());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prefs = AppController.getInstance().getPrefs();

        init();
        mSearchByIdIV.setOnClickListener(this);
        mSerchByKeywordIV.setOnClickListener(this);
        mAdvanceSearchIV.setOnClickListener(this);
        mSearchPartner.setOnClickListener(this);

        mPartnerCurrentCountry.setOnClickListener(this);
        mPartnerCurrentCity.setOnClickListener(this);
        mPartnerReligion.setOnClickListener(this);
        mPartnerCaste.setOnClickListener(this);
        mPartnerMotherTongue.setOnClickListener(this);
        mPartnerMaritalStatus.setOnClickListener(this);
        mPartnerEduOccup.setOnClickListener(this);
        mPartnerAnnIncome.setOnClickListener(this);

        mPartnerPermLoc.setOnClickListener(this);
        mPartnerMinHeight.setOnClickListener(this);
        mPartnerMaxHeight.setOnClickListener(this);
    }

    private void init(){
        mframeLayoutSearchById = (FrameLayout)view.findViewById(R.id.searchIdFL);
        mframeLayoutSearchByKeyword = (FrameLayout)view.findViewById(R.id.searchKeywordFL);
        mlinearLayoutAdvanceSearch = (LinearLayout) view.findViewById(R.id.searchAdvanceLL);

        mSearchByIdIV = (ImageView)view.findViewById(R.id.searchProfileIdIV);
        mSerchByKeywordIV = (ImageView)view.findViewById(R.id.searchByKeywordIV);
        mAdvanceSearchIV = (ImageView) view.findViewById(R.id.searchAdvanceIV);
        mSearchPartner = (Button) view.findViewById(R.id.searchPartnerBtn);

        mPartnerCurrentCountry = (RelativeLayout) view.findViewById(R.id.idPartnerCurrentCountry);
        mPartnerCurrentCity = (RelativeLayout) view.findViewById(R.id.idPartnerCurrentCity);
        mPartnerReligion = (RelativeLayout) view.findViewById(R.id.idPartnerReligion);
        mPartnerCaste = (RelativeLayout) view.findViewById(R.id.idPartnerCaste);
        mPartnerMotherTongue = (RelativeLayout) view.findViewById(R.id.idPartnerMotherTongue);
        mPartnerMaritalStatus = (RelativeLayout) view.findViewById(R.id.idPartnerMaritalStatus);
        mPartnerEduOccup = (RelativeLayout) view.findViewById(R.id.idPartnerEduOccup);
        mPartnerAnnIncome = (RelativeLayout) view.findViewById(R.id.idPartnerAnnIncome);

        mPartnerPermLoc = (RelativeLayout) view.findViewById(R.id.idPartnerPermLoc);
        mPartnerMinHeight = (RelativeLayout) view.findViewById(R.id.idPartnerMinHeight);
        mPartnerMaxHeight = (RelativeLayout) view.findViewById(R.id.idPartnerMaxHeight);

        mSearchByIdET = (EditText)view.findViewById(R.id.searchByIdET) ;
        mRandomKeywordET = (EditText)view.findViewById(R.id.randomKeywordET) ;
        mAgeMinET = (EditText)view.findViewById(R.id.ageMinET) ;
        mAgeMaxET = (EditText)view.findViewById(R.id.ageMaxET) ;

        mPartnerCurrentCountryTV = (TextView)view.findViewById(R.id.partnerCurrentCountryTV) ;
        mPartnerCurrentCityIV = (TextView)view.findViewById(R.id.partnerCurrentCityTV) ;
        mSelectReligionTV = (TextView)view.findViewById(R.id.selectReligionTV) ;
        mSelectCastTV = (TextView)view.findViewById(R.id.selectCastTV) ;
        mSelectMotherToungeTV = (TextView)view.findViewById(R.id.selectMotherToungeTV) ;
        mMaritalStatusTV = (TextView)view.findViewById(R.id.maritalStatusTV) ;
        mEducationOccupationTV = (TextView)view.findViewById(R.id.educationOccupationTV) ;
        mAnnualIncomeTV = (TextView)view.findViewById(R.id.annualIncomeTV) ;

        mPartnerPermanentLocarionTV = (TextView)view.findViewById(R.id.partnerPermanentLocarionTV) ;
        mSelectHeightMinTV = (TextView)view.findViewById(R.id.selectHeightMinTV) ;
        mSelectHeightMaxTV = (TextView)view.findViewById(R.id.selectHeightMaxTV) ;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchProfileIdIV:
                if(keywordSearch){
                    mframeLayoutSearchById.setVisibility(View.GONE);
                    keywordSearch = false;
                }else{
                    mframeLayoutSearchById.setVisibility(View.VISIBLE);
                    keywordSearch = true;
                }
                break;
            case R.id.searchByKeywordIV:
                if(idSearch){
                    mframeLayoutSearchByKeyword.setVisibility(View.GONE);
                    idSearch = false;
                }else{
                    mframeLayoutSearchByKeyword.setVisibility(View.VISIBLE);
                    idSearch = true;
                }
                break;
            case R.id.searchAdvanceIV:
                if(advanceSearch){
                    mlinearLayoutAdvanceSearch.setVisibility(View.GONE);
                    advanceSearch = false;
                }else{
                    mlinearLayoutAdvanceSearch.setVisibility(View.VISIBLE);
                    advanceSearch = true;
                }
                break;
            case R.id.searchPartnerBtn:
//                communicator.saveAndSearchPaidPartnerByAdvance();
//                callWebServiceForSubsAdvanceSearch();
//                callWebServiceForSubsKeywordSearch();
//                callWebServiceForSubsIDSearch();
                showPartnerSelection(view);
                break;

            case R.id.idPartnerCurrentCountry:
                showCountry(mPartnerCurrentCountryTV);
                break;
            case R.id.idPartnerCurrentCity:
                showCity(mPartnerCurrentCityIV);
                break;
            case R.id.idPartnerReligion:
                showReligion(mSelectReligionTV);
                break;
            case R.id.idPartnerCaste:
                showCaste(mSelectCastTV);
                break;
            case R.id.idPartnerMotherTongue:
                showMotherTongue(mSelectMotherToungeTV);
                break;
            case R.id.idPartnerMaritalStatus:
                showMaritalStatus(mMaritalStatusTV);
                break;
            case R.id.idPartnerEduOccup:
                showEducation(mEducationOccupationTV);
                break;
            case R.id.idPartnerAnnIncome:
                showAnnualIncome(mAnnualIncomeTV);
                break;
            case R.id.idPartnerPermLoc:
                showLocation(mPartnerPermanentLocarionTV);
                break;
            case R.id.idPartnerMinHeight:
                showHeight(mSelectHeightMinTV);
                break;
            case R.id.idPartnerMaxHeight:
                showHeight(mSelectHeightMaxTV);
                break;
        }
    }

    public void showPartnerSelection(View view1){
        PopupMenu popupMenu = new PopupMenu(getActivity(), view1);
        popupMenu.inflate(R.menu.menu_partner);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){

                    case R.id.idSearchById:
                        checkValidation(1);
                        break;
                    case R.id.idSearchByKeyword:
                        checkValidation(2);
                        break;
                    case R.id.idAdvanceSearch:
                        checkValidation(3);
                        break;

                }
                return false;
            }
        });
        popupMenu.show();
    }
    public void checkValidation(int point){

        String searchById = mSearchByIdET.getText().toString() ;
        String searchByKeyword = mRandomKeywordET.getText().toString() ;

        if(point == 1){
            if(!TextUtils.isEmpty(searchById)){
                callWebServiceForSubsIDSearch();
            }else {
                AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", "Search By Id can't Empty");
            }
        }else if(point == 2){
            if(!TextUtils.isEmpty(searchByKeyword)){
                callWebServiceForSubsKeywordSearch();
            }else {
                AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", "Search By Keyword can't Empty");
            }
        }else if(point == 3){
            callWebServiceForSubsAdvanceSearch();
        }
    }
    /** API -  */
    /** Search By ID */
    public void callWebServiceForSubsIDSearch(){

//        String token = Constants.Token_Paid;
//        String token = "42a6259d9ae09e7fde77c74bbf2a9a48";

        String token = prefs.getString(Constants.LOGGED_TOKEN);;
        String searchID = mSearchByIdET.getText().toString() ;

        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(getActivity());
        Call<PaidSubscriberResponse> call = apiInterface.idSearchPaid1(token, searchID);
        call.enqueue(new Callback<PaidSubscriberResponse>() {
            @Override
            public void onResponse(Call<PaidSubscriberResponse> call, Response<PaidSubscriberResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){
                        if(response.body().getQuery().size() > 0){
                            List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList = response.body().getQuery();

                            communicator.saveAndSearchPaidPartnerByID(queryList, searchID);

                        }
                    }else {
                        AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Search Partner", "Opps");
                    }

                }
            }

            @Override
            public void onFailure(Call<PaidSubscriberResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }

    /** Search By Keyword */
    public void callWebServiceForSubsKeywordSearch(){

//        String token = "42a6259d9ae09e7fde77c74bbf2a9a48";
        String token = prefs.getString(Constants.LOGGED_TOKEN);;
        String keyword = mRandomKeywordET.getText().toString() ;

        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(getActivity());
        Call<SubsAdvanceSearchResponse> call = apiInterface.keywordSearchPaid(token, keyword);
        call.enqueue(new Callback<SubsAdvanceSearchResponse>() {
            @Override
            public void onResponse(Call<SubsAdvanceSearchResponse> call, Response<SubsAdvanceSearchResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){
                        if(response.body().getQuery().size() > 0){
                            List<Query> queryList = response.body().getQuery();
//                            System.out.print(profileList);
                            communicator.saveAndSearchPaidPartnerByKeyword(queryList, keyword);

                        }
                    }else {
                        AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Search Partner", "Opps");
                    }

                }
            }

            @Override
            public void onFailure(Call<SubsAdvanceSearchResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }
    /** Advance Search */
    public void callWebServiceForSubsAdvanceSearch(){

        List<String> profileList =new ArrayList<>();

//        String token = "42a6259d9ae09e7fde77c74bbf2a9a48";;
        String token = prefs.getString(Constants.LOGGED_TOKEN);

        String minage = mAgeMinET.getText().toString() ;
        String maxage = mAgeMaxET.getText().toString() ;
        String country = mPartnerCurrentCountryTV.getText().toString() ;
        String city = mPartnerCurrentCityIV.getText().toString() ;
        String religion = mSelectReligionTV.getText().toString() ;
        String caste = mSelectCastTV.getText().toString() ;
        String mother_tounge = mSelectMotherToungeTV.getText().toString() ;
        String marital_status = mMaritalStatusTV.getText().toString() ;
        String course = mEducationOccupationTV.getText().toString() ;
        String annual_income = mAnnualIncomeTV.getText().toString() ;


        String sPartnerLoc = mPartnerPermanentLocarionTV.getText().toString() ;
        String sMinHeight = mSelectHeightMinTV.getText().toString() ;
        String sMaxHeight = mSelectHeightMaxTV.getText().toString() ;

        profileList.add(minage);profileList.add(maxage);profileList.add(country);
        profileList.add(city);profileList.add(religion);profileList.add(caste);
        profileList.add(mother_tounge);profileList.add(marital_status);
        profileList.add(course);profileList.add(annual_income);
        profileList.add(sPartnerLoc);profileList.add(sMinHeight);profileList.add(sMaxHeight);

        PaidSubsAdvanceSearchRequest searchRequest = new PaidSubsAdvanceSearchRequest();
        searchRequest.token = token;
        searchRequest.minage = minage;
        searchRequest.maxage = maxage;
        searchRequest.country = country;
        searchRequest.city = city;
        searchRequest.religion = religion;
        searchRequest.caste = caste;
        searchRequest.mother_tounge = mother_tounge;
        searchRequest.marital_status = marital_status;
        searchRequest.course = course;
        searchRequest.annual_income = annual_income;

        searchRequest.min_height = sMinHeight;
        searchRequest.max_height = sMaxHeight;

        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        ProgressClass.getProgressInstance().showDialog(getActivity());
        Call<SubsAdvanceSearchResponse> call = apiInterface.advanceSearchPaid(searchRequest);
        call.enqueue(new Callback<SubsAdvanceSearchResponse>() {
            @Override
            public void onResponse(Call<SubsAdvanceSearchResponse> call, Response<SubsAdvanceSearchResponse> response) {
                ProgressClass.getProgressInstance().stopProgress();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().getSuccess().toString().equalsIgnoreCase("success")){
                        if(response.body().getQuery().size() > 0){
                            List<Query> queryList = response.body().getQuery();
                            System.out.print(profileList);
                            communicator.saveAndSearchPaidPartnerByAdvance(queryList, profileList);

                        }
                    }else {
                        AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Search Partner", "Opps");
                    }

                }
            }

            @Override
            public void onFailure(Call<SubsAdvanceSearchResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }


    public Vector<Dialog> dialogs = new Vector<Dialog>();
    private void showDialog(List<String> dataList, final TextView txtListChild) {

        int d_width = 100;
        int d_height = 50;
        final ArrayList<SliderDialogListLayoutModel> models = new ArrayList<SliderDialogListLayoutModel>();
        if(dataList.size()>0){
            for (int i = 0; i < dataList.size(); i++) {
                SliderDialogListLayoutModel model = new SliderDialogListLayoutModel();
                model.setName(dataList.get(i));
                models.add(model);
            }
        }else {
            for (int i = 0; i < 20; i++) {
                SliderDialogListLayoutModel model = new SliderDialogListLayoutModel();
                model.setName("senzecit " + i);
                models.add(model);
            }
        }

        final Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);//,R.style.CustomDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

//        View view = getLayoutInflater().inflate(R.layout.slider_dialog_list_layout, null);
        View view = mDialogInflator.inflate(R.layout.slider_dialog_list_layout, null);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.custom_list);
//		final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        ((Button) view.findViewById(R.id.button_done)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final SliderDialogListLayoutAdapter clad1 = new SliderDialogListLayoutAdapter(getContext(), models);
        recyclerView.setAdapter(clad1);

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialogs.add(dialog);
        dialog.show();

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Window window = dialog.getWindow();
        window.setGravity(Gravity.RIGHT);
        window.setLayout(width - d_width, height - d_height);

        clad1.setOnItemClickListener(new SliderDialogListLayoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String id) {
                int positionInList = position % models.size();

                txtListChild.setText(models.get(positionInList).getName());
                dialog.dismiss();
            }
        });

    }

    public Vector<Dialog> selectableDialogs = new Vector<Dialog>();
    private void showSelectableDialog(List<String> dataList, final TextView txtListChild)
    {
        int d_width = 100;
        int d_height = 50;
        final StringBuilder selectedQualification = new StringBuilder();
        final View[] parentListView = {null};
        final ArrayList<SliderDialogCheckboxLayoutModel> models = new ArrayList<SliderDialogCheckboxLayoutModel>();
        if(dataList.size()>0){
            for (int i = 0; i < dataList.size(); i++) {
                SliderDialogCheckboxLayoutModel model = new SliderDialogCheckboxLayoutModel();
                model.setName(dataList.get(i));
                models.add(model);
            }
        }else {
            for (int i = 0; i < 20; i++) {
                SliderDialogCheckboxLayoutModel model = new SliderDialogCheckboxLayoutModel();
                model.setName("senzecit " + i);
                models.add(model);
            }
        }

        final Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);//,R.style.CustomDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        View view = mDialogInflator.inflate(R.layout.slider_dialog_checkbox_layout, null);

        final ListView listView = (ListView) view.findViewById(R.id.custom_list);
        Button doneBtn = (Button)view.findViewById(R.id.button_done);


        SliderDialogCheckboxLayoutAdapter clad1 = new SliderDialogCheckboxLayoutAdapter(getContext(), models);
        listView.setAdapter(clad1);


        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        selectableDialogs.add(dialog);
        dialog.show();

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Window window = dialog.getWindow();
        window.setGravity(Gravity.RIGHT);
        window.setLayout(width - d_width, height - d_height);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < models.size(); i++) {

                    parentListView[0] = getViewByPosition(i, listView);
                    CheckBox checkBox = (CheckBox)parentListView[0].findViewById(R.id.idCheckbox);
                    TextView textView = (TextView)parentListView[0].findViewById(R.id.idText);

                    if (checkBox.isChecked()){

                        selectedQualification.append(textView.getText().toString()+", ");
                    }

                }
                txtListChild.setText(selectedQualification.toString());
                dialog.dismiss();
//             Toast.makeText(MainActivity.this, "Data is : "+slideText.getText(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


    public void showReligion(TextView textView){
        List<String> list = new ArrayList<>();
        list.add("Hindu");
        list.add("Muslim");
        list.add("Christian");
        list.add("Sikh");
        list.add("Parsi");
        list.add("Jain");
        list.add("Buddhist");
        list.add("Jewish");
        list.add("Other");

        showDialog(list, textView);
    }

    public void showMotherTongue(TextView textView){
        List<String> list = new ArrayList<>();
        list.add("Assamese");
        list.add("Bengali");
        list.add("English");
        list.add("Gujarati");
        list.add("Hindi");

        showDialog(list, textView);
    }
    public void showMaritalStatus(TextView textView){
        List<String> list = new ArrayList<>();
        list.add("Never Married");
        list.add("Divorced");
        list.add("Divorced Awaited");
        list.add("Widow/Widower");
        list.add("Any");

        showDialog(list, textView);
    }
    public void showEducation(TextView textView){
        List<String> list = new ArrayList<>();
        list.add("Any");
        list.add("Actuary");
        list.add("Hotel Management");
        list.add("Management");
        list.add("Engineering/Architecture");
        list.add("Medical");
        list.add("CA/CS/ICWA/CFA");
        list.add("Law");
        list.add("Design/Fashion Design");
        list.add("Government Officer");
        list.add("Social Work (Masters)");
        list.add("Media Communication (Masters)");
        list.add("Performing and Fine Arts");
        list.add("Masters");
        list.add("Research (Ph.D/FPM)");
        list.add("Others");

        showSelectableDialog(list, textView);
    }
    public void showAnnualIncome(TextView textView){
        List<String> list = new ArrayList<>();
        list.add("less than 10 LPA");
        list.add("11-20 LPA");
        list.add("21-30 LPA");
        list.add("31-50 LPA");
        list.add("51-75 LPA");
        list.add("76-100 LPA");
        list.add("More than 100 LPA");
        list.add("Not Disclosed");

        showDialog(list, textView);
    }

    public void showHeight(TextView textView){
        List<String> list = new ArrayList<>();

        list.add("4 ft 5in (134 cm)");
        list.add("4 ft 6in (137 cm)");
        list.add("4 ft 7in (139 cm)");
        list.add("4 ft 8in (142 cm)");
        list.add("4 ft 9in (144 cm)");
        list.add("4 ft 10in (147 cm)");
        list.add("4 ft 11in (149 cm)");
        list.add("5 ft 0in (152 cm)");
        list.add("5 ft 1in (154 cm)");
        list.add("5 ft 2in (157 cm)");
        list.add("5 ft 3in (160 cm)");
        list.add("5 ft 4in (162 cm)");
        list.add("5 ft 5in (165 cm)");
        list.add("5 ft 6in (167 cm)");
        list.add("5 ft 7in (170 cm)");
        list.add("5 ft 8in (172 cm)");
        list.add("5 ft 9in (175 cm)");
        list.add("5 ft 10in (177 cm)");
        list.add("5 ft 11in (180 cm)");
        list.add("6 ft 0in (182 cm)");
        list.add("6 ft 1in (185 cm)");
        list.add("6 ft 2in (187 cm)");
        list.add("6 ft 3in (190 cm)");
        list.add("6 ft 4in (193 cm)");
        list.add("6 ft 5in (195 cm)");
        list.add("6 ft 6in (198 cm)");
        list.add("6 ft 7in (200 cm)");
        list.add("6 ft 8in (203 cm)");

        showDialog(list, textView);
    }

    private void showCountry(final TextView textView){

        countryListWithId = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        countryList.clear();

//        AppPrefs prefs = AppController.getInstance().getPrefs();
        String token = prefs.getString(Constants.LOGGED_TOKEN);
//        String token = Constants.Token_Paid;

        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<CountryListResponse> call = apiInterface.countryList(token);
        ProgressClass.getProgressInstance().showDialog(getActivity());
        call.enqueue(new Callback<CountryListResponse>() {
            @Override
            public void onResponse(Call<CountryListResponse> call, Response<CountryListResponse> response) {
                if (response.isSuccessful()) {
                    ProgressClass.getProgressInstance().stopProgress();
                    List<AllCountry> rawCountryList = response.body().getAllCountries();
                    for(int i = 0; i<rawCountryList.size(); i++){
                        if(rawCountryList.get(i).getName() != null){
                            countryList.add(rawCountryList.get(i).getName());
                            countryResponse = new CountryModel(String.valueOf(rawCountryList.get(i).getOldValue()), rawCountryList.get(i).getName());
                            countryListWithId.add(countryResponse);
                        }
                    }
                    showDialog(countryList, textView);
                }
            }

            @Override
            public void onFailure(Call<CountryListResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showCity(final TextView textView){

        final List<String> cityList = new ArrayList<>();
        cityList.clear();
        String countryId = null;

        String country = mPartnerCurrentCountryTV.getText().toString() ;
        for(int i = 0; i < countryListWithId.size(); i++){
            if(countryListWithId.get(i).getCountryName().equalsIgnoreCase(country)){
                countryId = countryListWithId.get(i).getCountryId();
            }
        }
        System.out.println(countryId);

//        String countryId = "1151";

        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<CitiesAccCountryResponse> call = apiInterface.cityList(countryId);
        ProgressClass.getProgressInstance().showDialog(getActivity());
        call.enqueue(new Callback<CitiesAccCountryResponse>() {
            @Override
            public void onResponse(Call<CitiesAccCountryResponse> call, Response<CitiesAccCountryResponse> response) {
                if (response.isSuccessful()) {
                    ProgressClass.getProgressInstance().stopProgress();
                    List<AllCity> rawCityList = response.body().getAllCities();
                    for(int i = 0; i<rawCityList.size(); i++){
                        if(rawCityList.get(i).getName() != null){
                            cityList.add(rawCityList.get(i).getName());
                        }
                    }

                    showDialog(cityList, textView);
                }
            }

            @Override
            public void onFailure(Call<CitiesAccCountryResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showLocation(final TextView textView){

        final List<String> cityList = new ArrayList<>();
        cityList.clear();
        String countryId = null;

        String country = mPartnerCurrentCountryTV.getText().toString() ;
        for(int i = 0; i < countryListWithId.size(); i++){
            if(countryListWithId.get(i).getCountryName().equalsIgnoreCase(country)){
                countryId = countryListWithId.get(i).getCountryId();
            }
        }
        System.out.println(countryId);

//        String countryId = "1151";

        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<CitiesAccCountryResponse> call = apiInterface.cityList(countryId);
        ProgressClass.getProgressInstance().showDialog(getActivity());
        call.enqueue(new Callback<CitiesAccCountryResponse>() {
            @Override
            public void onResponse(Call<CitiesAccCountryResponse> call, Response<CitiesAccCountryResponse> response) {
                if (response.isSuccessful()) {
                    ProgressClass.getProgressInstance().stopProgress();
                    List<AllCity> rawCityList = response.body().getAllCities();
                    for(int i = 0; i<rawCityList.size(); i++){
                        if(rawCityList.get(i).getName() != null){
                            cityList.add(rawCityList.get(i).getName());
                        }
                    }

                    showDialog(cityList, textView);
                }
            }

            @Override
            public void onFailure(Call<CitiesAccCountryResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showCaste(final TextView textView){

//        String token = Constants.Token_Paid;
        String token = prefs.getString(Constants.LOGGED_USERID);
        String caste = mSelectReligionTV.getText().toString() ;

        APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL).create(APIInterface.class);
        Call<CasteAccReligionResponse> call = apiInterface.casteList(token, caste);
        ProgressClass.getProgressInstance().showDialog(getActivity());
        call.enqueue(new Callback<CasteAccReligionResponse>() {
            @Override
            public void onResponse(Call<CasteAccReligionResponse> call, Response<CasteAccReligionResponse> response) {
                if (response.isSuccessful()) {
                    ProgressClass.getProgressInstance().stopProgress();
                    List<String> casteList = response.body().getAllCastes();

                    showDialog(casteList, textView);
                }
            }

            @Override
            public void onFailure(Call<CasteAccReligionResponse> call, Throwable t) {
                call.cancel();
                ProgressClass.getProgressInstance().stopProgress();
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface PaidSearchPartnerFragmentCommunicator{
        void saveAndSearchPaidPartnerByID(List<com.senzecit.iitiimshaadi.model.api_response_model.paid_subscriber.Query> queryList, String userid);
        void saveAndSearchPaidPartnerByKeyword(List<Query> queryList, String keyword);
        void saveAndSearchPaidPartnerByAdvance(List<Query> queryList, List<String> profileList);

    }

}
