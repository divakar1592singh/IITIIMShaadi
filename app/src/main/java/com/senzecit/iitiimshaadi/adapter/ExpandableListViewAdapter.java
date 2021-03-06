package com.senzecit.iitiimshaadi.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
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
import com.senzecit.iitiimshaadi.api.RxNetworkingForObjectClass;
import com.senzecit.iitiimshaadi.model.api_response_model.common.SliderCheckModel;
import com.senzecit.iitiimshaadi.model.api_response_model.custom_folder.add_folder.AddFolderResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.DateToAgeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.Message;
import com.senzecit.iitiimshaadi.model.api_rquest_model.about_me.AboutMeRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.contact_details.ContactDetailsRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.education_career.EducationCareerRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.family_detail.FamilyDetailRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.profile.BasicProfileRequest;
import com.senzecit.iitiimshaadi.model.api_rquest_model.subscriber.religious.ReligiousBackgroundRequest;
import com.senzecit.iitiimshaadi.model.exp_listview.ExpOwnProfileModel;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private static String TAG = "ExpandableListViewAdapter";
    private Context _context;
    LayoutInflater layoutInflater;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    AppPrefs prefs;
    RxNetworkingForObjectClass rxNetworkingClass;
    private List<SliderCheckModel> sliderCheckList;
    JSONObject object;
    JSONObject basicObject = null;

    public ExpandableListViewAdapter(Context context, List<String> listDataHeader,
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


        final String childText = (String) getChild(groupPosition, childPosition);
        try {
            basicObject = object.getJSONObject("basicData");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch (groupPosition) {
            case 0:
                switch (childPosition) {
                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout.setHint(childText);

                        EditText editText = convertView.findViewById(R.id.idlistitemET);
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                        //SetData - Name
                        editText.setText(basicObject.optString("name"));

                        if (!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getName()))
                            editText.setText(ExpOwnProfileModel.getInstance().getName());
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setName(editable.toString());
                            }
                        });

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);

                        TextView txtListChildHeader = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);
                        //SetData - Diet
                        txtListChild.setText(basicObject.optString("profile_created_for"));
                        txtListChild.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setProfile(editable.toString());
                            }
                        });

                        break;
                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild2 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild2.setText(childText);

                        TextView txtListChildHeader2 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader2.setText(childText);
                        formattedDate(txtListChild2, basicObject.optString("birth_date"));
                        txtListChild2.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setAge(editable.toString());
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

                        TextView txtListChildHeader3 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader3.setText(childText);

                        //SetData - Diet
                        txtListChild3.setText(basicObject.optString("diet"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getDiet()))
                            txtListChild3.setText(ExpOwnProfileModel.getInstance().getDiet());

                        txtListChild3.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setDiet(editable.toString());
                            }
                        });

                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showDiet(txtListChild3);
                            }
                        });

                        break;
                    case 4:
                        LayoutInflater infalInflater4 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater4.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild4 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild4.setText(childText);

                        TextView txtListChildHeader4 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader4.setText(childText);

                        //SetData - DOB
                        txtListChild4.setText("Test");
                        //SetData - DOB
                        if(basicObject.optString("birth_date") != null) {

                            String dateOfBirth = basicObject.optString("birth_date");
                            txtListChild4.setText(getDate(dateOfBirth));
                        }
                        txtListChild4.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setDate_Of_Birth(editable.toString());
                            }
                        });


                        break;
                    case 5:
                        LayoutInflater infalInflater5 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater5.inflate(R.layout.list_item, null);

                        final TextView txtListChild5 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild5.setText(childText);

                        final TextView txtListChildHeader5 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader5.setText(childText);
                        txtListChild5.setText(basicObject.optString("marital_status"));
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild5,100,50);
                                showMaritalStatus(txtListChild5);
                            }
                        });

                        //SetData - MaritalStatus

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getMarital_Status()))
                            txtListChild5.setText(ExpOwnProfileModel.getInstance().getMarital_Status());

                        txtListChild5.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setMarital_Status(editable.toString());
                            }
                        });

                        break;
                    case 6:
                        LayoutInflater infalInflater6 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater6.inflate(R.layout.list_item, null);

                        final TextView txtListChild6 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild6.setText(childText);

                        //SetData - Drink
                        txtListChild6.setText(basicObject.optString("drink"));

                        TextView txtListChildHeader6 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader6.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild6,100,50);
                                showDrink(txtListChild6);
                            }
                        });

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getDrink()))
                            txtListChild6.setText(ExpOwnProfileModel.getInstance().getDrink());

                        txtListChild6.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setDrink(editable.toString());
                            }
                        });

                        break;
                    case 7:
                        LayoutInflater infalInflater7 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater7.inflate(R.layout.list_item, null);

                        final TextView txtListChild7 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild7.setText(childText);

                        //SetData - Drink
                        txtListChild7.setText(basicObject.optString("smoke"));

                        TextView txtListChildHeader7 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader7.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild7,100,50);
                                showSmoke(txtListChild7);
                            }
                        });

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getSmoke()))
                            txtListChild7.setText(ExpOwnProfileModel.getInstance().getSmoke());

                        txtListChild7.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setSmoke(editable.toString());
                            }
                        });

                        break;
                    case 8:
                        LayoutInflater infalInflater8 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater8.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout8 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout8.setHint(childText);

                        EditText editText8 = convertView.findViewById(R.id.idlistitemET);
                        editText8.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                        //SetData - Name
                        editText8.setText(basicObject.optString("health_issue"));
                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getHealth_Issue()))
                            editText8.setText(ExpOwnProfileModel.getInstance().getHealth_Issue());

                        editText8.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setHealth_Issue(editable.toString());
                            }
                        });

                        break;
                    case 9:
                        LayoutInflater infalInflater9 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater9.inflate(R.layout.list_item, null);

                        final TextView txtListChild9 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild9.setText(childText);

                        //SetData - Height
                        txtListChild9.setText(basicObject.optString("height"));
                        TextView txtListChildHeader9 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader9.setText(childText);

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getHeight()))
                            txtListChild9.setText(ExpOwnProfileModel.getInstance().getHeight());

                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild8,100,50);
                                showHeight(txtListChild9);
                            }
                        });

                        txtListChild9.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setHeight(editable.toString());
                            }
                        });

                        break;
                    case 10:
                        LayoutInflater infalInflater10 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater10.inflate(R.layout.list_item, null);

                        final TextView txtListChild10 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild10.setText(childText);

                        TextView txtListChildHeader10 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader10.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showSelectableDialog(txtListChild9,100,50);
                                showInterests(txtListChild10);
                            }
                        });

                        StringBuilder builder = new StringBuilder();
                        try {
                            JSONArray jsonArray = basicObject.getJSONArray("interest");
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
                        txtListChild10.setText(builder);

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getInterests()))
                            txtListChild10.setText(ExpOwnProfileModel.getInstance().getInterests());

                        txtListChild10.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setInterests(editable.toString());
                            }
                        });

                        break;
                    case 11:
                        LayoutInflater infalInflater11 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater11.inflate(R.layout.submit_data_button, null);
                        Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                        saveChanges.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //do somethings.
                                saveChangesOfCase_0();
                            }
                        });
                        break;
                }
                break;
            case 1:
                switch (childPosition) {

                    case 0:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item, null);

                        final TextView txtListChild1 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild1.setText(childText);

                        TextView txtListChildHeader1 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader1.setText(childText);

                        //SetData - Religion
                        txtListChild1.setText(basicObject.optString("religion"));
                        if (TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getReligion()))
                            ExpOwnProfileModel.getInstance().setReligion(basicObject.optString("religion"));

                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild1,100,50);
                                showReligion(txtListChild1);
                            }
                        });

                        if (!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getReligion()))
                            txtListChild1.setText(ExpOwnProfileModel.getInstance().getReligion());

                        txtListChild1.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setReligion(txtListChild1.getText().toString());
                            }
                        });
//

                        break;
                    case 1:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item, null);

                        final TextView txtListChild2 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild2.setText(childText);

                        //SetData - Caste
                        txtListChild2.setText(basicObject.optString("caste"));
                        TextView txtListChildHeader2 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader2.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild2,100,50);
                                showCaste(txtListChild2);
                            }
                        });

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getCaste()))
                            txtListChild2.setText(ExpOwnProfileModel.getInstance().getCaste());

                        txtListChild2.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setCaste(editable.toString());
                            }
                        });


                        break;
                    case 2:
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.list_item, null);

                        final TextView txtListChild3 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild3.setText(childText);

                        //SetData - Mother Tounge
                        txtListChild3.setText(basicObject.optString("mother_tounge"));
                        TextView txtListChildHeader3 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader3.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild3,100,50);
                                showMotherTongue(txtListChild3);
                            }
                        });

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getMother_Tongue()))
                            txtListChild3.setText(ExpOwnProfileModel.getInstance().getMother_Name());

                        txtListChild3.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setMother_Tongue(editable.toString());
                            }
                        });

                        break;
                    case 3:
                        LayoutInflater infalInflater10 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater10.inflate(R.layout.submit_data_button, null);
                        Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                        saveChanges.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //do somethings.
                                saveChangesOfCase_1();
                            }
                        });
                        break;
                }
                break;
//CONTACT DETAIL
            case 2:
                switch (childPosition){

                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_non_editable, null);

                        final TextView txtListChild = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);

                        //SetData - MobileNo
                        txtListChild.setText(basicObject.optString("mobile_no"));
                        TextView txtListChildHeader = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader.setText(childText);

                        txtListChild.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setPhone_Number(editable.toString());
                            }
                        });

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item_numbertype, null);

                        TextInputLayout textInputLayout1 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout1.setHint(childText);

                        EditText editText1 = convertView.findViewById(R.id.idlistitemET);

                        //SetData - AlternateNo
                        editText1.setText(basicObject.optString("alternate_no"));
                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getAlternate_Number()))
                            editText1.setText(ExpOwnProfileModel.getInstance().getAlternate_Number());

                        editText1.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setAlternate_Number(editable.toString());

                            }
                        });

                        break;
                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout2 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout2.setHint(childText);

                        EditText editText2 = convertView.findViewById(R.id.idlistitemET);
                        editText2.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - PermanentAddress
                        editText2.setText(basicObject.optString("permanent_address"));
                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getPermanent_Address()))
                            editText2.setText(ExpOwnProfileModel.getInstance().getPermanent_Address());

                        editText2.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setPermanent_Address(editable.toString());

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

                        //SetData - PermanentCountry
                        txtListChild3.setText(basicObject.optString("permanent_country"));
                        TextView txtListChildHeader3 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader3.setText(childText);

                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild3,100,50);
                                showCountry(txtListChild3);
                            }
                        });

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getPermanent_Country()))
                            txtListChild3.setText(ExpOwnProfileModel.getInstance().getPermanent_Country());

                        txtListChild3.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setPermanent_Country(editable.toString());
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

                        //SetData - PermanentState
                        txtListChild4.setText(basicObject.optString("permanent_state"));
                        TextView txtListChildHeader4 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader4.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild4,100,50);
                                showPermanentState(txtListChild4);
                            }
                        });

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getPermanent_State()))
                            txtListChild4.setText(ExpOwnProfileModel.getInstance().getPermanent_State());

                        txtListChild4.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setPermanent_State(editable.toString());
                            }
                        });
                        break;
                    case 5:
                        LayoutInflater infalInflater5 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater5.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout5 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout5.setHint(childText);

                        EditText editText5 = convertView.findViewById(R.id.idlistitemET);
                        editText5.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - PermanentCity
                        editText5.setText(basicObject.optString("permanent_city"));
                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getPermanent_City()))
                            editText5.setText(ExpOwnProfileModel.getInstance().getPermanent_City());

                        editText5.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setPermanent_City(editable.toString());

                            }
                        });
                        break;
                    case 6:
                        LayoutInflater infalInflater6 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater6.inflate(R.layout.list_item_numbertype, null);

                        TextInputLayout textInputLayout6 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout6.setHint(childText);

                        EditText editText6 = convertView.findViewById(R.id.idlistitemET);
                        //SetData - PermanentZipcode
                        editText6.setText(basicObject.optString("permanent_zipcode"));
                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getZip_Code_Perm()))
                            editText6.setText(ExpOwnProfileModel.getInstance().getZip_Code_Perm());

                        editText6.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setZip_Code_Perm(editable.toString());

                            }
                        });

                        break;
                    case 7:
                        LayoutInflater infalInflater7 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater7.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout7 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout7.setHint(childText);

                        EditText editText7 = convertView.findViewById(R.id.idlistitemET);
                        editText7.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - CurrentAddress
                        editText7.setText(basicObject.optString("current_address"));
                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getCurrent_Address()))
                            editText7.setText(ExpOwnProfileModel.getInstance().getCurrent_Address());

                        editText7.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setCurrent_Address(editable.toString());

                            }
                        });
                        break;
                    case 8:
                        LayoutInflater infalInflater8 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater8.inflate(R.layout.list_item, null);

                        final TextView txtListChild8 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild8.setText(childText);

                        //SetData - CurrentCountry
                        txtListChild8.setText(basicObject.optString("current_country"));
                        TextView txtListChildHeader8 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader8.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild8,100,50);
                                showCountry(txtListChild8);
                            }
                        });

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getCurrent_Country()))
                            txtListChild8.setText(ExpOwnProfileModel.getInstance().getCurrent_Country());

                        txtListChild8.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setCurrent_Country(editable.toString());
                            }
                        });
                        break;
                    case 9:
                        LayoutInflater infalInflater9 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater9.inflate(R.layout.list_item, null);

                        final TextView txtListChild9 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild9.setText(childText);

                        //SetData - CurrentState

                        txtListChild9.setText(basicObject.optString("current_state"));

                        TextView txtListChildHeader9 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader9.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild9,100,50);
                                showCurrentState(txtListChild9);
                            }
                        });

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getCurrent_State()))
                            txtListChild9.setText(ExpOwnProfileModel.getInstance().getCurrent_State());

                        txtListChild9.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setCurrent_State(editable.toString());
                            }
                        });

                        break;
                    case 10:
                        LayoutInflater infalInflater10 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater10.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout10 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout10.setHint(childText);

                        EditText editText10 = convertView.findViewById(R.id.idlistitemET);
                        editText10.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - CurrentCity
                        editText10.setText(basicObject.optString("current_city"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getCurrent_City()))
                            editText10.setText(ExpOwnProfileModel.getInstance().getCurrent_City());

                        editText10.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setCurrent_City(editable.toString());

                            }
                        });
                        break;
                    case 11:
                        LayoutInflater infalInflater11 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater11.inflate(R.layout.list_item_numbertype, null);

                        TextInputLayout textInputLayout11 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout11.setHint(childText);

                        EditText editText11 = convertView.findViewById(R.id.idlistitemET);
                        editText11.setText(basicObject.optString("current_zipcode"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getZip_Code_Cur()))
                            editText11.setText(ExpOwnProfileModel.getInstance().getZip_Code_Cur());

                        editText11.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setZip_Code_Cur(editable.toString());

                            }
                        });
                        break;
                    case 12:
                        LayoutInflater infalInflater12 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater12.inflate(R.layout.submit_data_button, null);
                        Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                        saveChanges.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //do somethings.
                                saveChangesOfCase_2();
                            }
                        });
                        break;
                }
                break;
            case 3:
                switch (childPosition) {

                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout.setHint(childText);

                        EditText editText = convertView.findViewById(R.id.idlistitemET);
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - FatherName
                        editText.setText(basicObject.optString("father_name"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getFather_Name()))
                            editText.setText(ExpOwnProfileModel.getInstance().getFather_Name());

                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setFather_Name(editable.toString());

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
                        editText1.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - FatherOccupation
                        editText1.setText(basicObject.optString("father_occupation"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getFather_Occupation()))
                            editText1.setText(ExpOwnProfileModel.getInstance().getFather_Occupation());

                        editText1.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setFather_Occupation(editable.toString());

                            }
                        });
                        break;
                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout2 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout2.setHint(childText);

                        EditText editText2 = convertView.findViewById(R.id.idlistitemET);
                        editText2.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - MotherName
                        editText2.setText(basicObject.optString("mother_name"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getMother_Name()))
                            editText2.setText(ExpOwnProfileModel.getInstance().getMother_Name());

                        editText2.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setMother_Name(editable.toString());

                            }
                        });
                        break;
                    case 3:
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout3 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout3.setHint(childText);

                        EditText editText3 = convertView.findViewById(R.id.idlistitemET);
                        editText3.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - MotherOccupation
                        editText3.setText(basicObject.optString("mother_occupation"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getMother_Occupation()))
                            editText3.setText(ExpOwnProfileModel.getInstance().getMother_Occupation());

                        editText3.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setMother_Occupation(editable.toString());

                            }
                        });
                        break;
                    case 4:
                        LayoutInflater infalInflater4 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater4.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout4 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout4.setHint(childText);

                        EditText editText4 = convertView.findViewById(R.id.idlistitemET);
                        editText4.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - Sister
                        editText4.setText(basicObject.optString("sister"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getDetails_Sisters()))
                            editText4.setText(ExpOwnProfileModel.getInstance().getDetails_Sisters());
                        editText4.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setDetails_Sisters(editable.toString());

                            }
                        });

                        break;
                    case 5:
                        LayoutInflater infalInflater5 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater5.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout5 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout5.setHint(childText);

                        EditText editText5 = convertView.findViewById(R.id.idlistitemET);
                        editText5.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - Brother
                        editText5.setText(basicObject.optString("brother"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getDetails_Brothers()))
                            editText5.setText(ExpOwnProfileModel.getInstance().getDetails_Brothers());
                        editText5.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setDetails_Brothers(editable.toString());

                            }
                        });
                        break;
                    case 6:
                        LayoutInflater infalInflater6 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater6.inflate(R.layout.submit_data_button, null);
                        Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                        saveChanges.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //do somethings.
                                saveChangesOfCase_3();
                            }
                        });
                        break;
                }
                break;
            case 4:
                switch (childPosition) {

                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout.setHint(childText);

                        EditText editText = convertView.findViewById(R.id.idlistitemET);
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - Schooling
                        editText.setText(basicObject.optString("schooling"));


                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getSchooling()))
                            editText.setText(ExpOwnProfileModel.getInstance().getSchooling());

                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setSchooling(editable.toString());

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

                        //SetData - School Year

                        TextView txtListChildHeader1 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader1.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild9,100,50);
                                showYear(txtListChild1);
                            }
                        });

                        txtListChild1.setText(String.valueOf(basicObject.optInt("schooling_year")));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getSchooling_Year()))
                            txtListChild1.setText(ExpOwnProfileModel.getInstance().getSchooling_Year());

                        txtListChild1.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setSchooling_Year(editable.toString());
                            }
                        });

//                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
//                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                        convertView = infalInflater1.inflate(R.layout.list_item_numbertype, null);
//
//                        TextInputLayout textInputLayout1 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
//                        textInputLayout1.setHint(childText);
//
//                        EditText editText1 = convertView.findViewById(R.id.idlistitemET);
//                        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
//
//                        InputFilter[] FilterArray = new InputFilter[1];
//                        FilterArray[0] = new InputFilter.LengthFilter(4);
//                        editText1.setFilters(FilterArray);
//
//                        //SetData - SchoolingYear
//                        editText1.setText(String.valueOf(basicObject.optInt("schooling_year")));
//
//                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getSchooling_Year()))
//                            editText1.setText(ExpOwnProfileModel.getInstance().getSchooling_Year());
//
//                        editText1.addTextChangedListener(new TextWatcher() {
//                            @Override
//                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                            }
//
//                            @Override
//                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                            }
//
//                            @Override
//                            public void afterTextChanged(Editable editable) {
//                                ExpOwnProfileModel.getInstance().setSchooling_Year(editable.toString());
//
//                            }
//                        });

                        break;
                    case 2:
                        LayoutInflater infalInflater2 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater2.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout2 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout2.setHint(childText);

                        EditText editText2 = convertView.findViewById(R.id.idlistitemET);
                        editText2.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - Graduation
                        editText2.setText(basicObject.optString("graduation"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getGraduation()))
                            editText2.setText(ExpOwnProfileModel.getInstance().getGraduation());

                        editText2.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setGraduation(editable.toString());

                            }
                        });

                        break;
                    case 3:
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout3 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout3.setHint(childText);

                        EditText editText3 = convertView.findViewById(R.id.idlistitemET);
                        editText3.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - GraduationCollege
                        editText3.setText(basicObject.optString("graduation_college"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getGraduation_College()))
                            editText3.setText(ExpOwnProfileModel.getInstance().getGraduation_College());

                        editText3.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setGraduation_College(editable.toString());

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

                        //SetData - School Year

                        TextView txtListChildHeader4 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader4.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild9,100,50);
                                showYear(txtListChild4);
                            }
                        });

                        txtListChild4.setText(String.valueOf(basicObject.optInt("graduation_year")));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getGraduation_Year()))
                            txtListChild4.setText(ExpOwnProfileModel.getInstance().getGraduation_Year());

                        txtListChild4.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setGraduation_Year(editable.toString());
                            }
                        });

              /*          LayoutInflater infalInflater4 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater4.inflate(R.layout.list_item_numbertype, null);

                        TextInputLayout textInputLayout4 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout4.setHint(childText);

                        EditText editText4 = convertView.findViewById(R.id.idlistitemET);
                        editText4.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

                        InputFilter[] FilterArray1 = new InputFilter[1];
                        FilterArray1[0] = new InputFilter.LengthFilter(4);
                        editText4.setFilters(FilterArray1);

                        //SetData - GraduationYear
                        editText4.setText(String.valueOf(basicObject.optInt("graduation_year")));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getGraduation_Year()))
                            editText4.setText(ExpOwnProfileModel.getInstance().getGraduation_Year());

                        editText4.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setGraduation_Year(editable.toString());

                            }
                        });*/

                        break;
                    case 5:
                        LayoutInflater infalInflater5 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater5.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout5 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout5.setHint(childText);

                        EditText editText5 = convertView.findViewById(R.id.idlistitemET);
                        editText5.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - PostGraduation
                        editText5.setText(basicObject.optString("post_graduation"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getPost_Graduation()))
                            editText5.setText(ExpOwnProfileModel.getInstance().getPost_Graduation());

                        editText5.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setPost_Graduation(editable.toString());

                            }
                        });

                        break;
                    case 6:
                        LayoutInflater infalInflater6 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater6.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout6 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout6.setHint(childText);

                        EditText editText6 = convertView.findViewById(R.id.idlistitemET);
                        editText6.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - PostGraduationCollege
                        editText6.setText(basicObject.optString("post_graduation_college"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getPost_Graduation_College()))
                            editText6.setText(ExpOwnProfileModel.getInstance().getPost_Graduation());

                        editText6.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setPost_Graduation_College(editable.toString());

                            }
                        });

                        break;
                    case 7:


                        LayoutInflater infalInflater7 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater7.inflate(R.layout.list_item, null);

                        final TextView txtListChild7 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild7.setText(childText);

                        TextView txtListChildHeader7 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader7.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild9,100,50);
                                showYear(txtListChild7);
                            }
                        });

                        //SetData - School Year
                        txtListChild7.setText(String.valueOf(basicObject.optInt("post_graduation_year")));


                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getPost_Graduation_Year()))
                            txtListChild7.setText(ExpOwnProfileModel.getInstance().getPost_Graduation_Year());

                        txtListChild7.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setPost_Graduation_Year(editable.toString());
                            }
                        });



/*
                        LayoutInflater infalInflater7 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater7.inflate(R.layout.list_item_numbertype, null);

                        TextInputLayout textInputLayout7 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout7.setHint(childText);

                        EditText editText7 = convertView.findViewById(R.id.idlistitemET);

                        //SetData - PostGraduationYear
                        editText7.setText(String.valueOf(basicObject.optInt("post_graduation_year")));
                        editText7.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

                        InputFilter[] FilterArray2 = new InputFilter[1];
                        FilterArray2[0] = new InputFilter.LengthFilter(4);
                        editText7.setFilters(FilterArray2);

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getPost_Graduation_Year()))
                            editText7.setText(ExpOwnProfileModel.getInstance().getPost_Graduation_Year());

                        editText7.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setPost_Graduation_Year(editable.toString());

                            }
                        });
*/

                        break;
                    case 8:
                        LayoutInflater infalInflater8 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater8.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout8 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout8.setHint(childText);

                        EditText editText8 = convertView.findViewById(R.id.idlistitemET);
                        editText8.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - HighestEducation
                        editText8.setText(basicObject.optString("highest_education"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getHighest_Education()))
                            editText8.setText(ExpOwnProfileModel.getInstance().getHighest_Education());

                        editText8.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setHighest_Education(editable.toString());

                            }
                        });

                        break;
                    case 9:
                        LayoutInflater infalInflater9 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater9.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout9 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout9.setHint(childText);

                        EditText editText9 = convertView.findViewById(R.id.idlistitemET);
                        editText9.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - WorkingWith
                        editText9.setText(basicObject.optString("name_of_company"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getWorking_With()))
                            editText9.setText(ExpOwnProfileModel.getInstance().getWorking_With());

                        editText9.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setWorking_With(editable.toString());

                            }
                        });

                        break;
                    case 10:
                        LayoutInflater infalInflater10 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater10.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout10 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout10.setHint(childText);

                        EditText editText10 = convertView.findViewById(R.id.idlistitemET);
                        editText10.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - WorkingAs
                        editText10.setText(basicObject.optString("working_as"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getWorking_As()))
                            editText10.setText(ExpOwnProfileModel.getInstance().getWorking_As());

                        editText10.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setWorking_As(editable.toString());

                            }
                        });

                        break;
                    case 11:
                        LayoutInflater infalInflater11 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater11.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout11 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout11.setHint(childText);

                        EditText editText11 = convertView.findViewById(R.id.idlistitemET);
                        editText11.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - WorkingLocation
                        editText11.setText(basicObject.optString("job_location"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getWork_Location()))
                            editText11.setText(ExpOwnProfileModel.getInstance().getWork_Location());

                        editText11.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setWork_Location(editable.toString());

                            }
                        });

                        break;
                    case 12:
                        LayoutInflater infalInflater12 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater12.inflate(R.layout.list_item, null);

                        final TextView txtListChild12 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild12.setText(childText);

                        //SetData - AnnualIncome
                        txtListChild12.setText(basicObject.optString("annual_income"));

                        TextView txtListChildHeader12 = convertView
                                .findViewById(R.id.childItemTVheader);
                        txtListChildHeader12.setText(childText);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                showDialog(txtListChild12,100,50);
                                showAnnualIncome(txtListChild12);
                            }
                        });

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getAnnual_Income()))
                            txtListChild12.setText(ExpOwnProfileModel.getInstance().getAnnual_Income());

                        txtListChild12.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setAnnual_Income(editable.toString());
                            }
                        });

                        break;
                    case 13:
                        LayoutInflater infalInflater13 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater13.inflate(R.layout.list_item_secondtype, null);

                        TextInputLayout textInputLayout13 = convertView.findViewById(R.id.idTextInputLayout);
                        textInputLayout13.setHint(childText);

                        EditText editText13 = convertView.findViewById(R.id.idlistitemET);
                        editText13.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - LinkedIn
                        editText13.setText(basicObject.optString("linked_in"));

                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getLinkdIn_Url()))
                            editText13.setText(ExpOwnProfileModel.getInstance().getLinkdIn_Url());

                        editText13.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setLinkdIn_Url(editable.toString());

                            }
                        });

                        break;
                    case 14:
                        LayoutInflater infalInflater14 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater14.inflate(R.layout.submit_data_button, null);
                        Button saveChanges = convertView.findViewById(R.id.save_changes_button);
                        saveChanges.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //do somethings.
                                saveChangesOfCase_4();
                            }
                        });
                        break;
                }
                break;
            case 5:
                switch (childPosition){

                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_boxtype, null);

                        EditText editText = convertView.findViewById(R.id.idlistitemET);
                        editText.setHint("Say Something...");
//                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                        //SetData - AboutMe
                        editText.setText(basicObject.optString("about_me"));

                        if(TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getAbout_you()))
                            ExpOwnProfileModel.getInstance().setAbout_you(basicObject.optString("about_me"));


                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getAbout_you()))
                            editText.setText(ExpOwnProfileModel.getInstance().getAbout_you());

                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                ExpOwnProfileModel.getInstance().setAbout_you(editable.toString());

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
                                saveChangesOfCase_5();
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

    // GET and SET Value
    public void showDiet(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.diet_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }

    public void showMaritalStatus(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.marital_status_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }
    public void showMaritalStatusPt(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.marital_status_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showSelectableDialog(list, textView);
    }

    public void showDrink(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.yes_no_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }

    public void showSmoke(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.yes_no_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);

    }

    public void showHeight(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.height_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }

    public void showInterests(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.interest_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showSelectableDialog(list, textView);
    }

    //RELIGIOUS BACKGROUND
    public void showReligion(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.religion_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }

    public void showMotherTongue(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.mother_tongue_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }

    public void showYear(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.year_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }

    //EDUCATION & CARREER
    public void showAnnualIncome(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.ann_income_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }


    /** Network Operation */
    public void saveChangesOfCase_0() {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        String name = ExpOwnProfileModel.getInstance().getName();
        String Profile = ExpOwnProfileModel.getInstance().getProfile();
        String Age = ExpOwnProfileModel.getInstance().getAge();
        String Diet = ExpOwnProfileModel.getInstance().getDiet();
        String Date_Of_Birth = ExpOwnProfileModel.getInstance().getDate_Of_Birth();

        String Marital_Status = ExpOwnProfileModel.getInstance().getMarital_Status();
        String[] marital_statusArr = new String[1];
        marital_statusArr[0] = Marital_Status;
        String Drink = ExpOwnProfileModel.getInstance().getDrink();
        String Smoke = ExpOwnProfileModel.getInstance().getSmoke();
        String Health = ExpOwnProfileModel.getInstance().getHealth_Issue();
        String Height = ExpOwnProfileModel.getInstance().getHeight();
        String Interests = ExpOwnProfileModel.getInstance().getInterests();

//        Toast.makeText(_context, "Output : " + Interests, Toast.LENGTH_LONG).show();

        BasicProfileRequest request = new BasicProfileRequest();
        request.token = token;
        request.name = name;
        request.health_issue = Health;
        request.height = Height;
        request.diet = Diet;
        request.marital_status = Marital_Status;
        request.drink = Drink;
        request.smoke = Smoke;
        request.interest = Interests;
        if(NetworkClass.getInstance().checkInternet(_context) == true){

            ProgressClass.getProgressInstance().showDialog(_context);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<AddFolderResponse> call = apiInterface.sendBasicProfile(request);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {
                        AddFolderResponse serverResponse = response.body();
                        if(serverResponse.getMessage().getSuccess() != null) {
                            if (serverResponse.getMessage().getSuccess().equalsIgnoreCase("success")) {

                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", "Successful");
//                            communicator.saveChangesOfCaseI_0();
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
    public void saveChangesOfCase_1() {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String Religion = ExpOwnProfileModel.getInstance().getReligion();
        String Caste = ExpOwnProfileModel.getInstance().getCaste();
        String Mother_Tongue = ExpOwnProfileModel.getInstance().getMother_Tongue();

        ReligiousBackgroundRequest request = new ReligiousBackgroundRequest();
        request.token = token;
        request.religion = Religion;
        request.caste = Caste;
        request.mother_tounge = Mother_Tongue;
        if(NetworkClass.getInstance().checkInternet(_context) == true){

            ProgressClass.getProgressInstance().showDialog(_context);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<AddFolderResponse> call = apiInterface.sendReligiousBackground(request);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {
                        AddFolderResponse serverResponse = response.body();
                        if(serverResponse.getMessage().getSuccess() != null) {
                            if (serverResponse.getMessage().getSuccess().equalsIgnoreCase("success")) {

                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", "Successful");
//                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();
//                            communicator.saveChangesOfCaseI_1();

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
    public void saveChangesOfCase_2() {

        String Phone_Number = ExpOwnProfileModel.getInstance().getPhone_Number();
        String Alternate_Number = ExpOwnProfileModel.getInstance().getAlternate_Number();
        String Permanent_Address = ExpOwnProfileModel.getInstance().getPermanent_Address();
        String Permanent_Country = ExpOwnProfileModel.getInstance().getPermanent_Country();
        String Permanent_State = ExpOwnProfileModel.getInstance().getPermanent_State();
        String Permanent_City = ExpOwnProfileModel.getInstance().getPermanent_City();
        String Zip_Code_Perm = ExpOwnProfileModel.getInstance().getZip_Code_Perm();
        String Current_Address = ExpOwnProfileModel.getInstance().getCurrent_Address();
        String Current_Country = ExpOwnProfileModel.getInstance().getCurrent_Country();
        String Current_State = ExpOwnProfileModel.getInstance().getCurrent_State();
        String Current_City = ExpOwnProfileModel.getInstance().getCurrent_City();
        String Zip_Code_Cur = ExpOwnProfileModel.getInstance().getZip_Code_Cur();

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        ContactDetailsRequest request = new ContactDetailsRequest();
        request.token = token;
        request.mobile_no = Phone_Number;
        request.alternate_no = Alternate_Number;
        request.permanent_address = Permanent_Address;
        request.permanent_country = Permanent_Country;
        request.permanent_state = Permanent_State;
        request.permanent_city = Permanent_City;
        request.permanent_zipcode = Zip_Code_Perm;
        request.current_address = Current_Address;
        request.current_country = Current_Country;
        request.current_state = Current_State;
        request.current_city = Current_City;
        request.current_zipcode = Zip_Code_Cur;

        if(NetworkClass.getInstance().checkInternet(_context) == true){

            ProgressClass.getProgressInstance().showDialog(_context);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<AddFolderResponse> call = apiInterface.sendContactDetails(request);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {
                        AddFolderResponse serverResponse = response.body();
                        if(serverResponse.getMessage().getSuccess() != null) {
                            if (serverResponse.getMessage().getSuccess().equalsIgnoreCase("success")) {

                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", "Successful");
//                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();
//                            communicator.saveChangesOfCaseI_2();

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
    public void saveChangesOfCase_3() {

        String Father_Name = ExpOwnProfileModel.getInstance().getFather_Name();
        String Father_Occupation = ExpOwnProfileModel.getInstance().getFather_Occupation();
        String Mother_Name = ExpOwnProfileModel.getInstance().getMother_Name();
        String Mother_Occupation = ExpOwnProfileModel.getInstance().getMother_Occupation();
        String Details_Sisters = ExpOwnProfileModel.getInstance().getDetails_Sisters();
        String Details_Brothers = ExpOwnProfileModel.getInstance().getDetails_Brothers();

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        FamilyDetailRequest request = new FamilyDetailRequest();
        request.token = token;
        request.father_name = Father_Name;
        request.father_occupation = Father_Occupation;
        request.mother_name = Mother_Name;
        request.mother_occupation = Mother_Occupation;
        request.brother = Details_Brothers;
        request.sister = Details_Sisters;

        if(NetworkClass.getInstance().checkInternet(_context) == true){

            ProgressClass.getProgressInstance().showDialog(_context);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<AddFolderResponse> call = apiInterface.sendFamilyDetails(request);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {
                        AddFolderResponse serverResponse = response.body();
                        if(serverResponse.getMessage().getSuccess() != null) {
                            if (serverResponse.getMessage().getSuccess().equalsIgnoreCase("success")) {

                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", "Successful");
//                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();
//                            communicator.saveChangesOfCaseI_3();

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
    public void saveChangesOfCase_4() {

        String Schooling = ExpOwnProfileModel.getInstance().getSchooling();
        String Schooling_Year = ExpOwnProfileModel.getInstance().getSchooling_Year();
        String Graduation = ExpOwnProfileModel.getInstance().getGraduation();
        String Graduation_College = ExpOwnProfileModel.getInstance().getGraduation_College();
        String Graduation_Year = ExpOwnProfileModel.getInstance().getGraduation_Year();
        String Post_Graduation = ExpOwnProfileModel.getInstance().getPost_Graduation();
        String Post_Graduation_College = ExpOwnProfileModel.getInstance().getPost_Graduation_College();
        String Post_Graduation_Year = ExpOwnProfileModel.getInstance().getPost_Graduation_Year();
        String Highest_Education = ExpOwnProfileModel.getInstance().getHighest_Education();
        String Working_With = ExpOwnProfileModel.getInstance().getWorking_With();
        String Working_As = ExpOwnProfileModel.getInstance().getWorking_As();
        String Work_Location = ExpOwnProfileModel.getInstance().getWork_Location();
        String Annual_Income = ExpOwnProfileModel.getInstance().getAnnual_Income();
        String LinkdIn_Url = ExpOwnProfileModel.getInstance().getLinkdIn_Url();

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        EducationCareerRequest request = new EducationCareerRequest();
        request.token = token;
        request.schooling = Schooling;
        request.schooling_year = Schooling_Year;
        request.graduation = Graduation;
        request.graduation_year = Graduation_Year;
        request.graduation_college = Graduation_College;
        request.post_graduation = Post_Graduation;
        request.post_graduation_year = Post_Graduation_Year;
        request.post_graduation_college = Post_Graduation_College;
        request.highest_education = Highest_Education;
        request.working_as = Working_As;
        request.job_location = Work_Location;
        request.name_of_company = Working_With;
        request.annual_income = Annual_Income;
        request.linked_in = LinkdIn_Url;

        if(NetworkClass.getInstance().checkInternet(_context) == true){

            ProgressClass.getProgressInstance().showDialog(_context);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<AddFolderResponse> call = apiInterface.sendEducationCareer(request);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {
                        AddFolderResponse serverResponse = response.body();
                        if(serverResponse.getMessage().getSuccess() != null) {
                            if (serverResponse.getMessage().getSuccess().equalsIgnoreCase("success")) {

                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", "Successful");
//                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();
//                            communicator.saveChangesOfCaseI_4();

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
    public void saveChangesOfCase_5() {


        String About_you = ExpOwnProfileModel.getInstance().getAbout_you().trim();
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(!TextUtils.isEmpty(About_you)) {
        AboutMeRequest request = new AboutMeRequest();
        request.token = token;
        request.about_me = About_you;

        if(NetworkClass.getInstance().checkInternet(_context) == true){

            ProgressClass.getProgressInstance().showDialog(_context);
            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<AddFolderResponse> call = apiInterface.sendAboutMe(request);
            call.enqueue(new Callback<AddFolderResponse>() {
                @Override
                public void onResponse(Call<AddFolderResponse> call, Response<AddFolderResponse> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful()) {
                        AddFolderResponse serverResponse = response.body();
                        if(serverResponse.getMessage().getSuccess() != null) {
                            if (serverResponse.getMessage().getSuccess().equalsIgnoreCase("success")) {

                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", "Successful");
//                            Toast.makeText(_context, "Success", Toast.LENGTH_SHORT).show();
//                            communicator.saveChangesOfCaseI_5();

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


        }else {
            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", AppMessage.BLANK_DATA);
        }

    }

    public void showCountry(final TextView textView) {

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
                            showDialog(countryList, textView);
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

    public void showPermanentState(final TextView textView) {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String country = ExpOwnProfileModel.getInstance().getPermanent_Country();
        if(NetworkClass.getInstance().checkInternet(_context) == true){

        ProgressClass.getProgressInstance().showDialog(_context);
        AndroidNetworking.post("https://iitiimshaadi.com/api/states_acc_country.json")
                .addBodyParameter("token", token)
                .addBodyParameter("country", country)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
                        try {
                            JSONArray jsonArray = response.getJSONArray("allStates");
                            List<String> stateList = new ArrayList<>();
//                            if(jsonArray.length() > 0){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String sState = jsonArray.getString(i);
                                stateList.add(sState);
                            }
                            showDialog(stateList, textView);
                        /*}else {
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.cast_not_found);
                        }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.state_not_found);
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
    public void showCurrentState(final TextView textView) {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String country = ExpOwnProfileModel.getInstance().getCurrent_Country();
        if(NetworkClass.getInstance().checkInternet(_context) == true){

            ProgressClass.getProgressInstance().showDialog(_context);
        AndroidNetworking.post("https://iitiimshaadi.com/api/states_acc_country.json")
                .addBodyParameter("token", token)
                .addBodyParameter("country", country)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        ProgressClass.getProgressInstance().stopProgress();
                        try {
                            JSONArray jsonArray = response.getJSONArray("allStates");
                            List<String> stateList = new ArrayList<>();
//                            if(jsonArray.length() > 0){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String sState = jsonArray.getString(i);
                                stateList.add(sState);
                            }
                            showDialog(stateList, textView);
                        /*}else {
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.cast_not_found);
                        }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.state_not_found);
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

    public void showCaste(final TextView textView) {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String religion = ExpOwnProfileModel.getInstance().getReligion();

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
                            if(jsonArray.length() > 0){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String country = jsonArray.getString(i);
                                casteList.add(country);
                            }
                            showDialog(casteList, textView);
                        }else {
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.cast_not_found);
                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    public void formattedDate(TextView tv, String _date) {

//        String _date = "1988-08-28";
        try {

            APIInterface apiInterface = APIClient.getClient(CONSTANTS.BASE_URL).create(APIInterface.class);
            Call<DateToAgeResponse> call = apiInterface.dateToAge(_date);
//            ProgressClass.getProgressInstance().showDialog(mContext);
            call.enqueue(new Callback<DateToAgeResponse>() {
                @Override
                public void onResponse(Call<DateToAgeResponse> call, Response<DateToAgeResponse> response) {
                    if (response.isSuccessful()) {
//                        ProgressClass.getProgressInstance().stopProgress();
                        Message message = response.body().getMessage();
                        try {
                            if (message != null) {
                                tv.setText(response.body().getAge());
                            } else {
                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", " Check Religion selected!");
                            }
                        } catch (NullPointerException npe) {
                            Log.e("TAG", "#Error : " + npe, npe);
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", " Date Format not correct");
                        }
                    }
                }

                @Override
                public void onFailure(Call<DateToAgeResponse> call, Throwable t) {
                    call.cancel();
                    ProgressClass.getProgressInstance().stopProgress();
                    Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (NullPointerException npe) {
            Log.e("TAG", "#Error : " + npe, npe);
            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", "Religion not seletced");
        }

    }

    public static String getDate(String _Date){

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fmt2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = fmt.parse(_Date);
            return fmt2.format(date);
        }
        catch(ParseException pe) {
            return "Date";
        }
    }

    public static String removeLastChar(String s) {
        if (s == null || s.length() == 0 || s.equalsIgnoreCase("--") || s.equalsIgnoreCase("-")) {
            return s = "";
        }
        String rawString = s.substring(0, s.length()-1);
        return rawString;
    }


}
