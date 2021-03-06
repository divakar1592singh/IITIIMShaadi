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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.api.APIClient;
import com.senzecit.iitiimshaadi.api.APIInterface;
import com.senzecit.iitiimshaadi.model.api_response_model.common.SliderCheckModel;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.DateToAgeResponse;
import com.senzecit.iitiimshaadi.model.api_response_model.date_to_age.Message;
import com.senzecit.iitiimshaadi.model.commons.OwnBasicWebRequest;
import com.senzecit.iitiimshaadi.model.commons.PartnerBasicWebRequest;
import com.senzecit.iitiimshaadi.model.commons.PartnerEduWebRequest;
import com.senzecit.iitiimshaadi.model.commons.PartnerGroomWebRequest;
import com.senzecit.iitiimshaadi.model.commons.PartnerReligiousWebRequest;
import com.senzecit.iitiimshaadi.model.commons.PostAuthWebRequest;
import com.senzecit.iitiimshaadi.model.exp_listview.ExpOwnProfileModel;
import com.senzecit.iitiimshaadi.model.exp_listview.ExpPartnerProfileModel;
import com.senzecit.iitiimshaadi.sliderView.with_list.SliderDialogListLayoutAdapter;
import com.senzecit.iitiimshaadi.sliderView.with_list.SliderDialogListLayoutModel;
import com.senzecit.iitiimshaadi.sliderView.with_selection.SliderDialogCheckboxLayoutAdapter2;
import com.senzecit.iitiimshaadi.sliderView.with_selection.SliderDialogCheckboxLayoutModel;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CircleImageView;
import com.senzecit.iitiimshaadi.utils.DataHandlingClass;
import com.senzecit.iitiimshaadi.utils.Navigator;
import com.senzecit.iitiimshaadi.utils.NetworkClass;
import com.senzecit.iitiimshaadi.utils.alert.AlertDialogSingleClick;
import com.senzecit.iitiimshaadi.utils.alert.NetworkDialogHelper;
import com.senzecit.iitiimshaadi.utils.alert.ProgressClass;
import com.senzecit.iitiimshaadi.utils.AppMessage;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;
import com.senzecit.iitiimshaadi.viewController.AlbumActivity;
import com.senzecit.iitiimshaadi.viewController.ProfileActivity;
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

/**
 * Created by ravi on 8/11/17.
 */

public class ExpListViewSubsAdapter extends BaseExpandableListAdapter {

    private static final String TAG = "ExpListViewSubsAdapter";
    private Context _context;
    LayoutInflater layoutInflater;
    private List<String> _listDataHeader; // header titles
    private HashMap<String, List<String>> _listDataChild;
    AppPrefs prefs;
    private List<SliderCheckModel> sliderCheckList;
    OnExpLvSubsItemClickListener communicator;
    TextView mEmailVerifyTV, mMobVerifyTV, mDocumentsVerifyTV, mProofVerifyTV;
    CircleImageView mProfileCIV;
    JSONObject object;
    JSONObject basicObject = null;
    JSONObject partnerJson = null;
    PostAuthWebRequest request;

    public ExpListViewSubsAdapter(Context context, List<String> listDataHeader,
                                  HashMap<String, List<String>> listChildData,
                                  JSONObject object) {
        this._context = context;
        layoutInflater = LayoutInflater.from(_context);
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.object = object;
        prefs = AppController.getInstance().getPrefs();
        request = new PostAuthWebRequest();

        try{
            communicator = (OnExpLvSubsItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

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

        try {
            partnerJson = object.getJSONObject("partnerBasicData");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        switch (groupPosition) {
            case 2:
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
            case 3:
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
            case 4:
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
            case 5:
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
            case 6:
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


//                        LayoutInflater infalInflater4 = (LayoutInflater) this._context
//                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                        convertView = infalInflater4.inflate(R.layout.list_item_numbertype, null);
//
//                        TextInputLayout textInputLayout4 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
//                        textInputLayout4.setHint(childText);
//
//                        EditText editText4 = convertView.findViewById(R.id.idlistitemET);
//                        editText4.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
//
//                        InputFilter[] FilterArray1 = new InputFilter[1];
//                        FilterArray1[0] = new InputFilter.LengthFilter(4);
//                        editText4.setFilters(FilterArray1);
//                        //SetData - GraduationYear
//                        editText4.setText(String.valueOf(basicObject.optInt("graduation_year")));
//
//                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getGraduation_Year()))
//                            editText4.setText(ExpOwnProfileModel.getInstance().getGraduation_Year());
//
//                        editText4.addTextChangedListener(new TextWatcher() {
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
//                                ExpOwnProfileModel.getInstance().setGraduation_Year(editable.toString());
//
//                            }
//                        });

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


//                        LayoutInflater infalInflater7 = (LayoutInflater) this._context
//                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                        convertView = infalInflater7.inflate(R.layout.list_item_numbertype, null);
//
//                        TextInputLayout textInputLayout7 = (TextInputLayout) convertView.findViewById(R.id.idTextInputLayout);
//                        textInputLayout7.setHint(childText);
//
//                        EditText editText7 = convertView.findViewById(R.id.idlistitemET);
////                        editText7.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
//
//                        //SetData - PostGraduationYear
//                        editText7.setText(String.valueOf(basicObject.optInt("post_graduation_year")));
//                        editText7.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
//
//                        InputFilter[] FilterArray2 = new InputFilter[1];
//                        FilterArray2[0] = new InputFilter.LengthFilter(4);
//                        editText7.setFilters(FilterArray2);
//
//                        if(!TextUtils.isEmpty(ExpOwnProfileModel.getInstance().getPost_Graduation_Year()))
//                            editText7.setText(ExpOwnProfileModel.getInstance().getPost_Graduation_Year());
//
//                        editText7.addTextChangedListener(new TextWatcher() {
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
//                                ExpOwnProfileModel.getInstance().setPost_Graduation_Year(editable.toString());
//
//                            }
//                        });

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
            case 7:
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

                /** SEARCH PARTNER*/

            case 9:
                //Toast.makeText(_context, "0", //Toast.LENGTH_LONG).show();
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
            case 10:
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
                        txtListChild.setText(partnerJson.optString("prefered_partner_religion"));

                        break;
                    case 1:
                        LayoutInflater infalInflater1 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater1.inflate(R.layout.list_item, null);

                        final TextView txtListChild1 = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild1.setText(childText);

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
            case 11:
                //Toast.makeText(_context, "2", //Toast.LENGTH_LONG).show();
                switch (childPosition){
                    case 0:
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item, null);

                        final TextView txtListChild = convertView
                                .findViewById(R.id.childItemTV);
                        txtListChild.setText(childText);

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
            case 12:
                switch (childPosition){
                    case 0:
                        //Toast.makeText(_context, "3", //Toast.LENGTH_LONG).show();
                        LayoutInflater infalInflater = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater.inflate(R.layout.list_item_boxtype, null);

                        EditText editText = convertView.findViewById(R.id.idlistitemET);
                        editText.setHint("Say something...");

                        editText.setText(partnerJson.optString("choice_of_partner"));

                        if(TextUtils.isEmpty(ExpPartnerProfileModel.getInstance().getChoice_of_Groom()))
                            ExpPartnerProfileModel.getInstance().setChoice_of_Groom(partnerJson.optString("choice_of_partner"));

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

            if(groupPosition == 0) {

                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.row_subscriber_top, null);

                mProfileCIV = convertView.findViewById(R.id.idProfileCIV);
                Glide.with(_context).load(DataHandlingClass.getInstance().getProfilePicName()).into(mProfileCIV);
                handleHeaderData(convertView);


                return convertView;
            }
            else if( groupPosition == 1) {

                LayoutInflater infalInflater1 = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater1.inflate(R.layout.row_subs_text_layout, null);

                return convertView;
            }else if( groupPosition == 8) {
                LayoutInflater infalInflater2 = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater2.inflate(R.layout.row_group_title_layout, null);

                return convertView;
            }else {

                    String headerTitle = (String) getGroup(groupPosition);
                        LayoutInflater infalInflater3 = (LayoutInflater) this._context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = infalInflater3.inflate(R.layout.list_group, null);

                    TextView lblListHeader3 = convertView
                            .findViewById(R.id.profileGroupTV);
                    lblListHeader3.setTypeface(null, Typeface.NORMAL);
                    if(headerTitle.endsWith("PT")){
                        lblListHeader3.setText(headerTitle.substring(0, headerTitle.length() - 2));
                    }else {
                        lblListHeader3.setText(headerTitle);
                    }
                return convertView;
        }

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
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                SliderDialogListLayoutModel model = new SliderDialogListLayoutModel();
                model.setName(dataList.get(i));
                models.add(model);
            }
        } else {
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
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                SliderDialogCheckboxLayoutModel model = new SliderDialogCheckboxLayoutModel();
                model.setName(dataList.get(i));
                models.add(model);
                sliderCheckList.add(new SliderCheckModel(i, false));
            }
        } else {
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

                    if (checkBox.isChecked()) {

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

    public void showYear(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.year_ar);
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

    //EDUCATION & CARREER
    public void showAnnualIncome(TextView textView) {

        String[] ar = _context.getResources().getStringArray(R.array.ann_income_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showDialog(list, textView);
    }

    /**
     * Network Operation
     */

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

        OwnBasicWebRequest request = new OwnBasicWebRequest();
        request.token = token;
        request.name = name;
        request.health_issue = Health;
        request.height = Height;
        request.diet = Diet;
        request.marital_status = Marital_Status;
        request.drink = Drink;
        request.smoke = Smoke;
        request.interest = Interests;

        communicator.onProfileSubmission(CONSTANTS.BASIC_LIFESTYLE, request, CONSTANTS.METHOD_2);

    }
    public void saveChangesOfCase_1() {

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String Religion = ExpOwnProfileModel.getInstance().getReligion();
        String Caste = ExpOwnProfileModel.getInstance().getCaste();
        String Mother_Tongue = ExpOwnProfileModel.getInstance().getMother_Tongue();

        request.token = token;
        request.religion = Religion;
        request.caste = Caste;
        request.mother_tounge = Mother_Tongue;

        communicator.onProfileSubmission(CONSTANTS.RELIGIOUS_BACKGROUND_POST_URL, request, CONSTANTS.METHOD_2);

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

        request.token = token;
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

        communicator.onProfileSubmission(CONSTANTS.CONTACT_DETAILS, request, CONSTANTS.METHOD_2);

    }
    public void saveChangesOfCase_3() {

        String Father_Name = ExpOwnProfileModel.getInstance().getFather_Name();
        String Father_Occupation = ExpOwnProfileModel.getInstance().getFather_Occupation();
        String Mother_Name = ExpOwnProfileModel.getInstance().getMother_Name();
        String Mother_Occupation = ExpOwnProfileModel.getInstance().getMother_Occupation();
        String Details_Sisters = ExpOwnProfileModel.getInstance().getDetails_Sisters();
        String Details_Brothers = ExpOwnProfileModel.getInstance().getDetails_Brothers();

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        request.token = token;
        request.father_name = Father_Name;
        request.father_occupation = Father_Occupation;
        request.mother_name = Mother_Name;
        request.mother_occupation = Mother_Occupation;
        request.brother = Details_Brothers;
        request.sister = Details_Sisters;

        communicator.onProfileSubmission(CONSTANTS.FAMILY_DETAILS, request, CONSTANTS.METHOD_2);

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

        communicator.onProfileSubmission(CONSTANTS.EDUCATION_CAREER, request, CONSTANTS.METHOD_2);

    }
    public void saveChangesOfCase_5() {

        String About_you = ExpOwnProfileModel.getInstance().getAbout_you().trim();
        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        if(!TextUtils.isEmpty(About_you)) {
            request.token = token;
            request.about_me = About_you;

            communicator.onProfileSubmission(CONSTANTS.ABOUT_ME, request, CONSTANTS.METHOD_2);
        }else {
            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", AppMessage.BLANK_DATA);
        }

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
//                if ((Integer.parseInt(Maximum_Age) - Integer.parseInt(Minimum_Age)) <= 5 ) {
                    if ((getHeightInCM(Max_Height) - getHeightInCM(Min_Height)) > 0) {

                    PartnerBasicWebRequest request = new PartnerBasicWebRequest();
                    request.token = token;
                    request.prefered_partner_min_age = Minimum_Age;
                    request.prefered_partner_max_age = Maximum_Age;
                    request.prefered_partner_height = Min_Height;
                    request.prefered_partner_height_max = Max_Height;
                    request.prefered_partner_marital_status = Marital_Status;

                    communicator.onProfileSubmission(CONSTANTS.BASIC_LIFESTYLE_PT_PATH, request, CONSTANTS.METHOD_2);

                    } else {
                        AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", AppMessage.HEIGHT_DIFF_ERROR_INFO);
                    }
//                } else {
//                    AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", AppMessage.AGE_DIFF_5_INFO);
//                }
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

        PartnerReligiousWebRequest request = new PartnerReligiousWebRequest();
        request.token = token;
        request.prefered_partner_religion = Preferred_Religion;
        request.prefered_partner_caste = Preferred_Caste;
        request.prefered_partner_country = Preferred_Country;

        communicator.onProfileSubmission(CONSTANTS.RELIGIOUS_BACKGROUND_PT_PATH, request, CONSTANTS.METHOD_2);

    }
    public void saveChangesOfCasePt_2(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);

        String Preferred_Education = ExpPartnerProfileModel.getInstance().getPreferred_Education();

        String[] Preferred_EducationArr = new String[1];
        Preferred_EducationArr[0] = Preferred_Education;

        PartnerEduWebRequest request = new PartnerEduWebRequest();
        request.token = token;
        request.prefered_partner_education = Preferred_Education;

        communicator.onProfileSubmission(CONSTANTS.EDUCATION_CAREER_PT_PATH, request, CONSTANTS.METHOD_2);


    }
    public void saveChangesOfCasePt_3(){

        String token = prefs.getString(CONSTANTS.LOGGED_TOKEN);
        String Choice_of_Groom = ExpPartnerProfileModel.getInstance().getChoice_of_Groom().trim();

        if(!TextUtils.isEmpty(Choice_of_Groom)) {
        PartnerGroomWebRequest request = new PartnerGroomWebRequest();
        request.token = token;
        request.choice_of_partner = Choice_of_Groom;

        communicator.onProfileSubmission(CONSTANTS.CHOICE_OF_PARTNER_PATH, request, CONSTANTS.METHOD_2);

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
                            if(jsonArray.length() > 0) {
                                List<String> stateList = new ArrayList<>();
//                            if(jsonArray.length() > 0){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String sState = jsonArray.getString(i);
                                    stateList.add(sState);
                                }
                                showDialog(stateList, textView);
                            }else {
                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert","Select Country");
                            }
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
                            if(jsonArray.length() > 0) {
                            List<String> stateList = new ArrayList<>();
//                            if(jsonArray.length() > 0){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String sState = jsonArray.getString(i);
                                stateList.add(sState);
                            }
                            showDialog(stateList, textView);

                        }else {
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert","Select Country");
                        }
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
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String country = jsonArray.getString(i);
                                casteList.add(country);
                            }
                            showDialog(casteList, textView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.religion_error_msg);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.cast_not_found);
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
                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.religion_error_msg);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        ProgressClass.getProgressInstance().stopProgress();
                        AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", CONSTANTS.cast_not_found);
                    }
                });

        }else {
            NetworkDialogHelper.getInstance().showDialog(_context);
        }
    }

    public void formattedDate(TextView tv, String _date) {

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
//                                AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", " Check Religion selected!");
                            }
                        } catch (NullPointerException npe) {
                            Log.e("TAG", "#Error : " + npe, npe);
//                            AlertDialogSingleClick.getInstance().showDialog(_context, "Alert", " Date Format not correct");
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

    public static String getDate(String _Date) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fmt2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = fmt.parse(_Date);
            return fmt2.format(date);
        } catch (ParseException pe) {

            return "Date";
        }
    }

    /** SEARCH PARTNER*/
    public void showEducation(TextView textView){

        String[] ar = _context.getResources().getStringArray(R.array.education_ar);
        List<String> list = new ArrayList<String>(Arrays.asList(ar));
        showSelectableDialog(list, textView);
    }

    /** Handle Header */
    public void handleHeaderData(View convertView){

        //INIT SECTION

        TextView mUsrNameTV = convertView.findViewById(R.id.idUserNameTV);
        TextView mUsrIdTV = convertView.findViewById(R.id.idUserId);
        ProgressBar mProgress = convertView.findViewById(R.id.idprogress);
        TextView mProfilepercTV = convertView.findViewById(R.id.idProfilePercTV);

        LinearLayout albumLayout = convertView.findViewById(R.id.idAlbumLayout);
        ImageView mAlbumIV = convertView.findViewById(R.id.idAlbum);

        mEmailVerifyTV = convertView.findViewById(R.id.idEmailVerify);
        mMobVerifyTV = convertView.findViewById(R.id.idMobVerify);
        mDocumentsVerifyTV = convertView.findViewById(R.id.idDocumentsVerify);
        mProofVerifyTV = convertView.findViewById(R.id.idProofVerify);

        // DATA PARSING SECTION

        String userId = prefs.getString(CONSTANTS.LOGGED_USERID);
//        String profileUri = CONSTANTS.IMAGE_AVATAR_URL+userId+"/"+prefs.getString(CONSTANTS.LOGGED_USER_PIC);
        try {
            basicObject = object.getJSONObject("basicData");
        } catch (JSONException jse) {
            Log.e(TAG, "Error : "+jse, jse);
        } catch (NullPointerException npe) {
            Log.e(TAG, "Error : "+npe, npe);
        }

        try {
            partnerJson = object.getJSONObject("partnerBasicData");
        } catch (JSONException jse) {
            Log.e(TAG, "Error : "+jse, jse);
        } catch (NullPointerException npe) {
            Log.e(TAG, "Error : "+npe, npe);
        }


        try {
            String profileUri = CONSTANTS.IMAGE_AVATAR_URL + userId + "/" + basicObject.optString("profile_image");
            Glide.with(_context).load(profileUri).error(DataHandlingClass.getInstance().getProfilePicName()).into(mProfileCIV);
            System.out.println(profileUri);

            int profileCompletion = basicObject.optInt("profile_complition");
            mProgress.setProgress(profileCompletion);
            mProfilepercTV.setText(String.valueOf(profileCompletion)+"%");

//            if (!TextUtils.isEmpty(profileUri)) {
//            }
        }catch (NullPointerException npe){
            Log.e(TAG, " #Error : "+npe, npe);
            Toast.makeText(_context, "Image Not Found", Toast.LENGTH_SHORT).show();
        }
        String userName = prefs.getString(CONSTANTS.LOGGED_USERNAME);
        mUsrNameTV.setText(new StringBuilder("@").append(userName));
        mUsrIdTV.setText(new StringBuilder("@").append(userId));

        /**  Verification Status */
        try{
            String userType = prefs.getString(CONSTANTS.LOGGED_USER_TYPE);
            if(userType.equalsIgnoreCase("subscriber_viewer")) {

                setVerificationStatus(true, true, true, true );

            }else if(userType.equalsIgnoreCase("subscriber")){

                String email_verified = object.optString("emailStatus");
                int mob_verified = object.optInt("mobileStatus");
//                int biodata_verified = verifiedObject.getInt("biodata_status");
                int doc_verified = object.optInt("document_verified");
                int idProof_verified = object.optInt("identity_proof_verified");

                boolean email = email_verified.equalsIgnoreCase("Yes");
                boolean mob = mob_verified == 1;
//                boolean bioData = biodata_verified == 1?true:false;
                boolean doc = doc_verified == 1;
                boolean idProof = idProof_verified == 1;

                setVerificationStatus(email, mob, doc, idProof);


                setVerificationStatus(email, mob, doc, idProof);
            }

        }catch (NullPointerException npe){
            Log.e(TAG, " #Error : "+npe, npe);
        }

        //CLICK EVENT
        mProfileCIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.getClassInstance().navigateToActivity(_context, ProfileActivity.class);
            }
        });
        albumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.getClassInstance().navigateToActivity(_context, AlbumActivity.class);
            }
        });

        mEmailVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                alertDialogEmail();
                communicator.verifiyEmail();
            }
        });
        mMobVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                alertDialogMobile();
                communicator.verifiyMob();
            }
        });
        mDocumentsVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                alertDialogDocuments();
                communicator.verifiyDoc();
            }
        });
        mProofVerifyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                alertDialogIDProof();
                communicator.verifiyIDProof();
            }
        });

    }

    public void setVerificationStatus(boolean email, boolean mob, boolean doc, boolean idProof){


        if(email == true){
            mEmailVerifyTV.setText("Email Verified");
            mEmailVerifyTV.setBackgroundResource(R.drawable.round_view_green_border);
            mEmailVerifyTV.setEnabled(false);
        }else if (email == false){
            mEmailVerifyTV.setText("Email Unverified");
            mEmailVerifyTV.setBackgroundResource(R.drawable.round_view_yellow_border);
            mEmailVerifyTV.setEnabled(true);
        }

        if(mob == true){
            mMobVerifyTV.setText("Mobile Verified");
            mMobVerifyTV.setBackgroundResource(R.drawable.round_view_green_border);
            mMobVerifyTV.setEnabled(false);
        }else if (mob == false){
            mMobVerifyTV.setText("Mobile Unverified");
            mMobVerifyTV.setBackgroundResource(R.drawable.round_view_yellow_border);
            mMobVerifyTV.setEnabled(true);
        }

        if(doc == true){
            mDocumentsVerifyTV.setText("Doc Verified");
            mDocumentsVerifyTV.setBackgroundResource(R.drawable.round_view_green_border);
            mDocumentsVerifyTV.setEnabled(false);
        }else if (doc == false){
            mDocumentsVerifyTV.setText("Doc Unverified");
            mDocumentsVerifyTV.setBackgroundResource(R.drawable.round_view_yellow_border);
            mDocumentsVerifyTV.setEnabled(true);
        }

        if(idProof == true){
            mProofVerifyTV.setText("ID Proof Verified");
            mProofVerifyTV.setBackgroundResource(R.drawable.round_view_green_border);
            mProofVerifyTV.setEnabled(false);
        }else if (idProof == false){
            mProofVerifyTV.setText("ID Proof Unverified");
            mProofVerifyTV.setBackgroundResource(R.drawable.round_view_yellow_border);
            mProofVerifyTV.setEnabled(true);
        }

    }

    public interface OnExpLvSubsItemClickListener{

        void verifiyEmail();
        void verifiyMob();
        void verifiyDoc();
        void verifiyIDProof();
        void onProfileSubmission(String urlPath, Object request, String methodName);
//        void onProfileSubmission(String urlPath, PostAuthWebRequest request, String methodName);

        void refreshPage();

    }

    public static String removeLastChar(String s) {
        if (s == null || s.length() == 0 || s.equalsIgnoreCase("--") || s.equalsIgnoreCase("-")) {
            return s = "";
        }
        String rawString = s.substring(0, s.length()-1);
        return rawString;
//        st = st.replaceAll("\\s+","")
    }
    public static String removeWhiteSpace(String s) {
        if (s == null || s.length() == 0 || s.equalsIgnoreCase("--")) {
            return s = "[]";
        }
        return s.replaceAll("\\s+","");
//        st = st.replaceAll("\\s+","")
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


}
