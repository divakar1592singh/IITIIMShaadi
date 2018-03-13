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
import android.util.Log;
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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.common.CountryModel;
import com.senzecit.iitiimshaadi.model.api_response_model.common.city.AllCity;
import com.senzecit.iitiimshaadi.model.api_response_model.common.city.CitiesAccCountryResponse;
import com.senzecit.iitiimshaadi.sliderView.with_list.SliderDialogListLayoutAdapter;
import com.senzecit.iitiimshaadi.sliderView.with_list.SliderDialogListLayoutModel;
import com.senzecit.iitiimshaadi.sliderView.with_selection.SliderDialogCheckboxLayoutAdapter;
import com.senzecit.iitiimshaadi.sliderView.with_selection.SliderDialogCheckboxLayoutModel;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 13/11/17.
 */

public class PaidSearchPartnerFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "PaidSearchPartnerFrgmnt";
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
//                callWebServiceForSubsIDSearch();
                callIDSearch();

            }else {
                AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", "Search By Id can't Empty");
            }
        }else if(point == 2){
            if(!TextUtils.isEmpty(searchByKeyword)){
//                callWebServiceForSubsKeywordSearch();
                    callKeywordSearch();
            }else {
                AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", "Search By Keyword can't Empty");
            }
        }else if(point == 3){
//            callWebServiceForSubsAdvanceSearch();
            callAdvanceSearch();
        }
    }
    /** API -  */
//    /** Search By ID */
    public void callIDSearch(){

        prefs.putString(CONSTANTS.SEARCH_TYPE, "id");

        String searchID = mSearchByIdET.getText().toString() ;
        prefs.putString(CONSTANTS.SEARCH_ID, searchID);
//        communicator.saveAndSearchPaidPartnerByID(queryList, searchID);
        communicator.saveAndSearchPaidPartner();
    }
    /** Search By Keyword */
    public void callKeywordSearch(){

        prefs.putString(CONSTANTS.SEARCH_TYPE, "keyword");

        String keyword = mRandomKeywordET.getText().toString() ;
        prefs.putString(CONSTANTS.SEARCH_KEYWORD, keyword);
//        communicator.saveAndSearchPaidPartnerByKeyword(queryList, keyword);
        communicator.saveAndSearchPaidPartner();
    }
    /** Advance Search */
    public void callAdvanceSearch(){

        prefs.putString(CONSTANTS.SEARCH_TYPE, "advance");

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

        prefs.putString(CONSTANTS.MIN_AGE, minage);
        prefs.putString(CONSTANTS.MAX_AGE, maxage);
        prefs.putString(CONSTANTS.COUNTRY, country);
        prefs.putString(CONSTANTS.CITY, city);
        prefs.putString(CONSTANTS.RELIGION, religion);
        prefs.putString(CONSTANTS.CASTE, caste);
        prefs.putString(CONSTANTS.MOTHER_TONGUE, mother_tounge);
        prefs.putString(CONSTANTS.MARITAL_STATUS, marital_status);
        prefs.putString(CONSTANTS.COURSE, course);
        prefs.putString(CONSTANTS.ANNUAL_INCOME, annual_income);
        prefs.putString(CONSTANTS.PARTNER_LOC, sPartnerLoc);
        prefs.putString(CONSTANTS.MIN_HEIGHT, sMinHeight);
        prefs.putString(CONSTANTS.MAX_HEIGHT, sMaxHeight);


        communicator.saveAndSearchPaidPartner();

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

                String s = String.valueOf(selectedQualification.deleteCharAt(selectedQualification.length()-1));
//                selectedQualification.toString()
                txtListChild.setText(s);
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

        String[] ar = getActivity().getResources().getStringArray(R.array.religion_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }

    public void showMotherTongue(TextView textView){

        String[] ar = getActivity().getResources().getStringArray(R.array.mother_tongue_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showSelectableDialog(list, textView);
    }
    public void showMaritalStatus(TextView textView){

        String[] ar = getActivity().getResources().getStringArray(R.array.marital_status_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showSelectableDialog(list, textView);
    }
    public void showEducation(TextView textView){

        String[] ar = getActivity().getResources().getStringArray(R.array.education_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showSelectableDialog(list, textView);
    }
    public void showAnnualIncome(TextView textView){

        String[] ar = getActivity().getResources().getStringArray(R.array.ann_income_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showSelectableDialog(list, textView);
    }

    public void showHeight(TextView textView){

        String[] ar = getActivity().getResources().getStringArray(R.array.height_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }
    public void showCountry(final TextView textView) {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        ProgressClass.getProgressInstance().showDialog(getActivity());
        AndroidNetworking.post("https://iitiimshaadi.com/api/country.json")
                .addBodyParameter("token", token)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
                        try {
                            JSONArray jsonArray = response.getJSONArray("allCountries");
                            countryListWithId = new ArrayList<>();
                            List<String> countryList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject countryObject = jsonArray.getJSONObject(i);
                                String countryId = countryObject.getString("old_value");
                                String country = countryObject.getString("name");
                                countryList.add(country);
                                CountryModel countryModel = new CountryModel(countryId, country);
                                countryListWithId.add(countryModel);
                            }
                            showDialog(countryList, textView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", CONSTANTS.country_not_found);
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", CONSTANTS.country_not_found);
                    }
                });

    }
    public void showCity(final TextView textView){

        final List<String> cityList = new ArrayList<>();
        cityList.clear();
        String countryId = null;

        if(countryListWithId != null) {
            String country = mPartnerCurrentCountryTV.getText().toString();
            for (int i = 0; i < countryListWithId.size(); i++) {
                if (countryListWithId.get(i).getCountryName().equalsIgnoreCase(country)) {
                    countryId = countryListWithId.get(i).getCountryId();
                }
            }
            System.out.println(countryId);
        }

        ProgressClass.getProgressInstance().showDialog(getActivity());
        AndroidNetworking.post("https://iitiimshaadi.com/api/cities.json")
                .addBodyParameter("country_id", countryId)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
                        try {
                            JSONArray jsonArray = response.getJSONArray("allCities");
                            List<String> cityList = new ArrayList<>();
//                            if(jsonArray.length() > 0){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String city = object.getString("name");
                                cityList.add(city);
                            }
                            showSelectableDialog(cityList, textView);
                        /*}else {
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.cast_not_found);
                        }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", CONSTANTS.city_not_found);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", CONSTANTS.country_error_msg);
                    }
                });


//        String countryId = "1151";
/*
        APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
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
        });*/
    }
    public void showLocation(final TextView textView){

        final List<String> cityList = new ArrayList<>();
        cityList.clear();
        String countryId = null;
        try {
            String country = mPartnerCurrentCountryTV.getText().toString();
            for (int i = 0; i < countryListWithId.size(); i++) {
                if (countryListWithId.get(i).getCountryName().equalsIgnoreCase(country)) {
                    countryId = countryListWithId.get(i).getCountryId();
                }
            }
            System.out.println(countryId);

//        String countryId = "1151";

            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<CitiesAccCountryResponse> call = apiInterface.cityList(countryId);
            ProgressClass.getProgressInstance().showDialog(getActivity());
            call.enqueue(new Callback<CitiesAccCountryResponse>() {
                @Override
                public void onResponse(Call<CitiesAccCountryResponse> call, Response<CitiesAccCountryResponse> response) {
                    if (response.isSuccessful()) {
                        ProgressClass.getProgressInstance().stopProgress();
                        List<AllCity> rawCityList = response.body().getAllCities();
                        for (int i = 0; i < rawCityList.size(); i++) {
                            if (rawCityList.get(i).getName() != null) {
                                cityList.add(rawCityList.get(i).getName());
                            }
                        }

                        showSelectableDialog(cityList, textView);
                    }
                }

                @Override
                public void onFailure(Call<CitiesAccCountryResponse> call, Throwable t) {
                    call.cancel();
                    ProgressClass.getProgressInstance().stopProgress();
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }catch(NullPointerException npe){
            Log.e(TAG, " #Error : "+npe,npe);
            AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", CONSTANTS.country_not_found);
        }
    }
    public void showCaste(final TextView textView) {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String religion = mSelectReligionTV.getText().toString() ;
        ProgressClass.getProgressInstance().showDialog(getActivity());
        AndroidNetworking.post("https://iitiimshaadi.com/api/caste.json")
                .addBodyParameter("token", token)
                .addBodyParameter("religion", religion)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
                        try {
                            JSONArray jsonArray = response.getJSONArray("allCastes");
                            List<String> casteList = new ArrayList<>();
//                            if(jsonArray.length() > 0){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String country = jsonArray.getString(i);
                                casteList.add(country);
                            }
                            showSelectableDialog(casteList, textView);
                        /*}else {
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.cast_not_found);
                        }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", CONSTANTS.religion_error_msg);
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", CONSTANTS.cast_not_found);
                    }
                });

    }
    public interface PaidSearchPartnerFragmentCommunicator{
        void saveAndSearchPaidPartner();
//        void saveAndSearchPaidPartnerByKeyword(List<Query> queryList, String keyword);
//        void saveAndSearchPaidPartnerByAdvance(List<Query> queryList, List<String> profileList);

    }

}
