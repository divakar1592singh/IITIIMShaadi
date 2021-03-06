package com.senzecit.iitiimshaadi.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.model.api_response_model.common.CityModel;
import com.senzecit.iitiimshaadi.model.api_response_model.common.CountryModel;
import com.senzecit.iitiimshaadi.model.api_response_model.common.SliderCheckModel;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.SubsAdvanceSearchResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.search_partner_subs.User;
import com.senzecit.iitiimshaadi.sliderView.with_list.SliderDialogListLayoutAdapter;
import com.senzecit.iitiimshaadi.sliderView.with_list.SliderDialogListLayoutModel;
import com.senzecit.iitiimshaadi.sliderView.with_selection.SliderDialogCheckboxLayoutAdapter2;
import com.senzecit.iitiimshaadi.sliderView.with_selection.SliderDialogCheckboxLayoutModel;
import com.senzecit.iitiimshaadi.utils.AppMessage;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 14/11/17.
 */

public class SearchPartnerFragment extends Fragment implements View.OnClickListener, RxNetworkingForObjectClass.CompletionHandler {

    private static String TAG = "SearchPartnerFragment";
    View view;
    AppPrefs prefs;
    ImageView mAdvanceSearchIV;
    LinearLayout mlinearLayoutAdvanceSearch;
    boolean advanceSearch = true;
    Button mSearchPartner;

    CountryModel countryResponse;
    List<CountryModel> countryListWithId;

    SearchPartnerFragmentCommunicator communicator;
    RelativeLayout mPartnerCurrentCountry,mPartnerCurrentCity,mPartnerReligion,mPartnerCaste,mPartnerMotherTongue,mPartnerMaritalStatus,mPartnerEduOccup,mPartnerAnnIncome;
    LayoutInflater mDialogInflator;
    EditText mAgeMinET, mAgeMaxET;
    TextView mPartnerCurrentCountryTV, mPartnerCurrentCityIV, mSelectReligionTV, mSelectCastTV,
            mSelectMotherToungeTV, mMaritalStatusTV, mEducationOccupationTV, mAnnualIncomeTV, mAgeCautionTV;
    private List<SliderCheckModel> sliderCheckList;
    TextView mCountryID, mCityID;
    List<CityModel> cityWithIdList = null;
    RxNetworkingForObjectClass networkingClass;

    public void setSearchPartnerFragmentCommunicator(SearchPartnerFragmentCommunicator communicator){
        this.communicator = communicator;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        prefs = new AppPrefs(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_partner,container,false);
        mDialogInflator = LayoutInflater.from(view.getContext());
        sliderCheckList = new ArrayList<>();

        networkingClass = RxNetworkingForObjectClass.getInstance();
        networkingClass.setCompletionHandler(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callWebServiceForRecentSearch();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
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
    }

    private void init(){
        mlinearLayoutAdvanceSearch = view.findViewById(R.id.searchAdvanceLL);

        mAdvanceSearchIV = view.findViewById(R.id.searchAdvanceIV);
        mSearchPartner = view.findViewById(R.id.searchPartnerBtn);

        mPartnerCurrentCountry = view.findViewById(R.id.idPartnerCurrentCountry);
        mPartnerCurrentCity = view.findViewById(R.id.idPartnerCurrentCity);
        mPartnerReligion = view.findViewById(R.id.idPartnerReligion);
        mPartnerCaste = view.findViewById(R.id.idPartnerCaste);
        mPartnerMotherTongue = view.findViewById(R.id.idPartnerMotherTongue);
        mPartnerMaritalStatus = view.findViewById(R.id.idPartnerMaritalStatus);
        mPartnerEduOccup = view.findViewById(R.id.idPartnerEduOccup);
        mPartnerAnnIncome = view.findViewById(R.id.idPartnerAnnIncome);

        mAgeMinET = view.findViewById(R.id.ageMinET);
        mAgeMaxET = view.findViewById(R.id.ageMaxET);
        mAgeCautionTV = view.findViewById(R.id.idAgeCautionTV);

        mPartnerCurrentCountryTV = view.findViewById(R.id.partnerCurrentCountryTV);
        mPartnerCurrentCityIV = view.findViewById(R.id.partnerCurrentCityTV);
        mSelectReligionTV = view.findViewById(R.id.selectReligionTV);
        mSelectCastTV = view.findViewById(R.id.selectCastTV);
        mSelectMotherToungeTV = view.findViewById(R.id.selectMotherToungeTV);
        mMaritalStatusTV = view.findViewById(R.id.maritalStatusTV);
        mEducationOccupationTV = view.findViewById(R.id.educationOccupationTV);
        mAnnualIncomeTV = view.findViewById(R.id.annualIncomeTV);

        //
        mCountryID  = view.findViewById(R.id.idCountryID);
        mCityID  = view.findViewById(R.id.idCityID);

        textEventListner();

        cityWithIdList = new ArrayList<>();
    }

    public void textEventListner(){

        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(2);
        mAgeMinET.setFilters(FilterArray);
        mAgeMinET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        mAgeMaxET.setFilters(FilterArray);
        mAgeMaxET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        mAgeMinET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    String sMinAge = mAgeMinET.getText().toString().trim();
                    String sMaxAge = mAgeMaxET.getText().toString().trim();

                    if (!TextUtils.isEmpty(sMinAge)) {
                        int minimumAge = Integer.parseInt(sMinAge);
                            if (!TextUtils.isEmpty(sMinAge) && !TextUtils.isEmpty(sMaxAge)) {
                                int maximumAge = Integer.parseInt(sMaxAge);
                                if ((maximumAge - minimumAge) <= 5) {
                                    mAgeCautionTV.setVisibility(View.GONE);
                                } else {
                                    mAgeCautionTV.setVisibility(View.VISIBLE);
                                    mAgeCautionTV.setText(AppMessage.AGE_DIFF_5_INFO);
                                }
                        }
                    }

                }catch (NumberFormatException nfe){
                    Log.e(TAG, "#Err : "+nfe, nfe);
                }catch (NullPointerException npe){
                    Log.e(TAG, "#Err : "+npe, npe);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mAgeMaxET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


              /*  String sMinAge = mAgeMinET.getText().toString();
                String sMaxAge = mAgeMaxET.getText().toString();

                if(!TextUtils.isEmpty(sMinAge) && !TextUtils.isEmpty(sMaxAge)) {
                    int minimumAge = Integer.parseInt(sMinAge);
                    int maximumAge = Integer.parseInt(sMaxAge);

                    if ((maximumAge - minimumAge) <= 5) {
                        mAgeCautionTV.setVisibility(View.GONE);
                    } else {
                        mAgeCautionTV.setVisibility(View.VISIBLE);
                    }
                }
*/

                try {
                    String sMinAge = mAgeMinET.getText().toString().trim();
                    String sMaxAge = mAgeMaxET.getText().toString().trim();

                    if (!TextUtils.isEmpty(sMinAge)) {
                        int maximumAge = Integer.parseInt(sMaxAge);

                            if (!TextUtils.isEmpty(sMinAge) && !TextUtils.isEmpty(sMaxAge)) {
                                int minimumAge = Integer.parseInt(sMinAge);
                                if ((maximumAge - minimumAge) <= 5) {
                                    mAgeCautionTV.setVisibility(View.GONE);
                                } else {
                                    mAgeCautionTV.setVisibility(View.VISIBLE);
                                    mAgeCautionTV.setText(AppMessage.AGE_DIFF_5_INFO);
                                }
                            }
                    }

                }catch (NumberFormatException nfe){
                    Log.e(TAG, "#Err : "+nfe, nfe);
                }catch (NullPointerException npe){
                    Log.e(TAG, "#Err : "+npe, npe);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPartnerCurrentCountryTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String country = s.toString();
                showCountryId(country);
                mPartnerCurrentCityIV.setText("Select City");
            }
        });
        mPartnerCurrentCityIV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String city = removeLastChar(s.toString());
                String[] cityArr = city.split(",");
                mCityID.setText("");

                    showCityId(cityArr);

            }
        });

        mSelectReligionTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSelectCastTV.setText("Select Caste");
            }
        });
    }

    private void callWebServiceForRecentSearch() {

        String userID = prefs.getString(CONSTANTS.LOGGED_USERID);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("user_id", userID);

            RxNetworkingForObjectClass.getInstance().callWebServiceForJSONSearchPtnr(getActivity(), CONSTANTS.RECENT_SEARCH_URL, jsonObject, CONSTANTS.MEDIA_URL_1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
                callWebServiceForSubsAdvanceSearch();

//                Toast.makeText(getActivity(), "Functionality Part", Toast.LENGTH_SHORT).show();
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
        }
    }

    @Override
    public void handle(JSONObject object, String methodName) {

        String city = "", cityID = "", caste = "", motherTounge = "", maritalStatus = "", course = "", annualIncome = "";
        try {

            if(object.getInt("responseCode") == 200){

                String minage = object.getJSONObject("result").optString("minage");
                String maxage = object.getJSONObject("result").optString("maxage");
                String country = object.getJSONObject("country").optString("name");
                String countryId = object.getJSONObject("country").optString("old_value");

                String religion = object.getJSONObject("result").optString("religion");

                try{
                    StringBuilder stringBuilder = new StringBuilder();
                    StringBuilder cityIDSB = new StringBuilder();

                    JSONArray jsonArray = object.getJSONArray("cities");
                    for(int i = 0; i<jsonArray.length(); i++){
                        if(object.getJSONArray("cities").getString(i).equalsIgnoreCase("Any")){
                            stringBuilder.append(object.getJSONArray("cities").getString(i)).append(",");
                            cityIDSB.append(object.getJSONArray("cities").getString(i)).append(",");
                        }else {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            stringBuilder.append(jsonObject.optString("name")).append(",");
                            cityIDSB.append(jsonObject.optString("old_value")).append(",");
                        }

                     }
                    city =stringBuilder.toString();
                    cityID = cityIDSB.toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //                String location = convertJsonToString(object.getJSONObject("result").getJSONObject("location"));

                try{
                caste = convertJsonToString(object.getJSONObject("result").getJSONObject("caste"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try{
                motherTounge = convertJsonToString(object.getJSONObject("result").getJSONObject("mother_tounge"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try{
                maritalStatus = convertJsonToString(object.getJSONObject("result").getJSONObject("marital_status"));        } catch (JSONException e) {
                    e.printStackTrace();
                }

                try{
                course = convertJsonToString(object.getJSONObject("result").getJSONObject("course"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try{
                annualIncome = convertJsonToString(object.getJSONObject("result").getJSONObject("annual_income"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAgeMinET.setText(minage);
                mAgeMaxET.setText(maxage);
                mPartnerCurrentCountryTV.setText(country);
                mSelectReligionTV.setText(removeLastChar(religion));

                mPartnerCurrentCityIV.setText(removeLastChar(city));
                mSelectCastTV.setText(removeLastChar(caste));
                mSelectMotherToungeTV.setText(removeLastChar(motherTounge));
                mMaritalStatusTV.setText(removeLastChar(maritalStatus));
                mEducationOccupationTV.setText(removeLastChar(course));
                mAnnualIncomeTV.setText(removeLastChar(annualIncome));

                mCountryID.setText(countryId);
                mCityID.setText(cityID);


            }else {

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onServiceError(ANError error, String methodName) {

    }

    public interface SearchPartnerFragmentCommunicator{
        void saveSearchPartner(List<User> queryList, List<String> profileList, int totalUserCount );
    }

    /** API -  */

    /** Check API Section */
    public void callWebServiceForSubsAdvanceSearch(){

        List<String> profileList =new ArrayList<>();
        profileList.clear();
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        String minage = mAgeMinET.getText().toString() ;
        String maxage = mAgeMaxET.getText().toString() ;
        String country = mCountryID.getText().toString() ;

        String city =  removeLastChar(mCityID.getText().toString());
        String[] cityArr = new String[1];
        cityArr = city.split(",");

        String religion = mSelectReligionTV.getText().toString() ;
        String caste = removeLastChar(mSelectCastTV.getText().toString());
        String[] casteArr = new String[1];
        casteArr = caste.split(",");

        String mother_tounge =  removeLastChar(mSelectMotherToungeTV.getText().toString());
        String[] mother_toungeArr = new String[1];
        mother_toungeArr = mother_tounge.split(",");

        String marital_status =  removeLastChar(mMaritalStatusTV.getText().toString());
        String[] marital_statusArr = new String[1];
        marital_statusArr = marital_status.split(",");

        String course =  removeLastChar(mEducationOccupationTV.getText().toString());
        String[] courseArr = new String[1];
        courseArr = course.split(",");

        String annual_income =  removeLastChar(mAnnualIncomeTV.getText().toString());
        String[] annual_incomeArr = new String[1];
        annual_incomeArr = annual_income.split(",");

        profileList.add(minage);profileList.add(maxage);profileList.add(mPartnerCurrentCountryTV.getText().toString());
        profileList.add(mPartnerCurrentCityIV.getText().toString());profileList.add(religion);profileList.add(caste);
        profileList.add(mother_tounge);profileList.add(marital_status);
        profileList.add(course);profileList.add(annual_income);

        try{
        if(NetworkClass.getInstance().checkInternet(getActivity()) == true) {
                if ((Integer.parseInt(maxage) - Integer.parseInt(minage)) <= 5) {


                    APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
                    ProgressClass.getProgressInstance().showDialog(getActivity());
                    Call<SubsAdvanceSearchResponse> call = apiInterface.advanceSearch(token, "1", minage, maxage, country, cityArr, religion, casteArr, mother_toungeArr, marital_statusArr, courseArr, annual_incomeArr);
                    call.enqueue(new Callback<SubsAdvanceSearchResponse>() {
                        @Override
                        public void onResponse(Call<SubsAdvanceSearchResponse> call, Response<SubsAdvanceSearchResponse> response) {
                            ProgressClass.getProgressInstance().stopProgress();
                            if (response.isSuccessful()) {
                                if (response.body().getMessage().getSuccess().equalsIgnoreCase("success")) {
                                    if (response.body().getUsers().size() > 0) {
                                        List<User> queryList = response.body().getUsers();
                                        System.out.print(profileList);
                                        communicator.saveSearchPartner(queryList, profileList, response.body().getTotalCount());
                                    } else {
                                        AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", CONSTANTS.search_ptnr_err_msg);
                                    }
                                } else {
//                        AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Search Partner", "Opps");
                                    reTryMethod();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<SubsAdvanceSearchResponse> call, Throwable t) {
                            call.cancel();
//                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            ProgressClass.getProgressInstance().stopProgress();
                            reTryMethod();
                        }
                    });

                } else {
                    AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", AppMessage.AGE_DIFF_5_INFO);
                }
        }else {
            NetworkDialogHelper.getInstance().showDialog(getActivity());
        }

    }catch (NumberFormatException nfe){
        Log.e(TAG, " #Error : "+nfe, nfe);
        Toast.makeText(getActivity(), AppMessage.AGE_ERROR_INFO, Toast.LENGTH_SHORT).show();
    }catch (Exception e){
        Log.e(TAG, " #Error : "+e, e);
        Toast.makeText(getActivity(), AppMessage.HEIGHT_ERROR_INFO, Toast.LENGTH_SHORT).show();
    }

    }

    /** Dialog */
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
        final RecyclerView recyclerView = view.findViewById(R.id.custom_list);
//		final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        view.findViewById(R.id.button_done).setOnClickListener(new View.OnClickListener() {
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
        sliderCheckList = new ArrayList<>();
        sliderCheckList.clear();
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
                sliderCheckList.add(new SliderCheckModel(i, false));
            }
        }else {
            for (int i = 0; i < 20; i++) {
                SliderDialogCheckboxLayoutModel model = new SliderDialogCheckboxLayoutModel();
                model.setName("senzecit " + i);
                models.add(model);
                sliderCheckList.add(new SliderCheckModel(i, false));
            }
        }

        final Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);//,R.style.CustomDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        View view = mDialogInflator.inflate(R.layout.slider_dialog_checkbox_layout, null);

        final ListView listView = view.findViewById(R.id.custom_list);
        Button doneBtn = view.findViewById(R.id.button_done);

        SliderDialogCheckboxLayoutAdapter2 clad1 = new SliderDialogCheckboxLayoutAdapter2(getContext(), models, sliderCheckList);
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
                    CheckBox checkBox = parentListView[0].findViewById(R.id.idCheckbox);
                    TextView textView = parentListView[0].findViewById(R.id.idText);

                    if (checkBox.isChecked()){

                        selectedQualification.append(textView.getText().toString()).append(",");
                    }

                }
                /*txtListChild.setText(selectedQualification.toString());
                dialog.dismiss();*/
                txtListChild.setText(selectedQualification.toString());
                String rawStr = txtListChild.getText().toString().trim();
                txtListChild.setText(removeLastChar(rawStr));
                dialog.dismiss();

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
    public void showCountry(final TextView textView) {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(NetworkClass.getInstance().checkInternet(getActivity()) == true){

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

        }else {
            NetworkDialogHelper.getInstance().showDialog(getActivity());
        }
    }

    public void showCountryId(String countryName) {

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

                                if(country.equalsIgnoreCase(countryName)){
                                    mCountryID.setText(countryObject.getString("old_value"));
                                }

                                countryList.add(country);
                                CountryModel countryModel = new CountryModel(countryId, country);
                                countryListWithId.add(countryModel);
                            }
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

        if(NetworkClass.getInstance().checkInternet(getActivity()) == true){

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
                                if(jsonArray.length() > 0){
                                    List<String> cityList = new ArrayList<>();
                                    cityList.add("Any");
//                            if(jsonArray.length() > 0){
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String city = object.getString("name");
                                        String cityId = object.getString("old_value");
                                        cityList.add(city);
                                        CityModel cityModel = new CityModel(city, cityId);
                                        cityWithIdList.add(cityModel);

                                    }
//                            showDialog(cityList, textView);
                                    CityModel cityModel = new CityModel("Any", "Any");
                                    cityWithIdList.add(cityModel);
                                    showSelectableDialog(cityList, textView);
                                }else {
                                    AlertDialogSingleClick.getInstance().showDialog(getActivity(), "Alert", CONSTANTS.country_error_msg);
                                }
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


        }else {
            NetworkDialogHelper.getInstance().showDialog(getActivity());
        }
    }

    public void showCityId(String[] cityArr){
        StringBuilder sb1 = new StringBuilder();

        for(int i = 0; i < cityArr.length; i++) {
            for (int j = 0; j < cityWithIdList.size(); j++) {
                if (cityArr[i].trim().equalsIgnoreCase((cityWithIdList.get(j).getCityName()))) {

//                    mCityID.append(cityWithIdList.get(i).getCityId() + ", ");
                    sb1.append(cityWithIdList.get(j).getCityId()).append(", ");
                    break;
                }
            }
        }

        System.out.println(sb1);
        mCityID.setText(sb1);


    }

    public void showCaste(final TextView textView) {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String religion = mSelectReligionTV.getText().toString() ;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("religion_name", religion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ProgressClass.getProgressInstance().showDialog(getActivity());
//        AndroidNetworking.post("https://iitiimshaadi.com/api/caste.json")
        AndroidNetworking.post("http://35.154.217.225:1110/searchCaste")
                /*.addBodyParameter("token", token)
                .addBodyParameter("religion", religion)
                .setTag("test")*/
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            List<String> casteList = new ArrayList<>();
                            if(jsonArray.length() > 0){
                                casteList.add("Any");
                            }
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String country = jsonObject1.getString("group_name");
                                casteList.add(country);
                            }
                            showSelectableDialog(casteList, textView);
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


/*
    public void showCaste(final TextView textView) {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String religion = mSelectReligionTV.getText().toString();

        if(NetworkClass.getInstance().checkInternet(getActivity()) == true){

        ProgressClass.getProgressInstance().showDialog(getActivity());
        AndroidNetworking.post("https://iitiimshaadi.com/api/caste.json")
                .addBodyParameter("token", token)
                .addBodyParameter("religion", religion)
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
        }else {
            NetworkDialogHelper.getInstance().showDialog(getActivity());
        }
    }
*/

    public void reTryMethod(){

        String title = "Alert";
        String msg = "Oops. Please Try Again! \n";

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog_two_click);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTxt = dialog.findViewById(R.id.txt_file_path);
        titleTxt.setText(title);
        TextView msgTxt = dialog.findViewById(R.id.idMsg);
        msgTxt.setText(msg);

        Button dialogBtn_cancel = dialog.findViewById(R.id.btn_cancel);
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = dialog.findViewById(R.id.btn_okay);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWebServiceForSubsAdvanceSearch();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static String removeLastChar(String s) {
        String rawString = "";
        if (s == null || s.length() == 0 || s.equalsIgnoreCase("--") || s.equalsIgnoreCase("-")) {
            return s = "";
        }else if(s.endsWith(",")){

            return  rawString = s.substring(0, s.length()-1);
        }
        return s;
    }

    public static String removeWhiteSpace(String s) {
        if (s == null || s.length() == 0 || s.equalsIgnoreCase("--")) {
            return s = "[]";
        }
        return s.replaceAll("\\s+","");
//        st = st.replaceAll("\\s+","")
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//
//        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
//
//    }


    public String convertJsonToString(JSONObject jsonObj) {
        Iterator<String> iter = jsonObj.keys();
        StringBuilder stringBuilder = new StringBuilder();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                String value = jsonObj.getString(key);

                stringBuilder.append(value).append(",");
            } catch (JSONException e) {
                // Something went wrong!
            }
        }
        return stringBuilder.toString();
    }
}
