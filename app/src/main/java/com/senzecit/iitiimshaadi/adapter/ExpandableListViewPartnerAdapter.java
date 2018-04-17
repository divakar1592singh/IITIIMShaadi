package com.senzecit.iitiimshaadi.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
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
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.common.SliderCheckModel;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.my_profile.MyProfileResponse;
import com.senzecit.iitiimshaadi.model.api_rquest_model.groom.ChoiceOfGroomRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.pt_education.PtrEduCareerRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.ptr_basic_profile.ParnerBasicProfileRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.ptr_religious_country.PtrReligionCountryRequest;
import com.senzecit.iitiimshaadi.model.exp_listview.ExpPartnerProfileModel;
import com.senzecit.iitiimshaadi.sliderView.with_list.SliderDialogListLayoutAdapter;
import com.senzecit.iitiimshaadi.sliderView.with_list.SliderDialogListLayoutModel;
import com.senzecit.iitiimshaadi.sliderView.with_selection.SliderDialogCheckboxLayoutAdapter2;
import com.senzecit.iitiimshaadi.sliderView.with_selection.SliderDialogCheckboxLayoutModel;
import com.senzecit.iitiimshaadi.utils.AppController;
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
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 28/11/17.
 */

public class ExpandableListViewPartnerAdapter extends BaseExpandableListAdapter {

    private static String TAG = "ExpandableListViewPartnerAdapter";
    private Context _context;
    AppPrefs prefs;
    LayoutInflater layoutInflater;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    MyProfileResponse myProfileResponse;
    JSONObject object;
    JSONObject partnerJson = null;
    private List<SliderCheckModel> sliderCheckList;

    public ExpandableListViewPartnerAdapter(Context context, List<String> listDataHeader,
                                               HashMap<String, List<String>> listChildData, JSONObject object) {
        this._context = context;
        layoutInflater = LayoutInflater.from(_context);
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.object = object;
        prefs = AppController.getInstance().getPrefs();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }



    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        try {
            partnerJson = object.getJSONObject("partnerBasicData");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        final String childText = (String) getChild(groupPosition, childPosition);
        //****lchild data items start

       switch (groupPosition) {
        case 0:
        switch (childPosition){
            case 0:
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item_numbertype, null);

                TextInputLayout textInputLayout = convertView.findViewById(R.id.idTextInputLayout);
                textInputLayout.setHint(childText);

                EditText editText = convertView.findViewById(R.id.idlistitemET);

                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(2);
                editText.setFilters(FilterArray);


                editText.setText(String.valueOf(partnerJson.optInt("prefered_partner_min_age")));
                if(TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getMinimum_Age()))
                    ExpPartnerProfileModel.getInstance().setMinimum_Age(String.valueOf(partnerJson.optInt("prefered_partner_min_age")));

                if(!TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getMinimum_Age()))
                    editText.setText(ExpPartnerProfileModel.getInstance().getMinimum_Age());

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ExpPartnerProfileModel.getInstance().setMinimum_Age(editable.toString());

                    }
                });

                break;
            case 1:
                LayoutInflater infalInflater1 = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater1.inflate(R.layout.list_item_secondtype, null);

                TextInputLayout textInputLayout1 = convertView.findViewById(R.id.idTextInputLayout);
                textInputLayout1.setHint(childText);

                EditText editText1 = convertView.findViewById(R.id.idlistitemET);

                editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                InputFilter[] FilterArray2 = new InputFilter[1];
                FilterArray2[0] = new InputFilter.LengthFilter(2);
                editText1.setFilters(FilterArray2);

                //SetData - PreferedPartnerMaxAge
                editText1.setText(String.valueOf(partnerJson.optInt("prefered_partner_max_age")));
                if(TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getMaximum_Age()))
                    ExpPartnerProfileModel.getInstance().setMaximum_Age(String.valueOf(partnerJson.optInt("prefered_partner_max_age")));

                if(!TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getMaximum_Age()))
                    editText1.setText(ExpPartnerProfileModel.getInstance().getMaximum_Age());

                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ExpPartnerProfileModel.getInstance().setMaximum_Age(editable.toString());
                    }
                });

                break;
            case 2:
                LayoutInflater infalInflater2 = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater2.inflate(R.layout.list_item, null);

                final TextView txtListChild2 = convertView
                        .findViewById(R.id.childItemTV);
                txtListChild2.setText(childText);

                //SetData - PreferedPartnerHeightMin
                txtListChild2.setText(partnerJson.optString("prefered_partner_height_min"));
                if(TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getMin_Height()))
                    ExpPartnerProfileModel.getInstance().setMin_Height(partnerJson.optString("prefered_partner_height_min"));

                TextView txtListChildHeader2 = convertView
                        .findViewById(R.id.childItemTVheader);
                txtListChildHeader2.setText(childText);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                                showDialog(txtListChild2,100,50);
                        showHeight(txtListChild2);
                    }
                });

                if(!TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getMin_Height()))
                    txtListChild2.setText(ExpPartnerProfileModel.getInstance().getMin_Height());

                txtListChild2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ExpPartnerProfileModel.getInstance().setMin_Height(editable.toString());
                    }
                });

                break;
            case 3:
                LayoutInflater infalInflater3 = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater3.inflate(R.layout.list_item, null);

                final TextView txtListChild3 = convertView
                        .findViewById(R.id.childItemTV);
                txtListChild3.setText(childText);

                //SetData - PreferedPartnerHeightMax
                txtListChild3.setText(partnerJson.optString("prefered_partner_height_max"));
                if(TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getMax_Height()))
                    ExpPartnerProfileModel.getInstance().setMax_Height(partnerJson.optString("prefered_partner_height_max"));

                TextView txtListChildHeader3 = convertView
                        .findViewById(R.id.childItemTVheader);
                txtListChildHeader3.setText(childText);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                                showDialog(txtListChild3,100,50);
                        showHeight(txtListChild3);
                    }
                });

                if(!TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getMax_Height()))
                    txtListChild3.setText(ExpPartnerProfileModel.getInstance().getMax_Height());

                txtListChild3.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ExpPartnerProfileModel.getInstance().setMax_Height(editable.toString());
                    }
                });

                break;
            case 4:
                LayoutInflater infalInflater4 = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater4.inflate(R.layout.list_item, null);

                final TextView txtListChild4 = convertView
                        .findViewById(R.id.childItemTV);
                txtListChild4.setText(childText);

                //SetData - PreferedPartnerMaritalStatus
//                        String maritalStatus = "";
                StringBuilder builder = new StringBuilder();
                try {
                    JSONArray jsonArray = partnerJson.getJSONArray("prefered_partner_marital_status");
                    for(int pos = 0; pos < jsonArray.length(); pos++){
                        System.out.println(jsonArray);
//                             JSONObject object1 = jsonArray.getJSONObject(pos);
                        if(pos == jsonArray.length()-1){
                            builder.append(jsonArray.optString(pos));
                        }else {
                            builder.append(jsonArray.optString(pos)).append(",");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                txtListChild4.setText(builder);

                if(TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getMarital_Status()))
                    ExpPartnerProfileModel.getInstance().setMarital_Status(builder.toString());

                TextView txtListChildHeader4 = convertView
                        .findViewById(R.id.childItemTVheader);
                txtListChildHeader4.setText(childText);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                                showDialog(txtListChild4,100,50);
                        showMaritalStatusPt(txtListChild4);
                    }
                });

                if(!TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getMarital_Status()))
                    txtListChild4.setText(ExpPartnerProfileModel.getInstance().getMarital_Status());

                txtListChild4.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ExpPartnerProfileModel.getInstance().setMarital_Status(editable.toString());
                    }
                });

                break;
            case 5:
                LayoutInflater infalInflater5 = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater5.inflate(R.layout.submit_data_button, null);
                Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                saveChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do somethings.
                        saveChangesOfCasePt_0();
                    }
                });
                break;

        }

        break;
        case 1:
        switch (childPosition){
            case 0:
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);

                final TextView txtListChild = convertView
                        .findViewById(R.id.childItemTV);
                txtListChild.setText(childText);

                //SetData - PreferedPartnerReligion
                txtListChild.setText(partnerJson.optString("prefered_partner_religion"));

                if(TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getPreferred_Religion()))
                    ExpPartnerProfileModel.getInstance().setPreferred_Religion(partnerJson.optString("prefered_partner_religion"));

                    TextView txtListChildHeader = convertView
                        .findViewById(R.id.childItemTVheader);
                txtListChildHeader.setText(childText);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                                showDialog(txtListChild,100,50);
                        showReligion(txtListChild);
                    }
                });

                if(!TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getPreferred_Religion()))
                    txtListChild.setText(ExpPartnerProfileModel.getInstance().getPreferred_Religion());

                txtListChild.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ExpPartnerProfileModel.getInstance().setPreferred_Religion(editable.toString());
                    }
                });

                break;
            case 1:
                LayoutInflater infalInflater1 = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater1.inflate(R.layout.list_item, null);

                final TextView txtListChild1 = convertView
                        .findViewById(R.id.childItemTV);
                txtListChild1.setText(childText);

                //SetData - PreferedPartnerCaste
//                        String caste1 = partnerJson.optString("prefered_partner_caste").toString().replace("[", "");
//                        String caste = caste1.replace("]","");
                StringBuilder builder = new StringBuilder();
                try {
                    JSONArray jsonArray = partnerJson.getJSONArray("prefered_partner_caste");
                    for (int pos = 0; pos < jsonArray.length(); pos++) {
                        System.out.println(jsonArray);
//                             JSONObject object1 = jsonArray.getJSONObject(pos);
                        if (pos == jsonArray.length() - 1) {
                            builder.append(jsonArray.optString(pos));
                        } else {
                            builder.append(jsonArray.optString(pos)).append(",");
                        }
                    }
                }catch (JSONException jse){
                    Log.e(TAG, " #Err : "+jse, jse);
                }
                txtListChild1.setText(builder);

                TextView txtListChildHeader1 = convertView
                        .findViewById(R.id.childItemTVheader);
                txtListChildHeader1.setText(childText);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                                showDialog(txtListChild1,100,50);
                        showCastePt(txtListChild1);
                    }
                });

                if(!TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getPreferred_Caste()))
                    txtListChild1.setText(ExpPartnerProfileModel.getInstance().getPreferred_Caste());

                txtListChild1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ExpPartnerProfileModel.getInstance().setPreferred_Caste(editable.toString());
                    }
                });
                break;
            case 2:
                LayoutInflater infalInflater2 = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater2.inflate(R.layout.list_item, null);

                TextView txtListChildHeader2 = convertView
                        .findViewById(R.id.childItemTVheader);
                txtListChildHeader2.setText(childText);

                final TextView txtListChild2 = convertView
                        .findViewById(R.id.childItemTV);
                txtListChild2.setText(childText);

                   /*     try {
                            //SetData - PreferedPartnerCountry
                            String country1 = partnerJson.optString("prefered_partner_country").toString().replace("[", "");
                            String country = country1.replace("]", "");
                            txtListChild2.setText(country);

                        }catch (NullPointerException npe){
                            Log.e("TAG", " #Error : "+npe, npe );
                        }
                        */
                StringBuilder builder1 = new StringBuilder();
                try {
                    JSONArray jsonArray = partnerJson.getJSONArray("prefered_partner_country");
                    for (int pos = 0; pos < jsonArray.length(); pos++) {
                        System.out.println(jsonArray);
//                             JSONObject object1 = jsonArray.getJSONObject(pos);
                        if (pos == jsonArray.length() - 1) {
                            builder1.append(jsonArray.optString(pos));
                        } else {
                            builder1.append(jsonArray.optString(pos)).append(",");
                        }
                    }
                }catch (JSONException jse){
                    Log.e(TAG, " #Err : "+jse, jse);
                }
                txtListChild2.setText(builder1);

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                                showDialog(txtListChild2,100,50);
                        showCountryPt(txtListChild2);
                    }
                });

                if(!TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getPreferred_Country()))
                    txtListChild2.setText(ExpPartnerProfileModel.getInstance().getPreferred_Country());

                txtListChild2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ExpPartnerProfileModel.getInstance().setPreferred_Country(editable.toString());
                    }
                });


                break;
            case 3:
                LayoutInflater infalInflater3 = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater3.inflate(R.layout.submit_data_button, null);
                Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                saveChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do somethings.
                        saveChangesOfCasePt_1();
                    }
                });
                break;
        }
        break;
        case 2:
        //Toast.makeText(_context, "2", //Toast.LENGTH_LONG).show();
        switch (childPosition){
            case 0:
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);

                final TextView txtListChild = convertView
                        .findViewById(R.id.childItemTV);
                txtListChild.setText(childText);

                //SetData - PreferedPartnerEducation
//                String education1 = partnerJson.optString("prefered_partner_education").toString().replace("[", "");
//                String education = education1.replace("]","");
                StringBuilder builder = new StringBuilder();
                try {
                    JSONArray jsonArray = partnerJson.getJSONArray("prefered_partner_education");
                    for (int pos = 0; pos < jsonArray.length(); pos++) {
                        System.out.println(jsonArray);
//                             JSONObject object1 = jsonArray.getJSONObject(pos);
                        if (pos == jsonArray.length() - 1) {
                            builder.append(jsonArray.optString(pos));
                        } else {
                            builder.append(jsonArray.optString(pos)).append(",");
                        }
                    }
                }catch (JSONException jse){
                    Log.e(TAG, " #Err : "+jse, jse);
                }
                txtListChild.setText(builder);

                TextView txtListChildHeader = convertView
                        .findViewById(R.id.childItemTVheader);
                txtListChildHeader.setText(childText);

                if(!TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getPreferred_Education()))
                    txtListChild.setText(ExpPartnerProfileModel.getInstance().getPreferred_Education());

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                                showSelectableDialog(txtListChild,100,50);
                        showEducation(txtListChild);
                    }
                });

                txtListChild.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ExpPartnerProfileModel.getInstance().setPreferred_Education(editable.toString());
                    }
                });

                break;
            case 1:
                LayoutInflater infalInflater1 = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater1.inflate(R.layout.submit_data_button, null);
                Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                saveChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do somethings.
                        saveChangesOfCasePt_2();
                    }
                });
                break;

        }
        break;
        case 3:
        switch (childPosition){
            case 0:
                //Toast.makeText(_context, "3", //Toast.LENGTH_LONG).show();
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item_boxtype, null);

                EditText editText = convertView.findViewById(R.id.idlistitemET);
                editText.setHint("Say something...");
//                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                //SetData - ChoiceOfGroom
                editText.setText(partnerJson.optString("choice_of_partner"));

                if(!TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getChoice_of_Groom()))
                    editText.setText(ExpPartnerProfileModel.getInstance().getChoice_of_Groom());

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ExpPartnerProfileModel.getInstance().setChoice_of_Groom(editable.toString());

                    }
                });

                break;
            case 1:
                LayoutInflater infalInflater1 = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater1.inflate(R.layout.submit_data_button, null);
                Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                saveChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //do somethings.
                        saveChangesOfCasePt_3();
                    }
                });
                break;
        }
        break;

        }

        return convertView;
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = convertView
                .findViewById(R.id.profileGroupTV);
        lblListHeader.setTypeface(null, Typeface.NORMAL);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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

        final Dialog dialog = new Dialog(_context, R.style.CustomDialog);//,R.style.CustomDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

//        View view = getLayoutInflater().inflate(R.layout.slider_dialog_list_layout, null);
        View view = layoutInflater.inflate(R.layout.slider_dialog_list_layout, null);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(_context);
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

        final SliderDialogListLayoutAdapter clad1 = new SliderDialogListLayoutAdapter(_context, models);
        recyclerView.setAdapter(clad1);

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialogs.add(dialog);
        dialog.show();

        WindowManager wm = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
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

        final Dialog dialog = new Dialog(_context, R.style.CustomDialog);//,R.style.CustomDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        View view = layoutInflater.inflate(R.layout.slider_dialog_checkbox_layout, null);

        final ListView listView = view.findViewById(R.id.custom_list);
        Button doneBtn = view.findViewById(R.id.button_done);


        SliderDialogCheckboxLayoutAdapter2 clad1 = new SliderDialogCheckboxLayoutAdapter2(_context, models, sliderCheckList);
        listView.setAdapter(clad1);


        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        selectableDialogs.add(dialog);
        dialog.show();

        WindowManager wm = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
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

                        selectedQualification.append(textView.getText().toString()).append(", ");
                    }

                }
                txtListChild.setText(selectedQualification.toString());
                String rawStr = txtListChild.getText().toString().trim();
                txtListChild.setText(removeLastChar(rawStr));
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

    //SET & GET DATA

    public void showHeight(TextView textView){

        String[] ar = _context.getResources().getStringArray(R.array.height_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }
    public void showMaritalStatusPt(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.marital_status_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showSelectableDialog(list, textView);
    }
    public void showReligion(TextView textView){

        String[] ar = _context.getResources().getStringArray(R.array.religion_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }
    public void showEducation(TextView textView){

        String[] ar = _context.getResources().getStringArray(R.array.education_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showSelectableDialog(list, textView);
    }

    /**Search Partner*/
    /** Network Operation */
    public void saveChangesOfCasePt_0(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        String Minimum_Age = ExpPartnerProfileModel.getInstance().getMinimum_Age();
        String Maximum_Age = ExpPartnerProfileModel.getInstance().getMaximum_Age();
        String Min_Height = ExpPartnerProfileModel.getInstance().getMin_Height();
        String Max_Height = ExpPartnerProfileModel.getInstance().getMax_Height();
        String Marital_Status = ExpPartnerProfileModel.getInstance().getMarital_Status();

        String[] Marital_StatusArr = new String[1];
        Marital_StatusArr[0] = Marital_Status;

        try {
            if (NetworkClass.getInstance().checkInternet(_context) == true) {
                if ((Integer.parseInt(Maximum_Age) - Integer.parseInt(Minimum_Age)) > 0 ) {
//                    if ((Integer.parseInt(Maximum_Age) - Integer.parseInt(Minimum_Age)) <= 5 ) {
                        if ((getHeightInCM(Max_Height) - getHeightInCM(Min_Height)) > 0) {

                            ParnerBasicProfileRequest request = new ParnerBasicProfileRequest();
                            request.token = token;
                            request.prefered_partner_min_age = Minimum_Age;
                            request.prefered_partner_max_age = Maximum_Age;
                            request.prefered_partner_height = Min_Height;
                            request.prefered_partner_height_max = Max_Height;
                            request.prefered_partner_marital_status = Marital_Status;

                            ProgressClass.getProgressInstance().showDialog(_context);
                            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
                            Call<AddFolderResponse> call = apiInterface.sendPartnerBasicProfile(request);
                            call.enqueue(new Callback<AddFolderResponse>() {
                                @Override
                                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                                    ProgressClass.getProgressInstance().stopProgress();
                                    if (response.isSuccessful()) {
                                        AddFolderResponse serverResponse = response.body();
                                        if (serverResponse.getMessage().getSuccess() != null) {
                                            if (serverResponse.getMessage().getSuccess().equalsIgnoreCase("success")) {

                                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", "Successfull");
//                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();

                                            } else {
//                            Toast.makeText(_context, "Confuse", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(_context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                                    call.cancel();
                                    ProgressClass.getProgressInstance().stopProgress();
                                    Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", AppMessage.HEIGHT_DIFF_ERROR_INFO);
                        }
//                    } else {
//                        AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", AppMessage.AGE_DIFF_5_INFO);
//                    }
                } else {
                    AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", AppMessage.AGE_DIFF_ERROR_INFO);
                }

            } else {
                NetworkDialogHelper.getInstance().showDialog(_context);
            }

        }catch (NumberFormatException nfe){
            Log.e(TAG, " #Error : "+nfe, nfe);
            Toast.makeText(_context, AppMessage.AGE_ERROR_INFO, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(TAG, " #Error : "+e, e);
            Toast.makeText(_context, AppMessage.HEIGHT_ERROR_INFO, Toast.LENGTH_SHORT).show();
        }

    }
    public void saveChangesOfCasePt_1(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String Preferred_Religion = ExpPartnerProfileModel.getInstance().getPreferred_Religion();
        String Preferred_Caste = ExpPartnerProfileModel.getInstance().getPreferred_Caste();
        String Preferred_Country = ExpPartnerProfileModel.getInstance().getPreferred_Country();

        String[] Preferred_CasteArr = new String[1];
        String[] Preferred_CountryArr = new String[1];
        Preferred_CasteArr[0] = Preferred_Caste;
        Preferred_CountryArr[0] = Preferred_Country;

        PtrReligionCountryRequest request = new PtrReligionCountryRequest();
        request.token = token;
        request.prefered_partner_religion = Preferred_Religion;
        request.prefered_partner_caste = Preferred_Caste;
        request.prefered_partner_country = Preferred_Country;
        if(NetworkClass.getInstance().checkInternet(_context) == true){

            ProgressClass.getProgressInstance().showDialog(_context);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<AddFolderResponse> call = apiInterface.sendPartnerReligionCountry(request);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {
                        AddFolderResponse ptReligionResponse = response.body();
                        if(ptReligionResponse.getMessage().getSuccess() != null) {
                            if (ptReligionResponse.getMessage().getSuccess().equalsIgnoreCase("success")) {

                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", "Successfull");
//                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();

                            } else {
//                            Toast.makeText(_context, "Confuse", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(_context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                    call.cancel();
                    ProgressClass.getProgressInstance().stopProgress();
                    Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            NetworkDialogHelper.getInstance().showDialog(_context);
        }


    }
    public void saveChangesOfCasePt_2(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        String Preferred_Education = ExpPartnerProfileModel.getInstance().getPreferred_Education();

        String[] Preferred_EducationArr = new String[1];
        Preferred_EducationArr[0] = Preferred_Education;

        PtrEduCareerRequest request = new PtrEduCareerRequest();
        request.token = token;
        request.prefered_partner_education = Preferred_Education;
        if(NetworkClass.getInstance().checkInternet(_context) == true){

            ProgressClass.getProgressInstance().showDialog(_context);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<AddFolderResponse> call = apiInterface.sendPartnerEduCareer(request);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {
                        AddFolderResponse ptrEduResponse = response.body();
                        if(ptrEduResponse.getMessage().getSuccess() != null) {
                            if (ptrEduResponse.getMessage().getSuccess().equalsIgnoreCase("success")) {

                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", "Successfull");
//                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();

                            } else {
//                            Toast.makeText(_context, "Confuse", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(_context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                    call.cancel();
                    ProgressClass.getProgressInstance().stopProgress();
                    Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            NetworkDialogHelper.getInstance().showDialog(_context);
        }

    }
    public void saveChangesOfCasePt_3(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String Choice_of_Groom = ExpPartnerProfileModel.getInstance().getChoice_of_Groom();

        ChoiceOfGroomRequest request = new ChoiceOfGroomRequest();
        request.token = token;
        request.choice_of_partner = Choice_of_Groom;

        if(NetworkClass.getInstance().checkInternet(_context) == true){

            ProgressClass.getProgressInstance().showDialog(_context);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<AddFolderResponse> call = apiInterface.sendPartnerGroom(request);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {
                        AddFolderResponse aboutMeResponse = response.body();
                        if(aboutMeResponse.getMessage().getSuccess() != null) {
                            if (aboutMeResponse.getMessage().getSuccess().equalsIgnoreCase("success")) {

                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", "Successfull");
//                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();

                            } else {
//                            Toast.makeText(_context, "Confuse", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(_context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddFolderResponse> call, Throwable t) {
                    call.cancel();
                    ProgressClass.getProgressInstance().stopProgress();
                    Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            NetworkDialogHelper.getInstance().showDialog(_context);
        }

    }

    public void showCountryPt(final TextView textView) {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        if(NetworkClass.getInstance().checkInternet(_context) == true){

            ProgressClass.getProgressInstance().showDialog(_context);
            AndroidNetworking.post("https://iitiimshaadi.com/api/country.json")
                    .addBodyParameter("token", token)
//                .addBodyParameter("religion", preferred_Religion)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            ProgressClass.getProgressInstance().stopProgress();
                            try {
                                JSONArray jsonArray = response.getJSONArray("allCountries");
                                List<String> countryList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject countryObject = jsonArray.getJSONObject(i);
                                    String country = countryObject.getString("name");
                                    countryList.add(country);
                                }
                                showSelectableDialog(countryList, textView);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.country_not_found);
                            }

                        }

                        @Override
                        public void onError(ANError error) {
                            ProgressClass.getProgressInstance().stopProgress();
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.country_not_found);
                        }
                    });

        }else {
            NetworkDialogHelper.getInstance().showDialog(_context);
        }

    }

    public void showCastePt(final TextView textView) {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String religion = ExpPartnerProfileModel.getInstance().getPreferred_Religion();
        if(NetworkClass.getInstance().checkInternet(_context) == true){

            ProgressClass.getProgressInstance().showDialog(_context);
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
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String country = jsonArray.getString(i);
                                    casteList.add(country);
                                }
                                showSelectableDialog(casteList, textView);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.cast_not_found);
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            ProgressClass.getProgressInstance().stopProgress();
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.religion_error_msg);
                        }
                    });

        }else {
            NetworkDialogHelper.getInstance().showDialog(_context);
        }
    }

    public static int getHeightInCM(String s) {

        if (s == null || s.length() == 0 || s.equalsIgnoreCase("--") || s.equalsIgnoreCase("-")) {
            return 0;
        }
        String removedSpaceStr = s.replaceAll("\\s+", "");
        String rawString = removedSpaceStr.substring(0, removedSpaceStr.length() - 3);
        String netString = rawString.substring(rawString.length() - 3, rawString.length());
        return Integer.parseInt(netString);
    }

    public static String removeLastChar(String s) {
        if (s == null || s.length() == 0 || s.equalsIgnoreCase("--") || s.equalsIgnoreCase("-")) {
            return s = "";
        }
        String rawString = s.substring(0, s.length()-1);
        return rawString;
    }


}
